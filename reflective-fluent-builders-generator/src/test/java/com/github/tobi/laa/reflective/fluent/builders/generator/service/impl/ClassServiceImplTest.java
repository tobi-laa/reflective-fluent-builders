package com.github.tobi.laa.reflective.fluent.builders.generator.service.impl;

import com.github.tobi.laa.reflective.fluent.builders.generator.model.*;
import com.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @ParameterizedTest
    @MethodSource
    void testCollectClassesRecursively(final String packageName, final Set<Class<?>> expected) {
        // Arrange
        final ClassServiceImpl classServiceImpl = new ClassServiceImpl();
        // Act
        final Set<Class<?>> actual = classServiceImpl.collectClassesRecursively(packageName);
        // Assert
        assertThat(actual).isEqualTo(expected);
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