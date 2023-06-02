package io.github.tobi.laa.reflective.fluent.builders.mojo;

import com.google.common.collect.Sets;
import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import io.github.tobi.laa.reflective.fluent.builders.props.impl.StandardBuildersProperties;
import io.github.tobi.laa.reflective.fluent.builders.service.api.BuilderMetadataService;
import io.github.tobi.laa.reflective.fluent.builders.service.api.ClassService;
import jakarta.validation.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.build.BuildContext;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.maven.artifact.Artifact.*;

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

    private Set<String> scopesToInclude;

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

    @lombok.NonNull
    private final BuildContext buildContext;

    @Override
    @SneakyThrows
    public void execute() throws MojoFailureException, MojoExecutionException {
        validateParams();
        mapMavenParamsToProps();
        final var classes = collectAndFilterClasses();
        setDefaultsIfNecessary();
        createTargetDirectory();
        final var nonEmptyBuilderMetadata = collectNonEmptyBuilderMetadata(classes);
        if (isGenerationNecessary(nonEmptyBuilderMetadata)) {
            generateAndWriteBuildersToTarget(nonEmptyBuilderMetadata);
            addCompileSourceRoot();
            refreshBuildContext(nonEmptyBuilderMetadata);
        }
    }

    private void validateParams() throws MojoExecutionException {
        try (final ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            final Validator validator = factory.getValidator();
            final var violations = validator.validate(this);
            if (!violations.isEmpty()) {
                throw new MojoExecutionException("Parameter validation failed.\n" + violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("\n")));
            }
        }
    }

    private void mapMavenParamsToProps() {
        final var standardBuildersProperties = (StandardBuildersProperties) buildersProperties;
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
        final var allClasses = collectClasses();
        final var filteredClasses = filterClasses(allClasses);
        getLog().info("Found " + filteredClasses.size() + " classes for which to generate builders.");
        return filteredClasses;
    }

    private Set<Class<?>> collectClasses() throws MojoExecutionException {
        final var allClasses = new HashSet<Class<?>>();
        try (final var classLoader = constructClassLoader()) {
            for (final var include : includes) {
                if (include.getPackageName() != null) {
                    getLog().info("Scan package " + include.getPackageName() + " recursively for classes.");
                    allClasses.addAll(classService.collectClassesRecursively(include.getPackageName().trim(), classLoader));
                } else {
                    getLog().info("Add class " + include.getClassName() + '.');
                    allClasses.add(classForName(include.getClassName()));
                }
            }
        } catch (final IOException e) {
            throw new MojoExecutionException(e);
        }
        return allClasses;
    }

    private URLClassLoader constructClassLoader() throws MojoExecutionException {
        final var classUrls = Stream.concat( //
                        getOutputDirectoryUrls(), //
                        getUrlsOfArtifactsInScopesToInclude()) //
                .toArray(URL[]::new);
        return new URLClassLoader(classUrls, Thread.currentThread().getContextClassLoader());
    }

    private Stream<URL> getOutputDirectoryUrls() throws MojoExecutionException {
        final List<URL> outputDirectoryUrls = new ArrayList<>();
        getLog().debug("Add " + mavenProject.getBuild().getOutputDirectory() + " to ClassLoader.");
        outputDirectoryUrls.add(toUrl(new File(mavenProject.getBuild().getOutputDirectory())));
        if (isTestPhase()) {
            getLog().debug("Add " + mavenProject.getBuild().getTestOutputDirectory() + " to ClassLoader.");
            outputDirectoryUrls.add(toUrl(new File(mavenProject.getBuild().getTestOutputDirectory())));
        }
        return outputDirectoryUrls.stream();
    }

    private Stream<URL> getUrlsOfArtifactsInScopesToInclude() throws MojoExecutionException {
        final List<URL> urlsOfArtifacts = new ArrayList<>();
        for (final var artifact : mavenProject.getArtifacts()) {
            if (scopesToInclude.contains(artifact.getScope())) {
                getLog().debug("Add " + artifact.getFile() + " to ClassLoader.");
                urlsOfArtifacts.add(toUrl(artifact.getFile()));
            }
        }
        return urlsOfArtifacts.stream();
    }

    private URL toUrl(final File file) throws MojoExecutionException {
        try {
            return file.toURI().toURL();
        } catch (final MalformedURLException e) {
            throw new MojoExecutionException("Error while attempting to convert file " + file + " to URL.", e);
        }
    }

    private Class<?> classForName(final String className) throws MojoExecutionException {
        try {
            return Class.forName(className.trim());
        } catch (final ClassNotFoundException e) {
            throw new MojoExecutionException(e);
        }
    }

    private Set<Class<?>> filterClasses(final Set<Class<?>> classes) {
        final var buildableClasses = builderMetadataService.filterOutNonBuildableClasses(classes);
        final var filteredClasses = builderMetadataService.filterOutConfiguredExcludes(buildableClasses);
        if (getLog().isDebugEnabled()) {
            getLog().debug("Builders will be generated for the following classes:");
            filteredClasses.forEach(c -> getLog().debug("- " + c.getName()));
            final var nonBuildableClasses = Sets.difference(classes, buildableClasses);
            getLog().debug("The following classes cannot be built:");
            nonBuildableClasses.forEach(c -> getLog().debug("- " + c.getName()));
            final var excludedClasses = Sets.difference(buildableClasses, filteredClasses);
            getLog().debug("The following classes have been configured to be excluded:");
            excludedClasses.forEach(c -> getLog().debug("- " + c.getName()));
        }
        return filteredClasses;
    }

    private void setDefaultsIfNecessary() {
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
        //
        if (scopesToInclude == null && isTestPhase()) {
            scopesToInclude = Set.of(SCOPE_COMPILE, SCOPE_PROVIDED, SCOPE_SYSTEM, SCOPE_TEST);
        } else if (scopesToInclude == null) {
            scopesToInclude = Set.of(SCOPE_COMPILE, SCOPE_PROVIDED, SCOPE_SYSTEM);
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
        final var allMetadata = buildableClasses.stream() //
                .map(builderMetadataService::collectBuilderMetadata) //
                .collect(Collectors.toSet());
        final var nonEmptyMetadata = builderMetadataService.filterOutEmptyBuilders(allMetadata);
        if (getLog().isDebugEnabled()) {
            final var emptyMetadata = Sets.difference(allMetadata, nonEmptyMetadata);
            getLog().debug("Builders for the following classes would be empty and will thus be skipped:");
            emptyMetadata.forEach(m -> getLog().debug("- " + m.getBuiltType().getType().getName()));
        }
        return nonEmptyMetadata;
    }

    private boolean isGenerationNecessary(final Set<BuilderMetadata> builderMetadata) {
        return !buildContext.isIncremental() || //
                !allBuilderFilesExist(builderMetadata) || //
                buildContextHasDelta(builderMetadata);
    }

    private boolean allBuilderFilesExist(final Set<BuilderMetadata> builderMetadata) {
        return builderMetadata.stream().map(this::resolveBuilderFile).allMatch(Files::exists);
    }

    private Path resolveBuilderFile(final BuilderMetadata builderMetadata) {
        Path builderFile = target.toPath();
        for (final String subdir : builderMetadata.getPackageName().split("\\.")) {
            builderFile = builderFile.resolve(subdir);
        }
        builderFile = builderFile.resolve(builderMetadata.getName() + ".java");
        return builderFile;
    }

    private boolean buildContextHasDelta(final Set<BuilderMetadata> builderMetadata) {
        return determineBuiltTypeClassLocations(builderMetadata).anyMatch(buildContext::hasDelta);
    }

    private Stream<File> determineBuiltTypeClassLocations(final Set<BuilderMetadata> builderMetadata) {
        return builderMetadata.stream() //
                .map(BuilderMetadata::getBuiltType) //
                .map(BuilderMetadata.BuiltType::getClass) //
                .map(classService::determineClassLocation) //
                .filter(Optional::isPresent) //
                .map(Optional::get) //
                .map(Path::toFile);
    }

    private void generateAndWriteBuildersToTarget(Set<BuilderMetadata> nonEmptyBuilderMetadata) throws MojoFailureException {
        for (final var metadata : nonEmptyBuilderMetadata) {
            getLog().info("Generate builder for class " + metadata.getBuiltType().getType().getName());
            final var javaFile = javaFileGenerator.generateJavaFile(metadata);
            try {
                javaFile.writeTo(target);
            } catch (final IOException e) {
                throw new MojoFailureException("Could not create file for builder for " + metadata.getBuiltType().getType().getName() + '.', e);
            }
        }
    }

    private void addCompileSourceRoot() {
        if (addCompileSourceRoot) {
            final var path = target.getPath();
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

    private void refreshBuildContext(final Set<BuilderMetadata> builderMetadata) {
        determineBuiltTypeClassLocations(builderMetadata).forEach(buildContext::refresh);
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
     * If the mojo is executed within a {@link #isTestPhase() test phase}, the scope
     * {@link org.apache.maven.artifact.Artifact#SCOPE_TEST test}
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
        this.scopesToInclude = scopesToInclude;
    }
}
