package io.github.tobi.laa.reflective.fluent.builders.mojo;

import io.github.tobi.laa.reflective.fluent.builders.test.InjectMock;
import io.github.tobi.laa.reflective.fluent.builders.test.IntegrationTest;
import lombok.SneakyThrows;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.model.Build;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.project.MavenProject;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.codehaus.plexus.build.BuildContext;
import org.codehaus.plexus.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junitpioneer.jupiter.cartesian.CartesianTest;
import org.junitpioneer.jupiter.cartesian.CartesianTest.Values;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@IntegrationTest
class MavenBuildIT {

    @Inject
    private MavenBuild mavenBuild;

    @InjectMock
    private BuildContext buildContext;

    @InjectMock
    private MavenProject mavenProject;

    @InjectMock
    private MojoExecution mojoExecution;

    @InjectMock
    private Logger logger;

    @TempDir
    private Path tempDir;

    @BeforeEach
    void injectLogger() {
        mavenBuild.enableLogging(logger);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testIsIncremental(final boolean buildContextIncremental) {
        // Arrange
        doReturn(buildContextIncremental).when(buildContext).isIncremental();
        // Act
        final var incremental = mavenBuild.isIncremental();
        // Assert
        assertThat(incremental).isEqualTo(buildContextIncremental);
    }

    @Test
    void testHasDeltaNull() {
        // Act
        final ThrowingCallable hasDelta = () -> mavenBuild.hasDelta(null);
        // Assert
        assertThatThrownBy(hasDelta).isExactlyInstanceOf(NullPointerException.class);
        verifyNoInteractions(buildContext);
    }

    @Test
    void testHasDeltaProjectFile() {
        // Arrange
        final Path basedir = Paths.get("base");
        final Path file = basedir.resolve("sth");
        doReturn(basedir.toFile()).when(mavenProject).getBasedir();
        // Act
        mavenBuild.hasDelta(file.toFile());
        // Assert
        verify(buildContext).hasDelta(file.toFile());
    }

    @Test
    void testHasDeltaExternalFileNotExists() {
        // Arrange
        final Path basedir = Paths.get("base");
        final Path file = Paths.get("somewhere", "over", "the", "rainbow");
        doReturn(basedir.toFile()).when(mavenProject).getBasedir();
        // Act
        final boolean hasDelta = mavenBuild.hasDelta(file.toFile());
        // Assert
        assertThat(hasDelta).isFalse();
    }

    @Test
    @SneakyThrows
    void testHasDeltaExternalFileUnknown() {
        // Arrange
        final Path basedir = Paths.get("base");
        final Path file = tempDir.resolve("dummy");
        Files.createFile(file);
        doReturn(basedir.toFile()).when(mavenProject).getBasedir();
        // Act
        final boolean hasDelta = mavenBuild.hasDelta(file.toFile());
        // Assert
        assertThat(hasDelta).isTrue();
    }

    @Test
    @SneakyThrows
    void testHasDeltaExternalFileNewer() {
        // Arrange
        final Path basedir = Paths.get("base");
        final Path file = tempDir.resolve("dummy");
        Files.createFile(file);
        doReturn(basedir.toFile()).when(mavenProject).getBasedir();
        doReturn(Instant.MIN).when(buildContext).getValue(anyString());
        // Act
        final boolean hasDelta = mavenBuild.hasDelta(file.toFile());
        // Assert
        assertThat(hasDelta).isTrue();
    }

    @Test
    @SneakyThrows
    void testHasDeltaExternalFileNotNewer() {
        // Arrange
        final Path basedir = Paths.get("base");
        final Path file = tempDir.resolve("dummy");
        Files.createFile(file);
        doReturn(basedir.toFile()).when(mavenProject).getBasedir();
        doReturn(Instant.MAX).when(buildContext).getValue(anyString());
        // Act
        final boolean hasDelta = mavenBuild.hasDelta(file.toFile());
        // Assert
        assertThat(hasDelta).isFalse();
    }

    @Test
    void testUpdateModuleBuildTime() {
        // Arrange
        doReturn("com.dummy.group").when(mavenProject).getGroupId();
        doReturn("dummy-artifact").when(mavenProject).getArtifactId();
        doReturn("1.0.0").when(mavenProject).getVersion();
        // Act
        mavenBuild.updateModuleBuildTime();
        // Assert
        verify(buildContext).setValue("com.dummy.group:dummy-artifact:1.0.0:::buildTime", Instant.parse("3333-03-13T00:00:00.00Z"));
    }

    @Test
    void testRefreshNull() {
        // Act
        final ThrowingCallable refresh = () -> mavenBuild.refresh(null);
        // Assert
        assertThatThrownBy(refresh).isExactlyInstanceOf(NullPointerException.class);
        verifyNoInteractions(buildContext);
    }

    @Test
    void testRefresh() {
        // Arrange
        final Path basedir = Paths.get("base");
        final Path file = basedir.resolve("sth");
        // Act
        mavenBuild.refresh(file.toFile());
        // Assert
        verify(buildContext).refresh(file.toFile());
    }

    @ParameterizedTest
    @ValueSource(strings = {"validate", "initialize", "generate-sources", "process-sources", "generate-resources", "process-resources", "compile", "process-classes", "prepare-package", "package", "verify", "install", "deploy"})
    void testIsTestPhaseFalse(final String lifecyclePhase) {
        // Arrange
        doReturn(lifecyclePhase).when(mojoExecution).getLifecyclePhase();
        // Act
        final boolean testPhase = mavenBuild.isTestPhase();
        // Assert
        assertThat(testPhase).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"generate-test-sources", "process-test-sources", "generate-test-resources", "process-test-resources", "test-compile", "process-test-classes", "test", "pre-integration-test", "integration-test", "post-integration-test"})
    void testIsTestPhaseTrue(final String lifecyclePhase) {
        // Arrange
        doReturn(lifecyclePhase).when(mojoExecution).getLifecyclePhase();
        // Act
        final boolean testPhase = mavenBuild.isTestPhase();
        // Assert
        assertThat(testPhase).isTrue();
    }

