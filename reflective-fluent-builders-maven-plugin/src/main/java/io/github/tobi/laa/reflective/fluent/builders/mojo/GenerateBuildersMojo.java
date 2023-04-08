package io.github.tobi.laa.reflective.fluent.builders.mojo;

import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import io.github.tobi.laa.reflective.fluent.builders.props.impl.StandardBuildersProperties;
import io.github.tobi.laa.reflective.fluent.builders.service.api.BuilderMetadataService;
import io.github.tobi.laa.reflective.fluent.builders.service.api.ClassService;
import com.google.common.collect.Sets;
import com.squareup.javapoet.JavaFile;
import jakarta.validation.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * A maven plugin for generating fluent builders for existing classes with the help of reflection. This can be useful in
 * cases where it is not possible (or very hard) to change the sources of said classes to generate builders directly.
 * </p>
 */
@Mojo(name = "generate-builders", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class GenerateBuildersMojo extends AbstractMojo {

    // not using Lombok to generate setters for parameters as Javadoc can not be properly extracted by
    // maven-plugin-report-plugin
    private String builderPackage;

    private String builderSuffix;

    private String setterPrefix;

    private String getterPrefix;

    private boolean getAndAddEnabled;

    @Valid
    private HierarchyCollection hierarchyCollection;

    @NotEmpty(message = "At least one <include> has to be specified.")
    @Valid
    private Set<Include> includes;

    @Valid
    private Set<Exclude> excludes;

    private File target;

    private boolean addCompileSourceRoot;

    // for read-only parameters, no documentation is provided and thus Lombok can be used
    @Setter(onMethod_ =
    @Parameter(readonly = true, required = true, defaultValue = "${project}"))
    private MavenProject mavenProject;

    @Setter(onMethod_ =
    @Parameter(readonly = true, required = true, defaultValue = "${mojoExecution}"))
    private MojoExecution mojoExecution;

    @lombok.NonNull
    private final BuildersProperties buildersProperties;

    @lombok.NonNull
    private final JavaFileGenerator javaFileGenerator;

    @lombok.NonNull
    private final ClassService classService;

    @lombok.NonNull
    private final BuilderMetadataService builderMetadataService;

    @Override
    public void execute() throws MojoFailureException, MojoExecutionException {
        validateParams();
        mapMavenParamsToProps();
        final Set<Class<?>> classes = collectAndFilterClasses();
        setDefaultTargetDirectoryIfNecessary();
        createTargetDirectory();
        final Set<BuilderMetadata> nonEmptyBuilderMetadata = collectNonEmptyBuilderMetadata(classes);
        generateAndWriteBuildersToTarget(nonEmptyBuilderMetadata);
        addCompileSourceRoot();
    }

    private void validateParams() throws MojoExecutionException {
        try (final ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            final Validator validator = factory.getValidator();
            final Set<ConstraintViolation<GenerateBuildersMojo>> violations = validator.validate(this);
            if (!violations.isEmpty()) {
                throw new MojoExecutionException("Parameter validation failed.\n" + violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("\n")));
            }
        }
    }

    private void mapMavenParamsToProps() {
        final StandardBuildersProperties standardBuildersProperties = (StandardBuildersProperties) buildersProperties;
        standardBuildersProperties.setBuilderPackage(builderPackage);
        standardBuildersProperties.setBuilderSuffix(builderSuffix);
        standardBuildersProperties.setSetterPrefix(setterPrefix);
        standardBuildersProperties.setGetterPrefix(getterPrefix);
        standardBuildersProperties.setGetAndAddEnabled(getAndAddEnabled);
        if (excludes != null) {
            standardBuildersProperties.setExcludes(excludes.stream().map(Exclude::toPredicate).collect(Collectors.toSet()));
        }
        if (hierarchyCollection != null && hierarchyCollection.getExcludes() != null) {
            standardBuildersProperties.getHierarchyCollection().setExcludes(hierarchyCollection.getExcludes().stream().map(Exclude::toPredicate).collect(Collectors.toSet()));
        }
        getLog().debug("Properties are: " + buildersProperties);
    }

    private Set<Class<?>> collectAndFilterClasses() throws MojoExecutionException {
        final Set<Class<?>> allClasses = collectClasses();
        final Set<Class<?>> filteredClasses = filterClasses(allClasses);
        getLog().info("Found " + filteredClasses.size() + " classes for which to generate builders.");
        return filteredClasses;
    }

    private Set<Class<?>> collectClasses() throws MojoExecutionException {
        final Set<Class<?>> allClasses = new HashSet<Class<?>>();
        for (final Include include : includes) {
            if (include.getPackageName() != null) {
                getLog().info("Scan package " + include.getPackageName() + " recursively for classes.");
                allClasses.addAll(classService.collectClassesRecursively(include.getPackageName().trim()));
            } else {
                getLog().info("Add class " + include.getClassName() + '.');
                allClasses.add(classForName(include.getClassName()));
            }
        }
        return allClasses;
    }

    private Class<?> classForName(final String className) throws MojoExecutionException {
        try {
            return Class.forName(className.trim());
        } catch (final ClassNotFoundException e) {
            throw new MojoExecutionException(e);
        }
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

    private void setDefaultTargetDirectoryIfNecessary() {
        if (target == null && isTestPhase()) {
            target = Paths.get(mavenProject.getBuild().getDirectory()) //
                    .resolve("generated-test-sources") //
                    .resolve("builders") //
                    .toFile();
        } else if (target == null) {
            target = Paths.get(mavenProject.getBuild().getDirectory())
                    .resolve("generated-sources") //
                    .resolve("builders") //
                    .toFile();
        }
    }

    private void createTargetDirectory() throws MojoFailureException {
        getLog().info("Make sure target directory " + target + " exists.");
        try {
            Files.createDirectories(target.toPath());
        } catch (final IOException e) {
            throw new MojoFailureException("Could not create target directory " + target + '.', e);
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

    private void generateAndWriteBuildersToTarget(Set<BuilderMetadata> nonEmptyBuilderMetadata) throws MojoFailureException {
        for (final BuilderMetadata metadata : nonEmptyBuilderMetadata) {
            getLog().info("Generate builder for class " + metadata.getBuiltType().getType().getName());
            final JavaFile javaFile = javaFileGenerator.generateJavaFile(metadata);
            try {
                javaFile.writeTo(target);
            } catch (final IOException e) {
                throw new MojoFailureException("Could not create file for builder for " + metadata.getBuiltType().getType().getName() + '.', e);
            }
        }
    }

    private void addCompileSourceRoot() {
        if (addCompileSourceRoot) {
            final String path = target.getPath();
            if (isTestPhase()) {
                getLog().debug("Add " + path + " as test source folder.");
                mavenProject.addTestCompileSourceRoot(target.getPath());
            } else {
                getLog().debug("Add " + path + " as source folder.");
                mavenProject.addCompileSourceRoot(target.getPath());
            }
        }
    }

    private boolean isTestPhase() {
        return StringUtils.containsIgnoreCase(mojoExecution.getLifecyclePhase(), "test");
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
        this.builderPackage = builderPackage;
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
        this.builderSuffix = builderSuffix;
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
        this.setterPrefix = setterPrefix;
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
        this.getterPrefix = getterPrefix;
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
        this.getAndAddEnabled = getAndAddEnabled;
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
    public void setHierarchyCollection(final HierarchyCollection hierarchyCollection) {
        this.hierarchyCollection = hierarchyCollection;
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
        this.includes = includes;
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
        this.excludes = excludes;
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
        this.target = target;
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
        this.addCompileSourceRoot = addCompileSourceRoot;
    }
}
