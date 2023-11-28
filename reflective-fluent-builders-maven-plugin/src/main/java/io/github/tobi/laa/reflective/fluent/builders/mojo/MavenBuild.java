package io.github.tobi.laa.reflective.fluent.builders.mojo;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.build.BuildContext;
import org.codehaus.plexus.logging.AbstractLogEnabled;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.lang.Boolean.parseBoolean;
import static java.nio.file.Files.getLastModifiedTime;

/**
 * <p>
 * Encapsulates accesses to {@link BuildContext}, {@link MavenProject} and {@link MojoExecution} for better testability.
 * </p>
 */
@Singleton
@Named
@RequiredArgsConstructor(onConstructor_ = @Inject)
@Setter
class MavenBuild extends AbstractLogEnabled {

    @lombok.NonNull
    private BuildContext buildContext;

    @lombok.NonNull
    private MavenProject mavenProject;

    @lombok.NonNull
    private MojoExecution mojoExecution;

    boolean isIncremental() {
        return buildContext.isIncremental() || parseBoolean(System.getProperty("incrementalBuildForIntegrationTests"));
    }

    @SneakyThrows
    boolean hasDelta(final File file) {
        Objects.requireNonNull(file);
        if (parseBoolean(System.getProperty("fixedNoDeltaForIntegrationTests"))) {
            return false;
        } else if (isProjectFile(file)) {
            return buildContext.hasDelta(file);
        } else if (file.exists()) {
            final String key = lastModifiedKey(file);
            final Instant lastModifiedFromFile = getLastModifiedTime(file.toPath()).toInstant();
            final Instant lastModifiedFromBuildContext = Optional.ofNullable(buildContext.getValue(key))
                    .map(Instant.class::cast)
                    .orElse(Instant.MIN);
            return lastModifiedFromFile.isAfter(lastModifiedFromBuildContext);
        } else {
            return false;
        }
    }

    @SneakyThrows
    void refresh(final File file) {
        Objects.requireNonNull(file);
        if (isProjectFile(file)) {
            buildContext.refresh(file);
        } else if (file.exists()) {
            final String key = lastModifiedKey(file);
            final Instant lastModified = getLastModifiedTime(file.toPath()).toInstant();
            buildContext.setValue(key, lastModified);
        }
    }

    private String lastModifiedKey(final File file) {
        return getClass().getName() + ":::timestamp:::" + file.getPath();
    }

    private boolean isProjectFile(final File file) {
        return file.toPath().startsWith(mavenProject.getBasedir().toPath());
    }

    boolean isTestPhase() {
        return StringUtils.containsIgnoreCase(mojoExecution.getLifecyclePhase(), "test");
    }

    void addCompileSourceRoot(final File path) {
        Objects.requireNonNull(path);
        if (isTestPhase()) {
            getLogger().debug("Add " + path + " as test source folder.");
            mavenProject.addTestCompileSourceRoot(path.getPath());
        } else {
            getLogger().debug("Add " + path + " as source folder.");
            mavenProject.addCompileSourceRoot(path.getPath());
        }
    }

    String getDirectory() {
        return mavenProject.getBuild().getDirectory();
    }

    String getOutputDirectory() {
        return mavenProject.getBuild().getOutputDirectory();
    }

    String getTestOutputDirectory() {
        return mavenProject.getBuild().getTestOutputDirectory();
    }

    Set<Artifact> getArtifacts() {
        return mavenProject.getArtifacts();
    }

    List<String> getClasspathElements() throws DependencyResolutionRequiredException {
        if (isTestPhase()) {
            return mavenProject.getTestClasspathElements();
        } else {
            return mavenProject.getCompileClasspathElements();
        }
    }

    Optional<Path> resolveSourceFile(final String packageName, final Path sourceFile) {
        return mavenProject.getCompileSourceRoots()
                .stream()
                .map(Paths::get)
                .map(path -> path.resolve(javaNameToPath(packageName)))
                .map(path -> path.resolve(sourceFile))
                .filter(Files::isRegularFile)
                .findFirst();
    }

    private String javaNameToPath(final String name) {
        return name.replace(".", FileSystems.getDefault().getSeparator());
    }
}
