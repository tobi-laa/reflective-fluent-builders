package com.github.tobi.laa.reflective.fluent.builders.mojo;

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
}