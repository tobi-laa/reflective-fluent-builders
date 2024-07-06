package io.github.tobi.laa.reflective.fluent.builders.mojo;

import lombok.SneakyThrows;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.codehaus.plexus.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CloserTest {

    @InjectMocks
    private Closer closer;

    @Mock
    private Logger logger;

    @BeforeEach
    void injectLogger() {
        closer.enableLogging(logger);
    }

    @Test
    void testCloseIfCloseableNull() {
        // Arrange
        final Object object = null;
        // Act
        final ThrowingCallable close = () -> closer.closeIfCloseable(object);
        // Assert
        assertThatThrownBy(close).isExactlyInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @MethodSource
    @SneakyThrows
    void testCloseIfCloseableNonCloseable(final Object object) {
        // Act
        closer.closeIfCloseable(object);
        // Assert
        verify(logger).debug("Not closing " + object + " of type " + object.getClass() + " as it does not implements Closeable.");
    }

    static Stream<Object> testCloseIfCloseableNonCloseable() {
        return Stream.of("aString", new Object(), 6, new ArrayList<>());
    }

    @Test
    @SneakyThrows
    void testCloseIfCloseableIOException() {
        // Arrange
        final Closeable closeable = mock(Closeable.class);
        final IOException cause = new IOException("Thrown in unit test.");
        doThrow(cause).when(closeable).close();
        // Act
        final ThrowingCallable close = () -> closer.closeIfCloseable(closeable);
        // Assert
        assertThatThrownBy(close) //
                .isExactlyInstanceOf(Closer.CloseException.class)
                .hasMessageMatching("Error while attempting to close .+\\.")
                .hasCause(cause);
    }

    @Test
    @SneakyThrows
    void testCloseIfCloseable() {
        // Arrange
        final Closeable closeable = mock(Closeable.class);
        // Act
        closer.closeIfCloseable(closeable);
        // Assert
        verify(closeable).close();
    }
}