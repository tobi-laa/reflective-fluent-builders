package com.github.tobi.laa.reflective.fluent.builders.mojo;

import com.github.tobi.laa.reflective.fluent.builders.test.models.complex.Complex;
import com.github.tobi.laa.reflective.fluent.builders.test.models.full.Full;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.Simple;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClassNoDefaultConstructor;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClassNoSetPrefix;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.hierarchy.Child;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.hierarchy.Parent;
import com.github.tobi.laa.reflective.fluent.builders.test.models.visibility.Visibility;
import com.soebes.itf.jupiter.extension.MavenDebug;
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
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.soebes.itf.extension.assertj.MavenITAssertions.assertThat;

@MavenJupiterExtension
class GenerateBuildersMojoIT {

    private final Path expectedBuildersRootDir = Paths.get("src", "it", "resources", "expected-builders");

    @MavenTest
    void testGenerationForPackageComplex(final MavenExecutionResult result) {
        assertThat(result) //
                .isSuccessful() //
                .project() //
                .hasTarget() //
                .satisfies(containsExpectedBuilders(Complex.class.getPackage(), false));
    }

    @MavenTest
    void testGenerationForPackageFull(final MavenExecutionResult result) {
        assertThat(result) //
                .isSuccessful() //
                .project() //
                .hasTarget() //
                .satisfies(containsExpectedBuilders(Full.class.getPackage(), false));
    }

    @MavenTest
    void testGenerationForPackageSimple(final MavenExecutionResult result) {
        assertThat(result) //
                .isSuccessful() //
                .project() //
                .hasTarget() //
                .satisfies(containsExpectedBuilders(Simple.class.getPackage(), false));
    }

    @MavenTest
    void testGenerationForPackageUnbuildable(final MavenExecutionResult result) {
        assertThat(result) //
                .isSuccessful() //
                .project() //
                .hasTarget() //
                .satisfies(hasEmptyDirectory(Paths.get("generated-sources", "builders")));
    }

    @MavenTest
    void testGenerationForPackageVisibility(final MavenExecutionResult result) {
        assertThat(result) //
                .isSuccessful() //
                .project() //
                .hasTarget() //
                .satisfies(containsExpectedBuilders(Visibility.class.getPackage(), false));
    }

    @MavenTest
    @MavenDebug
    void testGenerationForPackageSimpleWithDebugLogging(final MavenExecutionResult result) {
        assertThat(result).isSuccessful();
        final var targetDirectory = getGeneratedBuildersDirectory(result.getMavenProjectResult(), false);
        assertThat(result) //
                .out() //
                .info() //
                .contains( //
                        "Scan package " + Simple.class.getPackage().getName() + " recursively for classes.", //
                        "Found 5 buildable classes.", //
                        "Make sure target directory " + targetDirectory + " exists.", //
                        "Generate builder for class " + Child.class.getName(), //
                        "Generate builder for class " + SimpleClass.class.getName(), //
                        "Generate builder for class " + Parent.class.getName());
        assertThat(result) //
                .out() //
                .debug() //
                .contains( //
                        "Properties are: StandardBuildersProperties(builderPackage=<PACKAGE_NAME>, builderSuffix=Builder, setterPrefix=set, hierarchyCollection=StandardBuildersProperties.StandardHierarchyCollection(classesToExclude=[]))", //
                        "The following classes can be built:", //
                        "- " + SimpleClassNoSetPrefix.class.getName(), //
                        "- " + SimpleClassNoDefaultConstructor.class.getName(), //
                        "- " + Child.class.getName(), //
                        "- " + SimpleClass.class.getName(), //
                        "- " + Parent.class.getName(), //
                        "The following classes cannot be built:", //
                        "- " + Simple.class.getName(), //
                        "Builders for the following classes would be empty and will thus be skipped:", //
                        "- " + SimpleClassNoDefaultConstructor.class.getName(), //
                        "- " + SimpleClassNoSetPrefix.class.getName(), //
                        "Add " + targetDirectory + " as source folder.");
    }

