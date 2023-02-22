package com.github.tobi.laa.reflective.fluent.builders.mojo;

import com.soebes.itf.jupiter.extension.MavenJupiterExtension;
import com.soebes.itf.jupiter.extension.MavenTest;
import com.soebes.itf.jupiter.maven.MavenExecutionResult;

import static com.soebes.itf.extension.assertj.MavenITAssertions.assertThat;

/**
 * <p>
 * Not an actual integration test, but rather a way for executing the mojo to (re)generate the expected builder classes
 * within the resources directory that will be used for the assertions within {@link GenerateBuildersMojoIT}.
 * </p>
 */
@SuppressWarnings("all")
@MavenJupiterExtension
class GenerateExpectedBuilders {

    @MavenTest
    void generateExpectedBuilders(final MavenExecutionResult result) {
        assertThat(result).isSuccessful().project().hasTarget();
    }
}
