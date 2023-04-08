package io.github.tobi.laa.reflective.fluent.builders.mojo;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.soebes.itf.jupiter.maven.MavenProjectResult;
import lombok.SneakyThrows;
import org.assertj.core.api.Condition;
import org.assertj.core.description.Description;
import org.assertj.core.description.JoinDescription;
import org.assertj.core.description.TextDescription;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static io.github.tobi.laa.reflective.fluent.builders.mojo.IntegrationTestConstants.EXPECTED_DEFAULT_BUILDERS_ROOT_DIR;

/**
 * <p>
 * For checking that all the expected builders (i.e. the java files) are contained within the project target dir.
 * </p>
 */
class ContainsBuildersCondition extends Condition<MavenProjectResult> {

    private final FileHelper fileHelper = new FileHelper();

    private final ProjectResultHelper projectResultHelper = new ProjectResultHelper();

    private final String builderClass;

    private final Package builderPackage;

    private final boolean buildersInTestSources;

    private final Path expectedBuildersRootDir;

    private ContainsBuildersCondition( //
                                       final String builderClass, //
                                       final boolean buildersInTestSources, //
                                       final Path expectedBuildersRootDir) {
        super();
        this.builderClass = Objects.requireNonNull(builderClass);
        this.builderPackage = null;
        this.buildersInTestSources = buildersInTestSources;
        this.expectedBuildersRootDir = Objects.requireNonNull(expectedBuildersRootDir);
    }

    private ContainsBuildersCondition( //
                                       final Package builderPackage, //
                                       final boolean buildersInTestSources, //
                                       final Path expectedBuildersRootDir) {
        super();
        this.builderClass = null;
        this.builderPackage = Objects.requireNonNull(builderPackage);
        this.buildersInTestSources = buildersInTestSources;
        this.expectedBuildersRootDir = Objects.requireNonNull(expectedBuildersRootDir);
    }

    public boolean matches(final MavenProjectResult result) {
        final Path actualBuildersDir = actualBuildersDir(result);
        final List<Path> expectedBuilderFiles = expectedBuilderFiles();
        final List<Description> descriptions = checkBuildersAndGenerateFailMessages(actualBuildersDir, expectedBuilderFiles);
        describedAs(new JoinDescription("", "", descriptions));
        return descriptions.isEmpty();
    }

    private List<Description> checkBuildersAndGenerateFailMessages( //
                                                                    final Path actualBuildersDir, //
                                                                    final List<Path> expectedBuilderFiles) {
        final List<Description> descriptions = new ArrayList<>();
        for (final Path expectedBuilderFile : expectedBuilderFiles) {
            checkBuilderAndGenerateFailMessage(actualBuildersDir, expectedBuilderFile)
                    .ifPresent(descriptions::add);
        }
        return descriptions;
    }

    private Optional<Description> checkBuilderAndGenerateFailMessage( //
                                                                      final Path actualBuildersDir, //
                                                                      final Path expectedBuilderFile) {

        final Path actualBuilderFile = resolveActualBuilderFile(actualBuildersDir, expectedBuilderFile);
        final Optional<Description> description = checkBuilderExistsAndGenerateFailMessage(actualBuilderFile);
        if (description.isPresent()) {
            return description;
        } else {
            return compareBuildersAndGenerateFailMessage(actualBuilderFile, expectedBuilderFile);
        }
    }

    private Path resolveActualBuilderFile(final Path actualBuildersDir, final Path expectedBuilderFile) {
        return actualBuildersDir.resolve(expectedBuildersRootDir.relativize(expectedBuilderFile));
    }

    private Optional<Description> checkBuilderExistsAndGenerateFailMessage(final Path actualBuilderFile) {
        if (Files.notExists(actualBuilderFile)) {
            return Optional.of(new TextDescription("builder file %s, but it does not exist.", actualBuilderFile));
        } else {
            return Optional.empty();
        }
    }

    @SneakyThrows
    private Optional<Description> compareBuildersAndGenerateFailMessage( //
                                                                         final Path actualBuilderFile, //
                                                                         final Path expectedBuilderFile) {
        final List<AbstractDelta<String>> deltas =
                DiffUtils.diff(Files.readAllLines(expectedBuilderFile), //
                                Files.readAllLines(actualBuilderFile)) //
                        .getDeltas();
        if (!deltas.isEmpty()) {
            return Optional.of(new JoinDescription(
                    String.format(
                            "builder file %s that matches expected builder file %s, but the following differences were found:",
                            actualBuilderFile,
                            expectedBuilderFile),
                    "",
                    deltas.stream() //
                            .map(AbstractDelta::toString) //
                            .map(TextDescription::new) //
                            .collect(Collectors.toList())));
        } else {
            return Optional.empty();
        }
    }

    private Path actualBuildersDir(final MavenProjectResult result) {
        if (buildersInTestSources) {
            return projectResultHelper.getGeneratedTestSourcesDir(result).resolve("builders");
        } else {
            return projectResultHelper.getGeneratedSourcesDir(result).resolve("builders");
        }
    }

    private List<Path> expectedBuilderFiles() {
        if (builderPackage != null) {
            return fileHelper.findJavaFiles(expectedBuildersRootDir, builderPackage);
        } else {
            return Collections.singletonList(
                    fileHelper.resolveJavaFile(expectedBuildersRootDir, builderClass));
        }
    }

    static ContainsBuildersCondition expectedBuilder( //
                                                      final String builderClass, //
                                                      final boolean buildersInTestSources, //
                                                      final Path expectedBuildersRootDir) {
        return new ContainsBuildersCondition(builderClass, buildersInTestSources, expectedBuildersRootDir);
    }

    static ContainsBuildersCondition expectedBuilder(final String builderClass, final boolean buildersInTestSources) {
        return expectedBuilder(builderClass, buildersInTestSources, EXPECTED_DEFAULT_BUILDERS_ROOT_DIR);
    }

    static ContainsBuildersCondition expectedBuilders( //
                                                       final Package builderPackage, //
                                                       final boolean buildersInTestSources, //
                                                       final Path expectedBuildersRootDir) {
        return new ContainsBuildersCondition(builderPackage, buildersInTestSources, expectedBuildersRootDir);
    }

    static ContainsBuildersCondition expectedBuilders(final Package builderPackage, final boolean buildersInTestSources) {
        return expectedBuilders(builderPackage, buildersInTestSources, EXPECTED_DEFAULT_BUILDERS_ROOT_DIR);
    }
}
