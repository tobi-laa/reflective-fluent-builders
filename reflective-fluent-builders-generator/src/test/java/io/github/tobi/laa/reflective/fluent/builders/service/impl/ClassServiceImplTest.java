package io.github.tobi.laa.reflective.fluent.builders.service.impl;

import io.github.tobi.laa.reflective.fluent.builders.exception.ReflectionException;
import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import com.google.common.reflect.ClassPath;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.*;
import io.github.tobi.laa.reflective.fluent.builders.test.models.nested.NestedMarker;
import io.github.tobi.laa.reflective.fluent.builders.test.models.nested.TopLevelClass;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.Simple;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClassNoDefaultConstructor;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClassNoSetPrefix;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.hierarchy.Child;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.hierarchy.Parent;
import lombok.SneakyThrows;
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
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
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
    void testCollectFullClassHierarchy(final Class<?> clazz, final Set<Predicate<Class<?>>> excludes, final Set<Class<?>> expected) {
        // Arrange
        final var hierarchyCollection = Mockito.mock(BuildersProperties.HierarchyCollection.class);
        when(properties.getHierarchyCollection()).thenReturn(hierarchyCollection);
        when(hierarchyCollection.getExcludes()).thenReturn(excludes);
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
                        Set.of( //
                                ClassWithHierarchy.class, //
                                FirstSuperClass.class, //
                                TopLevelSuperClass.class, //
                                AnInterface.class, //
                                AnotherInterface.class, //
                                Object.class)), //
                Arguments.of( //
                        ClassWithHierarchy.class, //
                        Set.<Predicate<Class<?>>>of(Object.class::equals), //
                        Set.of( //
                                ClassWithHierarchy.class, //
                                FirstSuperClass.class, //
                                TopLevelSuperClass.class, //
                                AnInterface.class, //
                                AnotherInterface.class)), //
                Arguments.of( //
                        ClassWithHierarchy.class, //
                        Set.<Predicate<Class<?>>>of(Object.class::equals, AnInterface.class::equals), //
                        Set.of( //
                                ClassWithHierarchy.class, //
                                FirstSuperClass.class, //
                                TopLevelSuperClass.class, //
                                AnotherInterface.class)), //
                Arguments.of( //
                        ClassWithHierarchy.class, //
                        Set.<Predicate<Class<?>>>of(FirstSuperClass.class::equals), //
                        Set.of( //
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
            final var cause = new IOException("Thrown in unit test");
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
                .anyMatch(Set.of(Test.class, ParameterizedTest.class)::contains);
    }

    @SneakyThrows
    private static Stream<Arguments> testCollectClassesRecursively() {
        return Stream.of( //
                Arguments.of(
                        Simple.class.getPackageName(), //
                        Set.of( //
                                Child.class, //
                                Parent.class, //
                                Simple.class, //
                                SimpleClass.class, //
                                SimpleClassNoDefaultConstructor.class, //
                                SimpleClassNoSetPrefix.class)),
                Arguments.of(
                        NestedMarker.class.getPackageName(), //
                        Set.of( //
                                NestedMarker.class, //
                                TopLevelClass.class, //
                                TopLevelClass.NestedPublicLevelOne.class, //
                                Class.forName(TopLevelClass.class.getName() + "$NestedProtectedLevelOne"), //
                                Class.forName(TopLevelClass.class.getName() + "$NestedPackagePrivateLevelOne"), //
                                Class.forName(TopLevelClass.class.getName() + "$NestedPrivateLevelOne"), //
                                TopLevelClass.NestedNonStatic.class, //
                                TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.class, //
                                TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree.class)));
    }
}