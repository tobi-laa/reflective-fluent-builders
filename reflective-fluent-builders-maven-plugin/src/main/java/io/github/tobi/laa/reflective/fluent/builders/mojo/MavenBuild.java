package io.github.tobi.laa.reflective.fluent.builders.mojo;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.build.BuildContext;
import org.codehaus.plexus.logging.AbstractLogEnabled;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;
import java.util.Objects;
import java.util.Set;

import static java.lang.Boolean.parseBoolean;

/**
 * <p>
 * Encapsulates accesses to {@link BuildContext}, {@link MavenProject} and {@link MojoExecution} for better testability.
 * </p>
 */
@Singleton
@Named
@RequiredArgsConstructor(onConstructor_ = @Inject)
class MavenBuild extends AbstractLogEnabled {

    @lombok.NonNull
    private final BuildContext buildContext;

    @lombok.NonNull
    private final MavenProject mavenProject;

    @lombok.NonNull
    private final MojoExecution mojoExecution;

    boolean isIncremental() {
        return buildContext.isIncremental() || parseBoolean(System.getenv("incrementalBuildForIntegrationTests"));
    }

    boolean hasDelta(final File file) {
        Objects.requireNonNull(file);
        return buildContext.hasDelta(file);
    }

    void refresh(final File file) {
        Objects.requireNonNull(file);
        buildContext.refresh(file);
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
}