    @MavenTest
    void testGenerationInvalidTargetDirectory(final MavenExecutionResult result) {
        assertThat(result).isFailure();
        final var pomXml = result.getMavenProjectResult().getTargetProjectDirectory() //
                .resolve("pom.xml") //
                .toAbsolutePath() //
                .toString();
        assertThat(result) //
                .out() //
                .info() //
                .contains( //
                        "Scan package does.not.matter recursively for classes.", //
                        "Found 0 buildable classes.", //
                        "Make sure target directory " + pomXml + " exists.");
        assertThat(result) //
                .out() //
                .error() //
                .anySatisfy(s -> Assertions.assertThat(s) //
                        .containsSubsequence( //
                                "Failed to execute goal com.github.tobi-laa:reflective-fluent-builders-maven-plugin", //
                                "generate-builders (default) on project", //
                                "Could not create target directory", //
                                pomXml, //
                                "-> [Help 1]"));
    }

    @MavenTest
    void testGenerationForPackageSimpleNoAddCompileSourceRoot(final MavenExecutionResult result) {
        assertThat(result) //
                .isSuccessful() //
                .project() //
                .hasTarget() //
                .satisfies(hasEmptyDirectory(Paths.get("classes")));
    }

    @MavenTest
    @MavenDebug
    void testGenerationForPackageSimplePhaseGenerateTestSources(final MavenExecutionResult result) {
        assertThat(result) //
                .isSuccessful() //
                .project() //
                .hasTarget() //
                .satisfies(hasEmptyDirectory(Paths.get("classes"))) //
                .satisfies(hasNonEmptyDirectory(Paths.get("test-classes"))) //
                .satisfies(containsExpectedBuilders(Simple.class.getPackage(), true));
    }

    private Consumer<MavenProjectResult> containsExpectedBuilders(final Package buildersPackage, final boolean test) {
        return result -> {
            for (final Path expectedBuilderRelativeToRoot : expectedBuildersForPackageRelativeToRoot(buildersPackage)) {
                final var actualBuilder = getGeneratedBuildersDirectory(result, test).resolve(expectedBuilderRelativeToRoot);
                final var expectedBuilder = expectedBuildersRootDir.resolve(expectedBuilderRelativeToRoot);
                Assertions.assertThat(actualBuilder) //
                        .exists() //
                        .content() //
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
        return findFilesRecursively(expectedBuildersDir).stream() //
                .map(expectedBuildersRootDir::relativize) //
                .collect(Collectors.toList());
    }

    private Condition<MavenProjectResult> hasEmptyDirectory(final Path subdirectory) {
        return new Condition<>(
                hasDirectoryWithFiles(subdirectory, List::isEmpty),
                "Expected subdirectory %s in target base directory to be empty.",
                subdirectory
        );
    }

    private Condition<MavenProjectResult> hasNonEmptyDirectory(final Path subdirectory) {
        return new Condition<>(
                hasDirectoryWithFiles(subdirectory, Predicate.not(List::isEmpty)),
                "Expected subdirectory %s in target base directory to not be empty.",
                subdirectory
        );
    }

    private Predicate<MavenProjectResult> hasDirectoryWithFiles(final Path subdirectory, final Predicate<List<Path>> filesPredicate) {
        return result -> {
            final var directory = result.getTargetProjectDirectory().resolve("target").resolve(subdirectory);
            return filesPredicate.test(findFilesRecursively(directory));
        };
    }

    @SneakyThrows
    private List<Path> findFilesRecursively(final Path directory) {
        if (Files.notExists(directory)) {
            return Collections.emptyList();
        } else {
            final var files = new ArrayList<Path>();
            Files.walkFileTree(directory, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                    files.add(file);
                    return super.visitFile(file, attrs);
                }
            });
            return files;
        }
    }

    private Path getGeneratedBuildersDirectory(final MavenProjectResult result, final boolean test) {
        return result.getTargetProjectDirectory() //
                .resolve("target") //
                .resolve(test ? "generated-test-sources" : "generated-sources") //
                .resolve("builders");
    }
}
