package io.github.tobi.laa.reflective.fluent.builders.mojo;

import lombok.SneakyThrows;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.codehaus.plexus.logging.Logger;
import org.junit.jupiter.api.AfterEach;
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
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
    private Closer closer;

    @Mock
    private Logger logger;

    private ClassLoader classLoader;

    @BeforeEach
    void injectLogger() {
        provider.enableLogging(logger);
    }

    @AfterEach
    void closeClassLoader() throws IOException {
        if (classLoader != null) {
            ((URLClassLoader) classLoader).close();
        }
    }

    @Test
    @SneakyThrows
    @SuppressWarnings("unused")
    void testGetMalformedURLException() {
        // Arrange
        try (final var mock = mockConstruction(URI.class, (uri, context) -> doThrow(new MalformedURLException("Thrown in unit test")).when(uri).toURL())) {
            mockClasspathElements("doesNotMatter");
            // Act
            final ThrowingCallable get = () -> provider.get();
            // Assert
            assertThatThrownBy(get) //
                    .isExactlyInstanceOf(ClassLoaderProvider.ClassLoaderProviderException.class) //
                    .hasMessageMatching("Error while attempting to convert file .+ to URL.") //
                    .hasCauseExactlyInstanceOf(MalformedURLException.class);
        }
    }

    @Test
    @SneakyThrows
    void testGetDependencyResolutionRequiredException() {
        // Arrange
        final var cause = new DependencyResolutionRequiredException(Mockito.mock(Artifact.class));
        doThrow(cause).when(mavenBuild).getClasspathElements();
        // Act
        final ThrowingCallable get = () -> provider.get();
        // Assert
        assertThatThrownBy(get) //
                .isExactlyInstanceOf(ClassLoaderProvider.ClassLoaderProviderException.class) //
                .hasMessageMatching("Error while resolving dependencies of maven project.") //
                .hasCauseExactlyInstanceOf(DependencyResolutionRequiredException.class);
    }

    @ParameterizedTest
    @MethodSource
    @SneakyThrows
    void testGetNoOldClassLoader(final String[] classpathElements, final URL[] expectedUrls) {
        // Arrange
        mockClasspathElements(classpathElements);
        // Act
        classLoader = provider.get();
        // Assert
        assertThat(classLoader).isNotNull().isInstanceOf(URLClassLoader.class);
        assertThat(((URLClassLoader) classLoader).getURLs()).containsExactlyInAnyOrder(expectedUrls);
        verifyLogAddingToClassLoader(classpathElements);
        verifyNoMoreInteractions(logger);
    }

    private static Stream<Arguments> testGetNoOldClassLoader() {
        return Stream.of( //
                Arguments.of(new String[0], new URL[0]), //
                Arguments.of( //
                        new String[]{"elem1"}, //
                        new URL[]{fileUrl("elem1")}), //
                Arguments.of( //
                        new String[]{"elem1", "elem2"}, //
                        new URL[]{fileUrl("elem1"), fileUrl("elem2")}));
    }

    @Test
    void testGetOldClassLoaderWithSameElementsAsMavenBuild() {
        // Arrange
        mockClasspathElements("elem1");
        final var oldClassLoader = provider.get();
        // Act
        classLoader = provider.get();
        // Assert
        assertThat(classLoader).isSameAs(oldClassLoader);
        verifyLogAddingToClassLoader("elem1");
        verifyNoMoreInteractions(logger);
    }

    @Test
    void testGetOldClassLoaderWithDifferentElementsAsMavenBuild() {
        // Arrange
        doCallRealMethod().when(closer).closeIfCloseable(any());
        mockClasspathElements("elem1");
        final var oldClassLoader = provider.get();
        mockClasspathElements("elem1", "elem2");
        // Act
        classLoader = provider.get();
        // Assert
        assertThat(classLoader).isNotSameAs(oldClassLoader);
        assertThat(classLoader).isNotNull().isInstanceOf(URLClassLoader.class);
        assertThat(((URLClassLoader) classLoader).getURLs()).containsExactlyInAnyOrder(fileUrl("elem1"), fileUrl("elem2"));
        verify(logger, times(2)).debug("Attempt to add elem1 to ClassLoader.");
        verifyLogAddingToClassLoader("elem2");
        verify(logger).debug("ClassLoader will be re-created as elements of underlying maven build have changed.");
        verifyNoMoreInteractions(logger);
        verify(closer).closeIfCloseable(oldClassLoader);
    }

    @SneakyThrows
    private static URL fileUrl(final String file) {
        return new URL("file", "", -1, Paths.get(file).toAbsolutePath().toString());
    }

    @SneakyThrows
    private void mockClasspathElements(final String... elements) {
        doReturn(List.of(elements)).when(mavenBuild).getClasspathElements();
    }

    private void verifyLogAddingToClassLoader(final String... classpathElements) {
        Arrays.stream(classpathElements).forEach(resource -> verify(logger).debug("Attempt to add " + resource + " to ClassLoader."));
    }

    @Test
    void testCloseAndDisposeOfClassLoaderClassLoaderNull() {
        // Act
        provider.closeAndDisposeOfClassLoader();
        // Assert
        assertThat(provider.classLoader).isNull();
        verifyNoInteractions(closer);
    }

    @Test
    @SneakyThrows
    void testCloseAndDisposeOfClassLoaderCloseException() {
        // Arrange
        mockClasspathElements("elem1");
        classLoader = provider.get();
        final var cause = new Closer.CloseException("Thrown in unit test.", null);
        doThrow(cause).when(closer).closeIfCloseable(any());
        // Act
        final ThrowingCallable closeAndDisposeOfClassLoader = () -> provider.closeAndDisposeOfClassLoader();
        // Assert
        assertThatThrownBy(closeAndDisposeOfClassLoader) //
                .isExactlyInstanceOf(ClassLoaderProvider.ClassLoaderProviderException.class) //
                .hasMessage("Error while closing old ClassLoader instance.") //
                .hasCause(cause);
        assertThat(provider.classLoader).isNull();
        verify(closer).closeIfCloseable(classLoader);
    }

    @Test
    void testCloseAndDisposeOfClassLoader() {
        // Arrange
        mockClasspathElements("elem1");
        classLoader = provider.get();
        // Act
        provider.closeAndDisposeOfClassLoader();
        // Assert
        assertThat(provider.classLoader).isNull();
        verify(closer).closeIfCloseable(classLoader);
    }
}
