package io.github.tobi.laa.reflective.fluent.builders.mojo;

import com.google.common.collect.ImmutableList;
import lombok.SneakyThrows;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.project.MavenProject;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.codehaus.plexus.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClassLoaderProviderTest {

    @InjectMocks
    private ClassLoaderProvider provider;

    @Mock
    private MavenBuild mavenBuild;

    @Mock
    private MavenProject mavenProject;

    @Mock
    private Logger logger;

    @BeforeEach
    void injectLogger() {
        provider.enableLogging(logger);
    }

    @Test
    @SneakyThrows
    @SuppressWarnings("unused")
    void testGetMalformedURLException() {
        // Arrange
        try (final MockedConstruction<URI> mock = mockConstruction(URI.class, (uri, context) -> doThrow(new MalformedURLException("Thrown in unit test")).when(uri).toURL())) {
            mockCompileClasspathElements("doesNotMatter");
            // Act
            final ThrowingCallable get = () -> provider.get();
            // Assert
            assertThatThrownBy(get) //
                    .isExactlyInstanceOf(ClassLoaderProvider.ClassLoaderConstructionException.class) //
                    .hasMessageMatching("Error while attempting to convert file .+ to URL.") //
                    .hasCauseExactlyInstanceOf(MalformedURLException.class);
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @SneakyThrows
    void testGetDependencyResolutionRequiredException(final boolean testPhase) {
        // Arrange
        final DependencyResolutionRequiredException cause = new DependencyResolutionRequiredException(Mockito.mock(Artifact.class));
        mockTestPhase(testPhase);
        if (testPhase) {
            doThrow(cause).when(mavenProject).getTestClasspathElements();
        } else {
            doThrow(cause).when(mavenProject).getCompileClasspathElements();
        }
        // Act
        final ThrowingCallable get = () -> provider.get();
        // Assert
        assertThatThrownBy(get) //
                .isExactlyInstanceOf(ClassLoaderProvider.ClassLoaderConstructionException.class) //
                .hasMessageMatching("Error while resolving dependencies of maven project.") //
                .hasCauseExactlyInstanceOf(DependencyResolutionRequiredException.class);
    }

    @ParameterizedTest
    @MethodSource
    @SneakyThrows
    void testGet(final boolean testPhase, final String[] classpathElements, final URL[] expectedUrls) {
        // Arrange
        mockTestPhase(testPhase);
        if (testPhase) {
            mockTestClasspathElements(classpathElements);
        } else {
            mockCompileClasspathElements(classpathElements);
        }
        // Act
        final ClassLoader classLoader = provider.get();
        // Assert
        assertThat(classLoader).isNotNull().isInstanceOf(URLClassLoader.class);
        assertThat(((URLClassLoader) classLoader).getURLs()).containsExactlyInAnyOrder(expectedUrls);
    }

    private static Stream<Arguments> testGet() {
        return Stream.of( //
                Arguments.of(false, new String[0], new URL[0]), //
                Arguments.of(false, new String[0], new URL[0]), //
                Arguments.of( //
                        false, //
                        new String[]{"elem1"}, //
                        new URL[]{fileUrl("elem1")}), //
                Arguments.of( //
                        true, //
                        new String[]{"elem1", "elem2"}, //
                        new URL[]{fileUrl("elem1"), fileUrl("elem2")}));
    }

    @SneakyThrows
    private static URL fileUrl(final String file) {
        return new URL("file", "", -1, Paths.get(file).toAbsolutePath().toString());
    }

    private void mockTestPhase(final boolean testPhase) {
        doReturn(testPhase).when(mavenBuild).isTestPhase();
    }

    @SneakyThrows
    private void mockTestClasspathElements(final String... elements) {
        doReturn(ImmutableList.copyOf(elements)).when(mavenProject).getTestClasspathElements();
    }

    @SneakyThrows
    private void mockCompileClasspathElements(final String... elements) {
        doReturn(ImmutableList.copyOf(elements)).when(mavenProject).getCompileClasspathElements();
    }
}
