package com.github.tobi.laa.reflective.fluent.builders.mojo;

import com.github.tobi.laa.reflective.fluent.builders.test.models.complex.Complex;
import com.github.tobi.laa.reflective.fluent.builders.test.models.full.Full;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.Simple;
import com.github.tobi.laa.reflective.fluent.builders.test.models.visibility.Visibility;
import com.soebes.itf.jupiter.extension.MavenJupiterExtension;
import com.soebes.itf.jupiter.extension.MavenTest;
import com.soebes.itf.jupiter.maven.MavenExecutionResult;
import com.soebes.itf.jupiter.maven.MavenProjectResult;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.soebes.itf.extension.assertj.MavenITAssertions.assertThat;

@MavenJupiterExtension
class GenerateBuildersMojoIT {

    private final Path expectedBuildersRootDir = Paths.get("src", "it", "resources", "expected-builders");

    @MavenTest
    void testGenerationForPackageComplex(final MavenExecutionResult result) {
        assertThat(result)
                .isSuccessful()
                .project()
                .hasTarget()
                .satisfies(containsExpectedBuilders(Complex.class.getPackage()));
    }

    @MavenTest
    void testGenerationForPackageFull(final MavenExecutionResult result) {
        assertThat(result)
                .isSuccessful()
                .project()
                .hasTarget()
                .satisfies(containsExpectedBuilders(Full.class.getPackage()));
    }

    @MavenTest
    void testGenerationForPackageSimple(final MavenExecutionResult result) {
        assertThat(result)
                .isSuccessful()
                .project()
                .hasTarget()
                .satisfies(containsExpectedBuilders(Simple.class.getPackage()));
    }

    @MavenTest
    void testGenerationForPackageUnbuildable(final MavenExecutionResult result) {
        assertThat(result)
                .isSuccessful()
                .project()
                .hasTarget()
                .satisfies(hasEmptyDirectory(Paths.get("generated-sources", "builders")));
    }

    @MavenTest
    void testGenerationForPackageVisibility(final MavenExecutionResult result) {
        assertThat(result)
                .isSuccessful()
                .project()
                .hasTarget()
                .satisfies(containsExpectedBuilders(Visibility.class.getPackage()));
    }

    private Consumer<MavenProjectResult> containsExpectedBuilders(final Package buildersPackage) {
        return result -> {
            for (final Path expectedBuilderRelativeToRoot : expectedBuildersForPackageRelativeToRoot(buildersPackage)) {
                final var actualBuilder = result.getTargetBaseDirectory()
                        .resolve("generated-sources")
                        .resolve("builders")
                        .resolve(expectedBuilderRelativeToRoot);
                final var expectedBuilder = expectedBuildersRootDir.resolve(expectedBuilderRelativeToRoot);
                Assertions.assertThat(actualBuilder)
                        .exists()
                        .content()
                        .isEqualToIgnoringNewLines(contentOf(expectedBuilder));
            }
        };
    }

    @SneakyThrows
    private String contentOf(final Path file) {
        return Files.readString(file);
    }

    @SneakyThrows
    private List<Path> expectedBuildersForPackageRelativeToRoot(final Package pack) {
        final var expectedBuildersDir = expectedBuildersRootDir.resolve(
                pack.getName().replace(".", FileSystems.getDefault().getSeparator()));
        return findFilesRecursively(expectedBuildersDir);
    }

    private Condition<MavenProjectResult> hasEmptyDirectory(final Path subdirectory) {
        return new Condition<>(
                result -> {
                    final var directory = result.getTargetBaseDirectory().resolve(subdirectory);
                    return findFilesRecursively(directory).isEmpty();
                },
                "Expected subdirectory {} in target base directory to be empty.",
                subdirectory
        );
    }

    @SneakyThrows
    private List<Path> findFilesRecursively(final Path directory) {
        final var files = new ArrayList<Path>();
        Files.walkFileTree(directory, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                files.add(expectedBuildersRootDir.relativize(file));
                return super.visitFile(file, attrs);
            }
        });
        return files;
    }
}
