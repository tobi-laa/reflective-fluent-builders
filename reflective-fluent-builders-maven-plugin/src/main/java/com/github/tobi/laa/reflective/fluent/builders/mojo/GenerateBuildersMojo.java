package com.github.tobi.laa.reflective.fluent.builders.mojo;

import com.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants;
import com.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator;
import com.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import com.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import com.github.tobi.laa.reflective.fluent.builders.props.impl.StandardBuildersProperties;
import com.github.tobi.laa.reflective.fluent.builders.service.api.BuilderMetadataService;
import com.github.tobi.laa.reflective.fluent.builders.service.api.ClassService;
import com.google.common.collect.Sets;
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

@Mojo(name = "generate-builders", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class GenerateBuildersMojo extends AbstractMojo {

    @Setter(onMethod_ =
    @Parameter(name = "builderPackage", defaultValue = BuilderConstants.PACKAGE_PLACEHOLDER))
    private String builderPackage;

    @Setter(onMethod_ =
    @Parameter(name = "builderSuffix", defaultValue = "Builder"))
    private String builderSuffix;

    @Setter(onMethod_ =
    @Parameter(name = "setterPrefix", defaultValue = "set"))
    private String setterPrefix;

    @Setter(onMethod_ =
    @Parameter(name = "getterPrefix", defaultValue = "get"))
    private String getterPrefix;

    @Setter(onMethod_ =
    @Parameter(name = "getAndAddEnabled", defaultValue = "false"))
    private boolean getAndAddEnabled;

    @Setter(onMethod_ =
    @Parameter(name = "hierarchyCollection"))
    @Valid
    private HierarchyCollection hierarchyCollection;

    @Setter(onMethod_ =
    @Parameter(required = true, name = "includes"))
    @NotEmpty(message = "At least one <include> has to be specified.")
    @Valid
    private Set<Include> includes;

    @Setter(onMethod_ =
    @Parameter(name = "excludes"))
    @Valid
    private Set<Exclude> excludes;

    @Setter(onMethod_ =
    @Parameter(name = "target"))
    private File target;

    @Setter(onMethod_ =
    @Parameter(name = "addCompileSourceRoot", defaultValue = "true"))
    private boolean addCompileSourceRoot;

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
        final var classes = collectAndFilterClasses();
        setDefaultTargetDirectoryIfNecessary();
        createTargetDirectory();
        final var nonEmptyBuilderMetadata = collectNonEmptyBuilderMetadata(classes);
        generateAndWriteBuildersToTarget(nonEmptyBuilderMetadata);
        addCompileSourceRoot();
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
        for (final var include : includes) {
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
}
