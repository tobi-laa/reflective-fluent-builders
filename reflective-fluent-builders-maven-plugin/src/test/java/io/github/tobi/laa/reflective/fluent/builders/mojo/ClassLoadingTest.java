package io.github.tobi.laa.reflective.fluent.builders.mojo;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.SneakyThrows;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.handler.ArtifactHandler;
import org.apache.maven.artifact.versioning.VersionRange;
import org.apache.maven.plugin.MojoExecutionException;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.codehaus.plexus.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Stream;

import static com.google.common.base.MoreObjects.firstNonNull;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class ClassLoadingTest {

    private static ClassLoader oldClassLoader;

    @InjectMocks
    private ClassLoading classLoading;

    @Mock
    private MavenBuild mavenBuild;

    @Mock
    private MojoParams params;

    @Mock
    private Logger logger;

    @BeforeAll
    static void storeOldClassLoader() {
        ClassLoadingTest.oldClassLoader = Thread.currentThread().getContextClassLoader();
    }

    @BeforeEach
    void injectLogger() {
        classLoading.enableLogging(logger);
    }

    @AfterEach
    void resetClassLoader() {
        Thread.currentThread().setContextClassLoader(oldClassLoader);
    }

    @Test
    @SneakyThrows
    void testSetThreadClassLoaderToArtifactIncludingClassLoaderMalformedURLException() {
        // Arrange
        mockOutputDirectory();
        mockScopesToInclude("compile");
        //
        final File file = Mockito.mock(File.class);
        final URI uri = Mockito.mock(URI.class);
        doReturn(uri).when(file).toURI();
        doThrow(new MalformedURLException("Thrown in unit test")).when(uri).toURL();
        //
        final Artifact artifact = artifact().file(file).scope("compile").build();
        mockArtifacts(singleton(artifact));
        // Act
        final ThrowingCallable setThreadClassLoaderToArtifactIncludingClassLoader = () -> classLoading.setThreadClassLoaderToArtifactIncludingClassLoader();
        // Assert
        assertThatThrownBy(setThreadClassLoaderToArtifactIncludingClassLoader) //
                .isExactlyInstanceOf(MojoExecutionException.class) //
                .hasMessageMatching("Error while attempting to convert file .+ to URL.") //
                .hasCauseExactlyInstanceOf(MalformedURLException.class);
        assertThat(classLoading.oldClassLoader).isSameAs(ClassLoadingTest.oldClassLoader);
        assertThat(classLoading.artifactIncludingClassLoader).isNull();
    }

    @ParameterizedTest
    @MethodSource
    @SneakyThrows
    void testSetThreadClassLoaderToArtifactIncludingClassLoader( //
                                                                 final boolean testPhase, //
                                                                 final String[] scopesToIncludes, //
                                                                 final Set<Artifact> artifacts,
                                                                 final URL[] expectedUrls) {
        // Arrange
        mockOutputDirectory();
        doReturn(testPhase).when(mavenBuild).isTestPhase();
        if (testPhase) {
            mockTestOutputDirectory();
        }
        if (!artifacts.isEmpty()) {
            mockScopesToInclude(scopesToIncludes);
        }
        mockArtifacts(artifacts);
        // Act
        classLoading.setThreadClassLoaderToArtifactIncludingClassLoader();
        // Assert
        assertThat(classLoading.oldClassLoader).isSameAs(ClassLoadingTest.oldClassLoader);
        assertThat(classLoading.artifactIncludingClassLoader).isNotNull();
        assertThat(classLoading.artifactIncludingClassLoader.getURLs()).containsExactlyInAnyOrder(expectedUrls);
    }

    private static Stream<Arguments> testSetThreadClassLoaderToArtifactIncludingClassLoader() {
        return Stream.of( //
                Arguments.of(false, new String[0], emptySet(), new URL[]{fileUrl("dummyOutput")}), //
                Arguments.of(true, new String[0], emptySet(), new URL[]{fileUrl("dummyOutput"), fileUrl("dummyTestOutput")}), //
                Arguments.of( //
                        false, //
                        new String[]{"compile"}, //
                        ImmutableSet.of( //
                                artifact().artifactId("no1").file(new File("artifact1")).scope("compile").build(), //
                                artifact().artifactId("no2").file(new File("artifact2")).scope("test").build()), //
                        new URL[]{fileUrl("dummyOutput"), fileUrl("artifact1")}), //
                Arguments.of( //
                        false, //
                        new String[]{"compile", "test"}, //
                        ImmutableSet.of( //
                                artifact().artifactId("no1").file(new File("artifact1")).scope("compile").build(), //
                                artifact().artifactId("no2").file(new File("artifact2")).scope("test").build()), //
                        new URL[]{fileUrl("dummyOutput"), fileUrl("artifact1"), fileUrl("artifact2")}));
    }

    @SneakyThrows
    private static URL fileUrl(final String file) {
        return new URL("file", "", -1, Paths.get(file).toAbsolutePath().toString());
    }

    @Test
    @SneakyThrows
    void testResetThreadClassLoaderIOException() {
        // Arrange
        classLoading.oldClassLoader = ClassLoadingTest.oldClassLoader;
        classLoading.artifactIncludingClassLoader = Mockito.mock(URLClassLoader.class);
        doThrow(new IOException("Thrown in unit test")).when(classLoading.artifactIncludingClassLoader).close();
        // Act
        final ThrowingCallable resetThreadClassLoader = () -> classLoading.resetThreadClassLoader();
        // Assert
        assertThatThrownBy(resetThreadClassLoader) //
                .isExactlyInstanceOf(MojoExecutionException.class) //
                .hasMessageMatching("Error while attempting to close ClassLoader.") //
                .hasCauseExactlyInstanceOf(IOException.class);
        assertThat(Thread.currentThread().getContextClassLoader()).isSameAs(ClassLoadingTest.oldClassLoader);
        assertThat(classLoading.oldClassLoader).isNull();
        assertThat(classLoading.artifactIncludingClassLoader).isNull();
    }

    @Test
    @SneakyThrows
    void testResetThreadClassLoader() {
        // Arrange
        classLoading.oldClassLoader = ClassLoadingTest.oldClassLoader;
        classLoading.artifactIncludingClassLoader = new URLClassLoader(new URL[0], oldClassLoader);
        // Act
        classLoading.resetThreadClassLoader();
        // Assert
        assertThat(Thread.currentThread().getContextClassLoader()).isSameAs(ClassLoadingTest.oldClassLoader);
        assertThat(classLoading.oldClassLoader).isNull();
        assertThat(classLoading.artifactIncludingClassLoader).isNull();
    }

    @Test
    @SneakyThrows
    void testLoadClassClassNotFoundException() {
        // Arrange
        final ClassLoader classLoader = Mockito.mock(ClassLoader.class);
        doThrow(new ClassNotFoundException("Thrown in unit test")).when(classLoader).loadClass(anyString());
        Thread.currentThread().setContextClassLoader(classLoader);
        // Act
        final ThrowingCallable loadClass = () -> classLoading.loadClass("does.not.matter");
        // Assert
        assertThatThrownBy(loadClass) //
                .isExactlyInstanceOf(MojoExecutionException.class) //
                .hasMessageMatching("Unable to load class does.not.matter") //
                .hasCauseExactlyInstanceOf(ClassNotFoundException.class);
    }

    @Test
    @SneakyThrows
    void testLoadClass() {
        // Act
        final Class<?> actual = classLoading.loadClass(String.class.getName());
        // Assert
        assertThat(actual).isEqualTo(String.class);
    }

    private void mockOutputDirectory() {
        doReturn("dummyOutput").when(mavenBuild).getOutputDirectory();
    }

    private void mockTestOutputDirectory() {
        doReturn("dummyTestOutput").when(mavenBuild).getTestOutputDirectory();
    }

    private void mockScopesToInclude(final String... scopes) {
        doReturn(ImmutableSet.of(scopes)).when(params).getScopesToInclude();
    }

    private void mockArtifacts(final Set<Artifact> artifacts) {
        doReturn(artifacts).when(mavenBuild).getArtifacts();
    }

    @Builder(builderMethodName = "artifact")
    @SneakyThrows
    private static Artifact newArtifact(final String groupId, //
                                        final String artifactId, //
                                        final VersionRange versionRange, //
                                        final String scope, //
                                        final String type, //
                                        final String classifier, //
                                        final ArtifactHandler artifactHandler, //
                                        final boolean optional, //
                                        final File file) {
        final Artifact artifact = new DefaultArtifact( //
                firstNonNull(groupId, "com.dummy"), //
                firstNonNull(artifactId, "artfiact"), //
                firstNonNull(versionRange, VersionRange.createFromVersionSpec("1.0")), //
                scope, //
                firstNonNull(type, "jar"), //
                firstNonNull(classifier, "dummy"), //
                artifactHandler, //
                optional);
        artifact.setFile(file);
        return artifact;
    }
}
