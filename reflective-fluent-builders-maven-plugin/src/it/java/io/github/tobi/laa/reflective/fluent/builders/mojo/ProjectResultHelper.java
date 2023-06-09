package io.github.tobi.laa.reflective.fluent.builders.mojo;

import com.soebes.itf.jupiter.maven.MavenProjectResult;

import java.nio.file.Path;

/**
 * <p>
 * Convenience methods for {@link MavenProjectResult}.
 * </p>
 */
class ProjectResultHelper {

    Path getGeneratedSourcesDir(final MavenProjectResult result) {
        return result.getTargetProjectDirectory().resolve("target").resolve("generated-sources");
    }

    Path getGeneratedTestSourcesDir(final MavenProjectResult result) {
        return result.getTargetProjectDirectory().resolve("target").resolve("generated-test-sources");
    }

    Path getOutputDirectory(final MavenProjectResult result) {
        return result.getTargetProjectDirectory().resolve("target").resolve("classes");
    }

    Path resolveMavenArtifact(final MavenProjectResult result, final String groupId, final String artifactId, final String version) {
        Path groupIdDir = result.getTargetCacheDirectory();
        for (final String subdir : groupId.split("\\.")) {
            groupIdDir = groupIdDir.resolve(subdir);
        }
        return groupIdDir.resolve(artifactId).resolve(version).resolve(artifactId + '-' + version + ".jar");
    }
}