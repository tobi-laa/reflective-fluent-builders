package com.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.github.tobi.laa.reflective.fluent.builders.model.*;
import com.squareup.javapoet.TypeName;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SetterTypeNameGeneratorImplTest {

    private final SetterTypeNameGeneratorImpl generator = new SetterTypeNameGeneratorImpl();

    @Test
    void testGenerateTypeNameForParamNull() {
        // Arrange
        final Setter setter = null;
        // Act
        final Executable generateTypeNameForParam = () -> generator.generateTypeNameForParam(setter);
        // Assert
        assertThrows(NullPointerException.class, generateTypeNameForParam);
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateTypeNameForParam(final Setter setter, final String expected) {
        // Act
        final TypeName actual = generator.generateTypeNameForParam(setter);
        // Assert
        assertThat(actual).hasToString(expected);
    }

    private static Stream<Arguments> testGenerateTypeNameForParam() {
        return Stream.of(
                Arguments.of(
                        SimpleSetter.builder() //
                                .methodName("setAnInt") //
                                .paramName("anInt") //
                                .paramType(int.class) //
                                .visibility(Visibility.PUBLIC) //
                                .build(), //
                        "int"
                ),
                Arguments.of(
                        CollectionSetter.builder() //
                                .methodName("setDeque") //
                                .paramName("deque") //
                                .paramType(Deque.class) //
                                .paramTypeArg(TypeUtils.wildcardType() //
                                        .withUpperBounds(Object.class) //
                                        .build()) //
                                .visibility(Visibility.PRIVATE) //
                                .build(), //
                        "java.util.Deque<?>"
                ),
                Arguments.of(
                        CollectionSetter.builder() //
                                .methodName("setList") //
                                .paramName("list") //
                                .paramType(List.class) //
                                .paramTypeArg(Character.class) //
                                .visibility(Visibility.PRIVATE) //
                                .build(), //
                        "java.util.List<java.lang.Character>"
                ),
                Arguments.of(
                        CollectionSetter.builder() //
                                .methodName("setList") //
                                .paramName("list") //
                                .paramType(List.class) //
                                .paramTypeArg(TypeUtils.parameterize(Map.class, String.class, Object.class)) //
                                .visibility(Visibility.PRIVATE) //
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
                                .build(), //
                        "float[]"
                ),
                Arguments.of(
                        MapSetter.builder() //
                                .methodName("setMap") //
                                .paramName("map") //
                                .paramType(Map.class) //
                                .keyType(String.class) //
                                .valueType(Object.class) //
                                .visibility(Visibility.PRIVATE) //
                                .build(), //
                        "java.util.Map<java.lang.String, java.lang.Object>"
                ),
                Arguments.of(
                        MapSetter.builder() //
                                .methodName("setMap") //
                                .paramName("map") //
                                .paramType(SortedMap.class) //
                                .keyType(String.class) //
                                .valueType(TypeUtils.wildcardType() //
                                        .withUpperBounds(Object.class) //
                                        .build()) //
                                .visibility(Visibility.PRIVATE) //
                                .build(), //
                        "java.util.SortedMap<java.lang.String, ?>"
                ));
    }
}