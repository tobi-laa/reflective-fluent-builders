package io.github.tobi.laa.reflective.fluent.builders.service.impl;

import io.github.tobi.laa.reflective.fluent.builders.model.Visibility;
import lombok.SneakyThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VisibilityServiceImplTest {

    private final VisibilityServiceImpl visibilityService = new VisibilityServiceImpl();

    @ParameterizedTest
    @MethodSource
    void testToVisibility(final int modifiers, final Visibility expected) {
        // Act
        final Visibility actual = visibilityService.toVisibility(modifiers);
        // Assert
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> testToVisibility() {
        return Stream.of( //
                Arguments.of(modifiersOf("privateMethod"), Visibility.PRIVATE), //
                Arguments.of(modifiersOf("privateStaticMethod"), Visibility.PRIVATE), //
                Arguments.of(modifiersOf("protectedMethod"), Visibility.PROTECTED), //
                Arguments.of(modifiersOf("protectedStaticMethod"), Visibility.PROTECTED), //
                Arguments.of(modifiersOf("packagePrivateMethod"), Visibility.PACKAGE_PRIVATE), //
                Arguments.of(modifiersOf("packagePrivateStaticMethod"), Visibility.PACKAGE_PRIVATE), //
                Arguments.of(modifiersOf("publicMethod"), Visibility.PUBLIC), //
                Arguments.of(modifiersOf("publicStaticMethod"), Visibility.PUBLIC));
    }

    @SneakyThrows
    private static int modifiersOf(final String name) {
        return SomeMethods.class.getDeclaredMethod(name).getModifiers();
    }

    @SuppressWarnings({"unused", "java:S1186"})
    private static class SomeMethods {
        private void privateMethod() {
        }

        private static void privateStaticMethod() {
        }

        protected void protectedMethod() {
        }

        protected static void protectedStaticMethod() {
        }

        void packagePrivateMethod() {
        }

        static void packagePrivateStaticMethod() {
        }

        public void publicMethod() {
        }

        public static void publicStaticMethod() {
        }
    }
}