    @Test
    void testAddCompileSourceRootNull() {
        // Act
        final ThrowingCallable addCompileSourceRoot = () -> mavenBuild.addCompileSourceRoot(null);
        // Assert
        assertThatThrownBy(addCompileSourceRoot).isExactlyInstanceOf(NullPointerException.class);
        verifyNoInteractions(mavenProject);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testAddCompileSourceRoot(final boolean testPhase) {
        // Arrange
        final File path = new File("dummy");
        if (testPhase) {
            doReturn("test").when(mojoExecution).getLifecyclePhase();
        }
        // Act
        mavenBuild.addCompileSourceRoot(path);
        // Assert
        if (testPhase) {
            verify(logger).debug("Add dummy as test source folder.");
            verify(mavenProject).addTestCompileSourceRoot(path.getPath());
        } else {
            verify(logger).debug("Add dummy as source folder.");
            verify(mavenProject).addCompileSourceRoot(path.getPath());
        }
    }

    @Test
    void testGetDirectory() {
        // Arrange
        final var directory = "dummy";
        final var build = Mockito.mock(Build.class);
        doReturn(directory).when(build).getDirectory();
        doReturn(build).when(mavenProject).getBuild();
        // Act
        final var actual = mavenBuild.getDirectory();
        // Assert
        assertThat(actual).isSameAs(directory);
        verify(mavenProject).getBuild();
        verify(build).getDirectory();
    }

    @Test
    void testGetOutputDirectory() {
        // Arrange
        final var outputDirectory = "dummy";
        final var build = Mockito.mock(Build.class);
        doReturn(outputDirectory).when(build).getOutputDirectory();
        doReturn(build).when(mavenProject).getBuild();
        // Act
        final var actual = mavenBuild.getOutputDirectory();
        // Assert
        assertThat(actual).isSameAs(outputDirectory);
        verify(mavenProject).getBuild();
        verify(build).getOutputDirectory();
    }

    @Test
    void testGetTestOutputDirectory() {
        // Arrange
        final var testOutputDirectory = "dummy";
        final var build = Mockito.mock(Build.class);
        doReturn(testOutputDirectory).when(build).getTestOutputDirectory();
        doReturn(build).when(mavenProject).getBuild();
        // Act
        final var actual = mavenBuild.getTestOutputDirectory();
        // Assert
        assertThat(actual).isSameAs(testOutputDirectory);
        verify(mavenProject).getBuild();
        verify(build).getTestOutputDirectory();
    }

    @Test
    void testGetArtifacts() {
        // Arrange
        final var artifacts = emptySet();
        doReturn(artifacts).when(mavenProject).getArtifacts();
        // Act
        final var actual = mavenBuild.getArtifacts();
        // Assert
        assertThat(actual).isSameAs(artifacts);
        verify(mavenProject).getArtifacts();
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @SneakyThrows
    void testGetClasspathElementsDependencyResolutionRequiredException(final boolean testPhase) {
        // Arrange
        final var exception = new DependencyResolutionRequiredException(Mockito.mock(Artifact.class));
        mockTestPhase(testPhase);
        if (testPhase) {
            doThrow(exception).when(mavenProject).getTestClasspathElements();
        } else {
            doThrow(exception).when(mavenProject).getCompileClasspathElements();
        }
        // Act
        final ThrowingCallable getClasspathElements = () -> mavenBuild.getClasspathElements();
        // Assert
        assertThatThrownBy(getClasspathElements).isSameAs(exception);
        if (testPhase) {
            verify(mavenProject).getTestClasspathElements();
        } else {
            verify(mavenProject).getCompileClasspathElements();
        }
        verifyNoMoreInteractions(mavenProject);
    }

    @ParameterizedTest
    @MethodSource
    @SneakyThrows
    void testGetClasspathElements(final boolean testPhase, final String[] expected) {
        // Arrange
        mockTestPhase(testPhase);
        if (testPhase) {
            mockTestClasspathElements(expected);
        } else {
            mockCompileClasspathElements(expected);
        }
        // Act
        final var actual = mavenBuild.getClasspathElements();
        // Assert
        assertThat(actual).containsExactlyInAnyOrder(expected);
        if (testPhase) {
            verify(mavenProject).getTestClasspathElements();
        } else {
            verify(mavenProject).getCompileClasspathElements();
        }
        verifyNoMoreInteractions(mavenProject);
    }

    private static Stream<Arguments> testGetClasspathElements() {
        return Stream.of( //
                Arguments.of(false, new String[0]), //
                Arguments.of(true, new String[0]), //
                Arguments.of(false, new String[]{"elem1"}),
                Arguments.of(true, new String[]{"elem1", "elem2"}));
    }

    private void mockTestPhase(final boolean testPhase) {
        if (testPhase) {
            doReturn("generate-test-sources").when(mojoExecution).getLifecyclePhase();
        } else {
            doReturn("generate-sources").when(mojoExecution).getLifecyclePhase();
        }
    }

    @SneakyThrows
    private void mockTestClasspathElements(final String... elements) {
        doReturn(List.of(elements)).when(mavenProject).getTestClasspathElements();
    }

    @SneakyThrows
    private void mockCompileClasspathElements(final String... elements) {
        doReturn(List.of(elements)).when(mavenProject).getCompileClasspathElements();
    }

    @CartesianTest
    @SneakyThrows
    void testResolveSourceFileUnderneathCompileSource(
            @Values(booleans = {true, false}) final boolean testPhase,
            @Values(strings = {"exists.for.test", "exists.for.compile", "does.not.exist"}) final String packageName,
            @Values(strings = {"ExistsForTest.java", "ExistsForCompile.java", "DoesNotExist.java"}) final String sourceFile) {
        // Arrange
        final var testRoot = tempDir.resolve("testroot");
        final var testSource = testRoot.resolve("exists").resolve("for").resolve("test").resolve("ExistsForTest.java");
        Files.createDirectories(testSource.getParent());
        Files.createFile(testSource);
        final var compileRoot = tempDir.resolve("compileroot");
        final var compileSource = compileRoot.resolve("exists").resolve("for").resolve("compile").resolve("ExistsForCompile.java");
        Files.createDirectories(compileSource.getParent());
        Files.createFile(compileSource);
        mockTestPhase(testPhase);
        doReturn(List.of(testRoot.toString())).when(mavenProject).getTestCompileSourceRoots();
        doReturn(List.of(compileRoot.toString())).when(mavenProject).getCompileSourceRoots();
        // Act
        final var actual = mavenBuild.resolveSourceFile(packageName, Paths.get(sourceFile));
        // Assert
        if (packageName.contains("compile") && sourceFile.equals("ExistsForCompile.java")
                || testPhase && packageName.contains("test") && sourceFile.equals("ExistsForTest.java")) {
            assertThat(actual).isPresent();
        } else {
            assertThat(actual).isEmpty();
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testResolveSourceFileUnderneathTestCompileSource(final boolean fileExists) {
        mockTestPhase(true);
    }
}
