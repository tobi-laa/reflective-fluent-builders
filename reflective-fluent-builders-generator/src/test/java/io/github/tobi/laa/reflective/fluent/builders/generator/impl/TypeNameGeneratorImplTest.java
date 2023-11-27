package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.TypeName;
import io.github.tobi.laa.reflective.fluent.builders.model.*;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Type;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.stream.Stream;

import static org.apache.commons.lang3.reflect.TypeUtils.wildcardType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TypeNameGeneratorImplTest {

    private final TypeNameGeneratorImpl generator = new TypeNameGeneratorImpl();

    @Test
    void testGenerateTypeNameForParamSetterNull() {
        // Arrange
        final Setter setter = null;
        // Act
        final Executable generateTypeNameForParam = () -> generator.generateTypeNameForParam(setter);
        // Assert
        assertThrows(NullPointerException.class, generateTypeNameForParam);
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateTypeNameForParamSetter(final Setter setter, final String expected) {
        // Act
        final TypeName actual = generator.generateTypeNameForParam(setter);
        // Assert
        assertThat(actual).hasToString(expected);
    }

    private static Stream<Arguments> testGenerateTypeNameForParamSetter() {
        return Stream.of(
                Arguments.of(
                        SimpleSetter.builder() //
                                .methodName("setAnInt") //
                                .paramName("anInt") //
                                .paramType(int.class) //
                                .visibility(Visibility.PUBLIC) //
                                .declaringClass(SimpleClass.class) //
                                .build(), //
                        "int"
                ),
                Arguments.of(
                        CollectionSetter.builder() //
                                .methodName("setDeque") //
                                .paramName("deque") //
                                .paramType(TypeUtils.parameterize(Deque.class, Object.class)) //
                                .paramTypeArg(wildcardType() //
                                        .withUpperBounds(Object.class) //
                                        .build()) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        "java.util.Deque<java.lang.Object>"
                ),
                Arguments.of(
                        CollectionSetter.builder() //
                                .methodName("setList") //
                                .paramName("list") //
                                .paramType(TypeUtils.parameterize(List.class, Character.class)) //
                                .paramTypeArg(Character.class) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        "java.util.List<java.lang.Character>"
                ),
                Arguments.of(
                        CollectionSetter.builder() //
                                .methodName("setList") //
                                .paramName("list") //
                                .paramType(List.class) //
                                .paramType(TypeUtils.parameterize(List.class, TypeUtils.parameterize(Map.class, String.class, Object.class))) //
                                .paramTypeArg(TypeUtils.parameterize(Map.class, String.class, Object.class)) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        "java.util.List<java.util.Map<java.lang.String, java.lang.Object>>"
                ),
                Arguments.of(
                        ArraySetter.builder() //
                                .methodName("setFloats") //
                                .paramName("floats") //
                                .paramType(float[].class) //
                                .paramComponentType(float.class) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        "float[]"
                ),
                Arguments.of(
                        MapSetter.builder() //
                                .methodName("setMap") //
                                .paramName("map") //
                                .paramType(TypeUtils.parameterize(Map.class, String.class, Object.class)) //
                                .keyType(String.class) //
                                .valueType(Object.class) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        "java.util.Map<java.lang.String, java.lang.Object>"
                ),
                Arguments.of(
                        MapSetter.builder() //
                                .methodName("setMap") //
                                .paramName("map") //
                                .paramType(TypeUtils.parameterize(SortedMap.class, String.class, Object.class)) //
                                .keyType(String.class) //
                                .valueType(wildcardType() //
                                        .withUpperBounds(Object.class) //
                                        .build()) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        "java.util.SortedMap<java.lang.String, java.lang.Object>"
                ));
    }

    @Test
    void testGenerateTypeNameForParamTypeNull() {
        // Arrange
        final Type type = null;
        // Act
        final Executable generateTypeNameForParam = () -> generator.generateTypeNameForParam(type);
        // Assert
        assertThrows(NullPointerException.class, generateTypeNameForParam);
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateTypeNameForParamType(final Type type, final String expected) {
        // Act
        final TypeName actual = generator.generateTypeNameForParam(type);
        // Assert
        assertThat(actual).hasToString(expected);
    }

    private static Stream<Arguments> testGenerateTypeNameForParamType() {
        return Stream.of(
                Arguments.of(int.class, "int"),
                Arguments.of(String.class, "java.lang.String"),
                Arguments.of(wildcardType().withUpperBounds(String.class).build(), "java.lang.String"),
                Arguments.of(wildcardType().build(), "java.lang.Object"));
    }
}