package io.github.tobi.laa.reflective.fluent.builders.mojo;

import com.google.common.collect.Sets;
import com.squareup.javapoet.JavaFile;
import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.service.api.BuilderMetadataService;
import io.github.tobi.laa.reflective.fluent.builders.service.api.ClassService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * A maven plugin for generating fluent builders for existing classes with the help of reflection. This can be useful in
 * cases where it is not possible (or very hard) to change the sources of said classes to generate builders directly.
 * </p>
 */
@Mojo(name = "generate-builders", defaultPhase = LifecyclePhase.GENERATE_SOURCES, requiresDependencyResolution = ResolutionScope.TEST)
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class GenerateBuildersMojo extends AbstractMojo {

    @lombok.NonNull
    private final MojoParams params;

    @lombok.NonNull
    private final MavenBuild mavenBuild;

    @lombok.NonNull
    private final ClassLoading classLoading;

    @lombok.NonNull
    private final JavaFileGenerator javaFileGenerator;

    @lombok.NonNull
    private final ClassService classService;

    @lombok.NonNull
    private final BuilderMetadataService builderMetadataService;

    @Override
    @SneakyThrows
    public void execute() throws MojoFailureException, MojoExecutionException {
        logMavenParams();
        validateParams();
        classLoading.setThreadClassLoaderToArtifactIncludingClassLoader();
        final Set<Class<?>> classes = collectAndFilterClasses();
        final Set<BuilderMetadata> nonEmptyBuilderMetadata = collectNonEmptyBuilderMetadata(classes);
        classLoading.resetThreadClassLoader();
        if (isGenerationNecessary(nonEmptyBuilderMetadata)) {
            createTargetDirectory();
            generateAndWriteBuildersToTarget(nonEmptyBuilderMetadata);
            addCompileSourceRoot();
            refreshBuildContext(nonEmptyBuilderMetadata);
        } else {
            logNoGenerationNecessary();
        }
    }

    private void logMavenParams() {
        getLog().debug("Parameters are: " + params);
    }

    private void validateParams() throws MojoExecutionException {
        try (final ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            final Validator validator = factory.getValidator();
            final Set<ConstraintViolation<MojoParams>> violations = validator.validate(params);
            if (!violations.isEmpty()) {
                throw new MojoExecutionException("Parameter validation failed.\n" + violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("\n")));
            }
        }
    }

    private Set<Class<?>> collectAndFilterClasses() throws MojoExecutionException {
        final Set<Class<?>> allClasses = collectClasses();
        final Set<Class<?>> filteredClasses = filterClasses(allClasses);
        getLog().info("Found " + filteredClasses.size() + " classes for which to generate builders.");
        return filteredClasses;
    }

    private Set<Class<?>> collectClasses() throws MojoExecutionException {
        final Set<Class<?>> allClasses = new HashSet<>();
        for (final Include include : params.getIncludes()) {
            if (include.getPackageName() != null) {
                getLog().info("Scan package " + include.getPackageName() + " recursively for classes.");
                allClasses.addAll(classService.collectClassesRecursively(include.getPackageName().trim()));
            } else {
                getLog().info("Add class " + include.getClassName() + '.');
                allClasses.add(classLoading.loadClass(include.getClassName()));
            }
        }
        return allClasses;
    }

    private Set<Class<?>> filterClasses(final Set<Class<?>> classes) {
        final Set<Class<?>> buildableClasses = builderMetadataService.filterOutNonBuildableClasses(classes);
        final Set<Class<?>> filteredClasses = builderMetadataService.filterOutConfiguredExcludes(buildableClasses);
        if (getLog().isDebugEnabled()) {
            getLog().debug("Builders will be generated for the following classes:");
            filteredClasses.forEach(c -> getLog().debug("- " + c.getName()));
            final Set<Class<?>> nonBuildableClasses = Sets.difference(classes, buildableClasses);
            getLog().debug("The following classes cannot be built:");
            nonBuildableClasses.forEach(c -> getLog().debug("- " + c.getName()));
            final Set<Class<?>> excludedClasses = Sets.difference(buildableClasses, filteredClasses);
            getLog().debug("The following classes have been configured to be excluded:");
            excludedClasses.forEach(c -> getLog().debug("- " + c.getName()));
        }
        return filteredClasses;
    }

    private void createTargetDirectory() throws MojoFailureException {
        getLog().info("Make sure target directory " + params.getTarget() + " exists.");
        try {
            Files.createDirectories(params.getTarget().toPath());
        } catch (final IOException e) {
            throw new MojoFailureException("Could not create target directory " + params.getTarget() + '.', e);
        }
    }

    private Set<BuilderMetadata> collectNonEmptyBuilderMetadata(final Set<Class<?>> buildableClasses) {
        final Set<BuilderMetadata> allMetadata = buildableClasses.stream() //
                .map(builderMetadataService::collectBuilderMetadata) //
                .collect(Collectors.toSet());
        final Set<BuilderMetadata> nonEmptyMetadata = builderMetadataService.filterOutEmptyBuilders(allMetadata);
        if (getLog().isDebugEnabled()) {
            final Set<BuilderMetadata> emptyMetadata = Sets.difference(allMetadata, nonEmptyMetadata);
            getLog().debug("Builders for the following classes would be empty and will thus be skipped:");
            emptyMetadata.forEach(m -> getLog().debug("- " + m.getBuiltType().getType().getName()));
        }
        return nonEmptyMetadata;
    }

    private boolean isGenerationNecessary(final Set<BuilderMetadata> builderMetadata) {
        return !mavenBuild.isIncremental() || //
                !allBuilderFilesExist(builderMetadata) || //
                buildContextHasDelta(builderMetadata);
    }

    private boolean allBuilderFilesExist(final Set<BuilderMetadata> builderMetadata) {
        return builderMetadata.stream().map(this::resolveBuilderFile).allMatch(Files::exists);
    }

    private Path resolveBuilderFile(final BuilderMetadata builderMetadata) {
        Path builderFile = params.getTarget().toPath();
        for (final String subdir : builderMetadata.getPackageName().split("\\.")) {
            builderFile = builderFile.resolve(subdir);
        }
        builderFile = builderFile.resolve(builderMetadata.getName() + ".java");
        return builderFile;
    }

    private boolean buildContextHasDelta(final Set<BuilderMetadata> builderMetadata) {
        return determineBuiltTypeClassLocations(builderMetadata).anyMatch(mavenBuild::hasDelta);
    }

    private Stream<File> determineBuiltTypeClassLocations(final Set<BuilderMetadata> builderMetadata) {
        return builderMetadata.stream() //
                .map(BuilderMetadata::getBuiltType) //
                .map(BuilderMetadata.BuiltType::getLocation) //
                .filter(Optional::isPresent) //
                .map(Optional::get) //
                .map(Path::toFile);
    }

    private void generateAndWriteBuildersToTarget(Set<BuilderMetadata> nonEmptyBuilderMetadata) throws MojoFailureException {
        for (final BuilderMetadata metadata : nonEmptyBuilderMetadata) {
            getLog().info("Generate builder for class " + metadata.getBuiltType().getType().getName());
            final JavaFile javaFile = javaFileGenerator.generateJavaFile(metadata);
            try {
                javaFile.writeTo(params.getTarget());
            } catch (final IOException e) {
                throw new MojoFailureException("Could not create file for builder for " + metadata.getBuiltType().getType().getName() + '.', e);
            }
        }
    }

    private void addCompileSourceRoot() {
        if (params.isAddCompileSourceRoot()) {
            mavenBuild.addCompileSourceRoot(params.getTarget());
        }
    }

    private void refreshBuildContext(final Set<BuilderMetadata> builderMetadata) {
        determineBuiltTypeClassLocations(builderMetadata).forEach(mavenBuild::refresh);
    }

    private void logNoGenerationNecessary() {
        getLog().info("All builders are up-to-date, skipping generation.");
    }

    /**
     * <p>
     * The package in which to place the generated builders.
     * </p>
     * <p>
     * Relative paths can be specified with the help of
     * {@code <PACKAGE_NAME>}.
     * As {@code <PACKAGE_NAME>}
     * is also the default value, builders will be placed within the same package as the classes built by them if
     * nothing else is specified.
     * </p>
     *
     * @param builderPackage The package in which to place the generated builders.
     * @since 1.0.0
     */
    @Parameter(name = "builderPackage", defaultValue = BuilderConstants.PACKAGE_PLACEHOLDER)
    public void setBuilderPackage(final String builderPackage) {
        params.setBuilderPackage(builderPackage);
    }

    /**
     * <p>
     * The suffix to append to builder classes. The default value is {@code Builder}, meaning a builder for a class
     * named {@code Person} would be named {@code PersonBuilder}.
     * </p>
     *
     * @param builderSuffix The suffix to append to builder classes.
     * @since 1.0.0
     */
    @Parameter(name = "builderSuffix", defaultValue = "Builder")
    public void setBuilderSuffix(final String builderSuffix) {
        params.setBuilderSuffix(builderSuffix);
    }

    /**
     * <p>
     * The prefix used for identifying setter methods via reflection when analyzing classes.
     * The default value is {@code set}.
     * </p>
     *
     * @param setterPrefix The prefix used for identifying setter methods via reflection when analyzing classes.
     * @since 1.0.0
     */
    @Parameter(name = "setterPrefix", defaultValue = "set")
    public void setSetterPrefix(final String setterPrefix) {
        params.setSetterPrefix(setterPrefix);
    }

    /**
     * <p>
     * The prefix used for identifying getter methods via reflection when analyzing classes.
     * The default value is {@code get}.
     * </p>
     *
     * @param getterPrefix The prefix used for identifying getter methods via reflection when analyzing classes.
     * @since 1.0.0
     */
    @Parameter(name = "getterPrefix", defaultValue = "get")
    public void setGetterPrefix(final String getterPrefix) {
        params.setGetterPrefix(getterPrefix);
    }

    /**
     * <p>
     * If this is set to {@code true}, it is assumed that getters of collections without a corresponding setter will
     * lazily initialize the underlying collection. The generated builders will use a get-and-add paradigm where
     * necessary to construct a collection.
     * </p>
     *
     * @param getAndAddEnabled Whether to support using a get-and-add paradigm in generated builders.
     * @since 1.0.0
     */
    @Parameter(name = "getAndAddEnabled", defaultValue = "false")
    public void setGetAndAddEnabled(final boolean getAndAddEnabled) {
        params.setGetAndAddEnabled(getAndAddEnabled);
    }

    /**
     * <p>
     * Properties relating to hierarchy collection of classes.
     * </p>
     * <ul>
     *     <li>
     *         <p><em>{@code hierarchyCollection.excludes}</em></p>
     *         <p>
     *             Specifies classes to be excluded from the hierarchy collection.
     *             They will not be added to the result. Furthermore, if a class from {@code excludes} is encountered
     *             during ancestor traversal of the starting class it is immediately stopped.
     *         </p>
     *         <p>
     *             A single {@code <exclude>..</exclude>} can be specified in the following ways:
     *             <ul>
     *                 <li>
     *                     <pre>
     * {@code <exclude>
     *     <className>fully.qualified.ClassName</className>
     * </exclude>}</pre>
     *                 </li>
     *                 <li>
     *                     <pre>
     * {@code <exclude>
     *     <classRegex>regex.for.Class[A-Z]+</className>
     * </exclude>}</pre>
     *                 </li>
     *                 <li>
     *                     <pre>
     * {@code <exclude>
     *     <packageName>fully.qualified.package.name</packageName>
     * </exclude>}</pre>
     *                 </li>
     *                 <li>
     *                     <pre>
     * {@code <exclude>
     *     <packageRegex>regex.for.packages.[a-z]+</packageRegex>
     * </exclude>}</pre>
     *                 </li>
     *             </ul>
     *         </p>
     *     </li>
     * </ul>
     *
     * @param hierarchyCollection Properties relating to hierarchy collection of classes.
     * @since 1.0.0
     */
    @Parameter(name = "hierarchyCollection")
    public void setHierarchyCollection(final MojoParams.HierarchyCollection hierarchyCollection) {
        params.setHierarchyCollection(hierarchyCollection);
    }

    /**
     * <p>
     * Specifies the classes for which to generate builders.
     * </p>
     * <p>
     * A single {@code <include>..</include>} can be specified in the following ways:
     * <ul>
     *     <li>
     *         <pre>
     * {@code <include>
     *     <className>fully.qualified.ClassName</className>
     * </include>}</pre>
     *      </li>
     *      <li>
     *          <pre>
     * {@code <include>
     *     <packageName>fully.qualified.package.name</packageName>
     * </include>}</pre>
     *      </li>
     * </ul>
     * </p>
     *
     * @param includes Specifies the classes for which to generate builders.
     * @since 1.0.0
     */
    @Parameter(required = true, name = "includes")
    public void setIncludes(final Set<Include> includes) {
        params.setIncludes(includes);
    }

    /**
     * <p>
     * Specifies classes to be excluded when generating builders.
     * </p>
     * <p>
     * A single {@code <exclude>..</exclude>} can be specified in the following ways:
     *     <ul>
     *         <li>
     *             <pre>
     * {@code <exclude>
     *     <className>fully.qualified.ClassName</className>
     * </exclude>}</pre>
     *          </li>
     *          <li>
     *              <pre>
     * {@code <exclude>
     *     <classRegex>regex.for.Class[A-Z]+</className>
     * </exclude>}</pre>
     *          </li>
     *          <li>
     *              <pre>
     * {@code <exclude>
     *     <packageName>fully.qualified.package.name</packageName>
     * </exclude>}</pre>
     *          </li>
     *          <li>
     *              <pre>
     * {@code <exclude>
     *     <packageRegex>regex.for.packages.[a-z]+</packageRegex>
     * </exclude>}</pre>
     *          </li>
     *     </ul>
     * </p>
     *
     * @param excludes Specifies classes to be excluded when generating builders.
     * @since 1.0.0
     */
    @Parameter(name = "excludes")
    public void setExcludes(final Set<Exclude> excludes) {
        params.setExcludes(excludes);
    }

    /**
     * <p>
     * The target directory in which to place the generated builders.
     * </p>
     * <p>
     * If not specified, the default value is dependent on the {@link LifecyclePhase}.For any test-related phase, the
     * default target directory will be the following:<br>
     * <code>${project.build.directory}/generated-test-sources/builders</code><br>
     * For any other phase, the default target directory will be:<br>
     * <code>${project.build.directory}/generated-sources/builders</code>
     * </p>
     *
     * @param target The target directory in which to place the generated builders.
     * @since 1.0.0
     */
    @Parameter(name = "target")
    public void setTarget(final File target) {
        params.setTarget(target);
    }

    /**
     * <p>
     * Specifies whether to add {@link #setTarget(File) target} as a source folder to this project. As this is the
     * behaviour that most projects will want, the default is {@code true}.
     * </p>
     *
     * @param addCompileSourceRoot Specifies whether to add {@link #setTarget(File) target} as a source folder to this
     *                             project.
     * @since 1.0.0
     */
    @Parameter(name = "addCompileSourceRoot", defaultValue = "true")
    public void setAddCompileSourceRoot(final boolean addCompileSourceRoot) {
        params.setAddCompileSourceRoot(addCompileSourceRoot);
    }

    /**
     * <p>
     * Specifies the scopes of the dependencies of the maven project within which this mojo is executed which should be
     * included when collecting classes.
     * </p>
     * <p>
     * By default, dependencies within the scope
     * {@link org.apache.maven.artifact.Artifact#SCOPE_COMPILE compile},
     * {@link org.apache.maven.artifact.Artifact#SCOPE_PROVIDED provided}
     * or
     * {@link org.apache.maven.artifact.Artifact#SCOPE_SYSTEM system}
     * are included.
     * </p>
     * <p>
     * If the mojo is executed within a
     * {@link MavenBuild#isTestPhase() test phase},
     * the scope {@link org.apache.maven.artifact.Artifact#SCOPE_TEST test}
     * is included by default as well.
     * </p>
     * <p>
     * If more control is desired, an empty {@code <scopesToInclude/>} element can be provided and the desired
     * dependencies to included can be explicitly provided as a dependency of this plugin:
     * </p>
     * <pre>
     * {@code <plugin>
     *     <groupId>io.github.tobi-laa</groupId>
     *     <artifactId>reflective-fluent-builders-maven-plugin</artifactId>
     *     <version>1.0.0</version>
     *     <executions>
     *         <!-- omitted for brevity -->
     *     </executions>
     *     <dependencies>
     *         <dependency>
     *             <groupId>com.bar.foo</groupId>
     *             <artifactId>dependency-to-include</artifactId>
     *             <version>1.2.3</version>
     *         </dependency>
     *     </dependencies>
     * </plugin>}</pre>
     *
     * @param scopesToInclude The scopes of the dependencies of the maven project within which this mojo is
     *                        executed which should be included when collecting classes.
     * @since 1.0.0
     */
    @Parameter(name = "scopesToInclude")
    public void setScopesToInclude(final Set<String> scopesToInclude) {
        params.setScopesToInclude(scopesToInclude);
    }
}
