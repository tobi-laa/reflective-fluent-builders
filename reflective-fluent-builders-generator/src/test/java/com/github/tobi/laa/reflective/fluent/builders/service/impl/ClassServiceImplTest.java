package com.github.tobi.laa.reflective.fluent.builders.service.impl;

import com.github.tobi.laa.reflective.fluent.builders.exception.ReflectionException;
import com.github.tobi.laa.reflective.fluent.builders.model.*;
import com.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.*;
import com.google.common.reflect.ClassPath;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

class ClassServiceImplTest {

    @Test
    void testCollectFullClassHierarchyNull() {
        // Arrange
        final ClassServiceImpl classServiceImpl = new ClassServiceImpl();
        // Act
        final Executable collectFullClassHierarchy = () -> classServiceImpl.collectFullClassHierarchy(null);
        // Assert
        assertThrows(NullPointerException.class, collectFullClassHierarchy);
    }

    @ParameterizedTest
    @MethodSource
    void testCollectFullClassHierarchy(final Class<?> clazz, final Class<?>[] excludes, final Set<Class<?>> expected) {
        // Arrange
        final ClassServiceImpl classServiceImpl = new ClassServiceImpl(excludes);
        // Act
        final Set<Class<?>> actual = classServiceImpl.collectFullClassHierarchy(clazz);
        // Assert
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> testCollectFullClassHierarchy() {
        return Stream.of( //
                Arguments.of( //
                        ClassWithHierarchy.class, //
                        new Class<?>[0], //
                        Set.of( //
                                ClassWithHierarchy.class, //
                                FirstSuperClass.class, //
                                TopLevelSuperClass.class, //
                                AnInterface.class, //
                                AnotherInterface.class, //
                                Object.class)), //
                Arguments.of( //
                        ClassWithHierarchy.class, //
                        new Class<?>[]{Object.class}, //
                        Set.of( //
                                ClassWithHierarchy.class, //
                                FirstSuperClass.class, //
                                TopLevelSuperClass.class, //
                                AnInterface.class, //
                                AnotherInterface.class)), //
                Arguments.of( //
                        ClassWithHierarchy.class, //
                        new Class<?>[]{Object.class, AnInterface.class}, //
                        Set.of( //
                                ClassWithHierarchy.class, //
                                FirstSuperClass.class, //
                                TopLevelSuperClass.class, //
                                AnotherInterface.class)), //
                Arguments.of( //
                        ClassWithHierarchy.class, //
                        new Class<?>[]{FirstSuperClass.class}, //
                        Set.of( //
                                ClassWithHierarchy.class, //
                                AnInterface.class)));
    }

    @Test
    void testCollectClassesRecursivelyNull() {
        // Arrange
        final ClassServiceImpl classServiceImpl = new ClassServiceImpl();
        // Act
        final Executable collectClassesRecursively = () -> classServiceImpl.collectClassesRecursively(null);
        // Assert
        assertThrows(NullPointerException.class, collectClassesRecursively);
    }

    @Test
    void testCollectClassesRecursivelyReflectionException() {
        try (final MockedStatic<ClassPath> classPath = mockStatic(ClassPath.class)) {
            // Arrange
            final var cause = new IOException("Thrown in unit test");
            classPath.when(() -> ClassPath.from(any())).thenThrow(cause);
            final ClassServiceImpl classServiceImpl = new ClassServiceImpl();
            // Act
            final ThrowableAssert.ThrowingCallable collectClassesRecursively = () -> classServiceImpl.collectClassesRecursively("");
            // Assert
            assertThatThrownBy(collectClassesRecursively)
                    .isInstanceOf(ReflectionException.class)
                    .hasMessage("Error while attempting to collect classes recursively.")
                    .hasCause(cause);
        }
    }

    @ParameterizedTest
    @MethodSource
    void testCollectClassesRecursively(final String packageName, final Set<Class<?>> expected) {
        // Arrange
        final ClassServiceImpl classServiceImpl = new ClassServiceImpl();
        // Act
        final Set<Class<?>> actual = classServiceImpl.collectClassesRecursively(packageName);
        // Assert
        assertThat(actual).filteredOn(not(this::isTestClass)).containsExactlyInAnyOrderElementsOf(expected);
    }

    private boolean isTestClass(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .map(Method::getDeclaredAnnotations)
                .flatMap(Arrays::stream)
                .map(Annotation::annotationType)
                .anyMatch(Set.of(Test.class, ParameterizedTest.class)::contains);
    }

    private static Stream<Arguments> testCollectClassesRecursively() {
        return Stream.of( //
                Arguments.of(
                        Setter.class.getPackageName(), //
                        Set.of( //
                                AbstractSetter.class, //
                                ArraySetter.class, //
                                BuilderMetadata.class, //
                                CollectionSetter.class, //
                                MapSetter.class, //
                                Setter.class, //
                                SimpleSetter.class, //
                                Visibility.class)) //
        );
    }
}