package io.github.tobi.laa.reflective.fluent.builders.mojo;

import com.google.common.collect.Sets;
import io.github.classgraph.ClassInfo;
import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata.BuiltType;
import io.github.tobi.laa.reflective.fluent.builders.service.api.BuilderMetadataService;
import io.github.tobi.laa.reflective.fluent.builders.service.api.ClassService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.build.BuildContext;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

/**
 * <p>
 * A maven plugin for generating fluent builders for existing classes with the help of reflection. This can be useful in
 * cases where it is not possible (or very hard) to change the sources of said classes to generate builders directly.
 * </p>
 */
@Mojo(name = "generate-builders", defaultPhase = LifecyclePhase.GENERATE_TEST_SOURCES, requiresDependencyResolution = ResolutionScope.TEST)
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class GenerateBuildersMojo extends AbstractMojo {

    @lombok.NonNull
    private final MojoParams params;

    @lombok.NonNull
    private final MavenBuild mavenBuild;

    @lombok.NonNull
    private final ClassLoaderProvider classLoaderProvider;

    @lombok.NonNull
    private final JavaFileGenerator javaFileGenerator;

    @lombok.NonNull
    private final ClassService classService;

    @lombok.NonNull
    private final BuilderMetadataService builderMetadataService;

    @lombok.NonNull
    private final JavaFileHelper javaFileHelper;

    @lombok.NonNull
    private final OrphanDeleter orphanDeleter;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        logMavenParams();
        validateParams();
        final var classes = collectAndFilterClasses();
        final var nonEmptyBuilderMetadata = collectNonEmptyBuilderMetadata(classes);
        createTargetDirectory();
        generateAndWriteBuildersToTarget(nonEmptyBuilderMetadata);
        deleteOrphanedBuilders(nonEmptyBuilderMetadata);
        refreshBuildContext(nonEmptyBuilderMetadata);
        addCompileSourceRoot();
        closeClassLoader();
    }

    private void closeClassLoader() {
        classLoaderProvider.closeAndDisposeOfClassLoader();
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

    private Set<ClassInfo> collectAndFilterClasses() throws MojoExecutionException {
        final var allClasses = collectClasses();
        final var filteredClasses = filterClasses(allClasses);
        getLog().info("Found " + filteredClasses.size() + " classes for which to generate builders.");
        return filteredClasses;
    }

    private Set<ClassInfo> collectClasses() throws MojoExecutionException {
        final var allClasses = new HashSet<ClassInfo>();
        for (final var include : params.getIncludes()) {
            if (include.getPackageName() != null) {
                getLog().info("Scan package " + include.getPackageName() + " recursively for classes.");
                allClasses.addAll(classService.collectClassesRecursively(include.getPackageName().trim()));
            } else {
                getLog().info("Add class " + include.getClassName() + '.');
                allClasses.add(loadClass(include.getClassName()));
            }
        }
        return allClasses;
    }

    private ClassInfo loadClass(final String className) throws MojoExecutionException {
        return classService.loadClass(className).orElseThrow(() -> new MojoExecutionException("Unable to load class " + className));
    }

    private Set<ClassInfo> filterClasses(final Set<ClassInfo> classes) {
        final var buildableClasses = builderMetadataService.filterOutNonBuildableClasses(classes);
        final var filteredClasses = builderMetadataService.filterOutConfiguredExcludes(buildableClasses);
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

    private Set<BuilderMetadata> collectNonEmptyBuilderMetadata(final Set<ClassInfo> buildableClasses) {
        final var allMetadata = buildableClasses.stream() //
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

    private boolean isGenerationNecessary(final BuilderMetadata builderMetadata) {
        return !mavenBuild.isIncremental() || //
                !builderFileExist(builderMetadata) || //
                buildContextHasDelta(builderMetadata);
    }

    private boolean builderFileExist(final BuilderMetadata builderMetadata) {
        final var builderFile = resolveBuilderFile(builderMetadata);
        return Files.exists(builderFile);
    }

    private Path resolveBuilderFile(final BuilderMetadata builderMetadata) {
        return params.getTarget().toPath()
                .resolve(javaFileHelper.javaNameToPath(builderMetadata.getPackageName()))
                .resolve(builderMetadata.getName() + ".java");
    }

    private boolean buildContextHasDelta(final BuilderMetadata builderMetadata) {
        return determineSourceOrClassLocation(builderMetadata).map(mavenBuild::hasDelta).orElse(true);
    }

    private Optional<File> determineSourceOrClassLocation(final BuilderMetadata builderMetadata) {
        return determineSourceOrClassLocation(builderMetadata.getBuiltType()).map(Path::toFile);
    }

    private Optional<Path> determineSourceOrClassLocation(final BuiltType type) {
        return determineSourceLocation(type).or(type::getLocation).filter(not(mavenBuild::containsClassFile));
    }

    private Optional<Path> determineSourceLocation(final BuiltType type) {
        return type.getSourceFile().flatMap(source -> mavenBuild.resolveSourceFile(type.getType().getPackageName(), source));
    }

    private void generateAndWriteBuildersToTarget(final Set<BuilderMetadata> nonEmptyBuilderMetadata) throws MojoFailureException {
        for (final var metadata : nonEmptyBuilderMetadata) {
            generateAndWriteBuilderToTarget(metadata);
        }
    }

    private void generateAndWriteBuilderToTarget(final BuilderMetadata metadata) throws MojoFailureException {
        final var className = metadata.getBuiltType().getType().getName();
        if (isGenerationNecessary(metadata)) {
            getLog().info("Generate builder for class " + className);
            final var javaFile = javaFileGenerator.generateJavaFile(metadata);
            try {
                javaFile.writeTo(params.getTarget());
            } catch (final IOException e) {
                throw new MojoFailureException("Could not create file for builder for " + className + '.', e);
            }
        } else {
            getLog().info("Builder for class " + className + " already exists and is up to date.");
        }
    }

    private void deleteOrphanedBuilders(final Set<BuilderMetadata> metadata) throws MojoFailureException {
        if (params.isDeleteOrphanedBuilders()) {
            orphanDeleter.deleteOrphanedBuilders(params.getTarget().toPath(), metadata);
        }
    }

    private void addCompileSourceRoot() {
        if (params.isAddCompileSourceRoot()) {
            mavenBuild.addCompileSourceRoot(params.getTarget());
        }
    }

    private void refreshBuildContext(final Set<BuilderMetadata> builderMetadata) {
        builderMetadata.stream()
                .map(this::determineSourceOrClassLocation)
                .flatMap(Optional::stream)
                .forEach(mavenBuild::refresh);
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
    @SuppressWarnings("unused")
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
    @SuppressWarnings("unused")
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
    @SuppressWarnings("unused")
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
    @SuppressWarnings("unused")
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
    @Parameter(name = "getAndAddEnabled", defaultValue = "true")
    @SuppressWarnings("unused")
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
    @SuppressWarnings("unused")
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
    @SuppressWarnings("unused")
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
     * <p>
     * If not specified, the default value is set to exclude classes ending with {@code Builder} or {@code BuilderImpl}.
     * </p>
     *
     * @param excludes Specifies classes to be excluded when generating builders.
     * @since 1.0.0
     */
    @Parameter(name = "excludes")
    @SuppressWarnings("unused")
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
    @SuppressWarnings("unused")
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
    @SuppressWarnings("unused")
    public void setAddCompileSourceRoot(final boolean addCompileSourceRoot) {
        params.setAddCompileSourceRoot(addCompileSourceRoot);
    }

    /**
     * <p>
     * Specifies whether to delete orphaned builders from the {@link #setTarget(File) target directory}.
     * </p>
     * <p>
     * A builder is considered orphaned if it would no longer be generated during a clean build, be it due to a class
     * having been deleted or renamed or due to the configuration having been changed.
     * </p>
     * <p>
     * As this is the behaviour that most projects will want, the default is {@code true}.
     * </p>
     *
     * @param deleteOrphanedBuilders Specifies whether to delete orphaned builders from the
     *                               {@link #setTarget(File) target directory}.
     * @since 1.7.0
     */
    @Parameter(name = "deleteOrphanedBuilders", defaultValue = "true")
    @SuppressWarnings("unused")
    public void setDeleteOrphanedBuilders(final boolean deleteOrphanedBuilders) {
        params.setDeleteOrphanedBuilders(deleteOrphanedBuilders);
    }

    /**
     * <p>
     * (Re-)Injects {@code buildContext} into components depending on it. This is mainly to circumvent bugs in multi module
     * projects where dependencies are being re-used across executions.
     * </p>
     *
     * @param buildContext (New) {@link BuildContext} to inject into components depending on it.
     */
    @Inject
    @SuppressWarnings("unused")
    public void setBuildContext(final BuildContext buildContext) {
        mavenBuild.setBuildContext(buildContext);
    }

    /**
     * <p>
     * (Re-)Injects {@code mavenProject} into components depending on it. This is mainly to circumvent bugs in multi module
     * projects where dependencies are being re-used across executions.
     * </p>
     *
     * @param mavenProject (New) {@link MavenProject} to inject into components depending on it.
     */
    @Inject
    @SuppressWarnings("unused")
    public void setMavenProject(final MavenProject mavenProject) {
        mavenBuild.setMavenProject(mavenProject);
    }

    /**
     * <p>
     * (Re-)Injects {@code mojoExecution} into components depending on it. This is mainly to circumvent bugs in multi module
     * projects where dependencies are being re-used across executions.
     * </p>
     *
     * @param mojoExecution (New) {@link MojoExecution} to inject into components depending on it.
     */
    @Inject
    @SuppressWarnings("unused")
    public void setMojoExecution(final MojoExecution mojoExecution) {
        mavenBuild.setMojoExecution(mojoExecution);
    }
}
