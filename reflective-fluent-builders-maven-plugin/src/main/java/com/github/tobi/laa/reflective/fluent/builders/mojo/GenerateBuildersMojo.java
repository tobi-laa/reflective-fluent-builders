package com.github.tobi.laa.reflective.fluent.builders.mojo;

import com.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants;
import com.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator;
import com.github.tobi.laa.reflective.fluent.builders.service.api.BuilderMetadataService;
import com.github.tobi.laa.reflective.fluent.builders.service.api.ClassService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

@Mojo(name = "generate-builders", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class GenerateBuildersMojo extends AbstractMojo {

    @Setter(onMethod = @__(
            @Parameter(property = "builderPackage", defaultValue = BuilderConstants.PACKAGE_PLACEHOLDER)))
    private String builderPackage;

    @Setter(onMethod = @__(
            @Parameter(property = "builderSuffix", defaultValue = "Builder")))
    private String builderSuffix;

    @Setter(onMethod = @__(
            @Parameter(property = "setterPrefix", defaultValue = "set")))
    private String setterPrefix;

    @Setter(onMethod = @__(
            @Parameter(property = "hierarchyCollection.classesToExclude")))
    private Set<Class<?>> classesToExclude = Set.of(Object.class);

    @Setter(onMethod = @__(
            @Parameter(property = "packageToScan")))
    private String packageToScan;

    @Setter(onMethod = @__(
            @Parameter(property = "target")))
    private Path target;

    @lombok.NonNull
    private final JavaFileGenerator javaFileGenerator;

    @lombok.NonNull
    private final ClassService classService;

    @lombok.NonNull
    private final BuilderMetadataService builderMetadataService;

    @lombok.NonNull
    private final Log log;

    @Override
    public void execute() throws MojoFailureException {
        log.info("Scan package " + packageToScan + " recursively for classes.");
        final var allClasses = classService.collectClassesRecursively(packageToScan.trim());
        final var buildableClasses = builderMetadataService.filterOutNonBuildableClasses(allClasses);
        log.info("Found " + allClasses.size() + " classes altogether, of which builders can be created for " + buildableClasses.size() + '.');
        log.info("Make sure target directory " + target + " exists.");
        try {
            Files.createDirectories(target);
        } catch (final IOException e) {
            throw new MojoFailureException("Could not create target directory " + target + '.', e);
        }
        for (final var clazz : buildableClasses) {
            log.info("Generate builder for class " + clazz.getName() + '.');
            final var metadata = builderMetadataService.collectBuilderMetadata(clazz);
            final var javaFile = javaFileGenerator.generateJavaFile(metadata);
            try {
                javaFile.writeTo(target);
            } catch (final IOException e) {
                throw new MojoFailureException("Could not create file for builder for " + clazz.getName() + '.', e);
            }
        }
    }
}
