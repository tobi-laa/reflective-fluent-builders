package com.github.tobi.laa.reflective.fluent.builders.service.impl;

import com.github.tobi.laa.reflective.fluent.builders.exception.ReflectionException;
import com.github.tobi.laa.reflective.fluent.builders.model.*;
import com.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import com.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.*;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import static com.google.common.base.Predicates.not;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClassServiceImplTest {

    @InjectMocks
    private ClassServiceImpl classServiceImpl;

    @Mock
    private BuildersProperties properties;

    @Test
    void testCollectFullClassHierarchyNull() {
        // Act
        final Executable collectFullClassHierarchy = () -> classServiceImpl.collectFullClassHierarchy(null);
        // Assert
        assertThrows(NullPointerException.class, collectFullClassHierarchy);
    }

    @ParameterizedTest
    @MethodSource
    void testCollectFullClassHierarchy(final Class<?> clazz, final Set<Class<?>> excludes, final Set<Class<?>> expected) {
        // Arrange
        final BuildersProperties.HierarchyCollection hierarchyCollection = Mockito.mock(BuildersProperties.HierarchyCollection.class);
        when(properties.getHierarchyCollection()).thenReturn(hierarchyCollection);
        when(hierarchyCollection.getClassesToExclude()).thenReturn(excludes);
        // Act
        final Set<Class<?>> actual = classServiceImpl.collectFullClassHierarchy(clazz);
        // Assert
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> testCollectFullClassHierarchy() {
        return Stream.of( //
                Arguments.of( //
                        ClassWithHierarchy.class, //
                        Collections.emptySet(), //
                        ImmutableSet.of( //
                                ClassWithHierarchy.class, //
                                FirstSuperClass.class, //
                                TopLevelSuperClass.class, //
                                AnInterface.class, //
                                AnotherInterface.class, //
                                Object.class)), //
                Arguments.of( //
                        ClassWithHierarchy.class, //
                        Collections.singleton(Object.class), //
                        ImmutableSet.of( //
                                ClassWithHierarchy.class, //
                                FirstSuperClass.class, //
                                TopLevelSuperClass.class, //
                                AnInterface.class, //
                                AnotherInterface.class)), //
                Arguments.of( //
                        ClassWithHierarchy.class, //
                        ImmutableSet.of(Object.class, AnInterface.class), //
                        ImmutableSet.of( //
                                ClassWithHierarchy.class, //
                                FirstSuperClass.class, //
                                TopLevelSuperClass.class, //
                                AnotherInterface.class)), //
                Arguments.of( //
                        ClassWithHierarchy.class, //
                        Collections.singleton(FirstSuperClass.class), //
                        ImmutableSet.of( //
                                ClassWithHierarchy.class, //
                                AnInterface.class)));
    }

    @Test
    void testCollectClassesRecursivelyNull() {
        // Act
        final Executable collectClassesRecursively = () -> classServiceImpl.collectClassesRecursively(null);
        // Assert
        assertThrows(NullPointerException.class, collectClassesRecursively);
    }

    @Test
    void testCollectClassesRecursivelyReflectionException() {
        try (final MockedStatic<ClassPath> classPath = mockStatic(ClassPath.class)) {
            // Arrange
            final IOException cause = new IOException("Thrown in unit test");
            classPath.when(() -> ClassPath.from(any())).thenThrow(cause);
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
                .anyMatch(ImmutableSet.of(Test.class, ParameterizedTest.class)::contains);
    }

    private static Stream<Arguments> testCollectClassesRecursively() {
        return Stream.of( //
                Arguments.of(
                        Setter.class.getPackage().getName(), //
                        ImmutableSet.of( //
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