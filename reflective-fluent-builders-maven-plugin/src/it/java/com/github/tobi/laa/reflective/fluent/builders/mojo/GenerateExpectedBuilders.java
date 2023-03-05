package com.github.tobi.laa.reflective.fluent.builders.mojo;

import com.soebes.itf.jupiter.extension.MavenDebug;
import com.soebes.itf.jupiter.extension.MavenJupiterExtension;
import com.soebes.itf.jupiter.extension.MavenTest;
import com.soebes.itf.jupiter.maven.MavenExecutionResult;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static com.github.tobi.laa.reflective.fluent.builders.mojo.GenerateBuildersMojoIT.EXPECTED_BUILDERS_ROOT_DIR;
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

    @BeforeAll
    @SneakyThrows
    static void deleteExpectedBuilders() {
        if (Files.exists(EXPECTED_BUILDERS_ROOT_DIR)) {
            Files.walkFileTree(EXPECTED_BUILDERS_ROOT_DIR, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return super.visitFile(file, attrs);
                }

                @Override
                public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
                    final FileVisitResult result = super.postVisitDirectory(dir, exc);
                    Files.delete(dir);
                    return result;
                }
            });
        }
    }

    @MavenTest
    @MavenDebug
    void generateExpectedBuilders(final MavenExecutionResult result) {
        assertThat(result).isSuccessful().project().hasTarget();
    }
}
