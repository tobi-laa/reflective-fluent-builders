package com.github.tobi.laa.reflective.fluent.builders.generator.service.impl;

import com.github.tobi.laa.reflective.fluent.builders.generator.exception.CodeGenerationException;
import com.github.tobi.laa.reflective.fluent.builders.generator.model.MapSetter;
import com.github.tobi.laa.reflective.fluent.builders.generator.model.Visibility;
import com.squareup.javapoet.CodeBlock;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.EnumMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class EnumMapInitializerCodeBlockGeneratorServiceImplTest {

    private final EnumMapInitializerCodeBlockerGeneratorServiceImpl enumMapInitializerCodeBlockerGeneratorServiceImpl = new EnumMapInitializerCodeBlockerGeneratorServiceImpl();

    @Test
    void testIsApplicableNull() {
        // Act
        final Executable isApplicable = () -> enumMapInitializerCodeBlockerGeneratorServiceImpl.isApplicable(null);
        // Assert
        assertThrows(NullPointerException.class, isApplicable);
    }

    @ParameterizedTest
    @MethodSource
    void testIsApplicableTrue(final MapSetter mapSetter) {
        // Act
        final boolean isApplicable = enumMapInitializerCodeBlockerGeneratorServiceImpl.isApplicable(mapSetter);
        // Assert
        assertTrue(isApplicable);
    }

    private static Stream<MapSetter> testIsApplicableTrue() {
        return testGenerateMapInitializer().map(args -> args.get()[0]).map(MapSetter.class::cast);
    }

    @ParameterizedTest
    @MethodSource
    void testIsApplicableFalse(final MapSetter mapSetter) {
        // Act
        final boolean isApplicable = enumMapInitializerCodeBlockerGeneratorServiceImpl.isApplicable(mapSetter);
        // Assert
        assertFalse(isApplicable);
    }

    private static Stream<MapSetter> testIsApplicableFalse() {
        return Stream.concat(
                        SimpleMapInitializerCodeBlockGeneratorServiceImpl.SUPPORTED_MAPS.stream(),
                        Stream.of(Set.class, List.class, SimpleCollectionInitializerCodeBlockGeneratorServiceImplTest.MyList.class))
                .map(type -> MapSetter.builder() //
                        .paramName("") //
                        .paramType(type) //
                        .keyType(Object.class) //
                        .valueType(Object.class)
                        .methodName("") //
                        .visibility(Visibility.PRIVATE) //
                        .build());
    }

    @Test
    void testGenerateMapInitializerNull() {
        // Act
        final Executable generateCollectionInitializer = () -> enumMapInitializerCodeBlockerGeneratorServiceImpl.generateMapInitializer(null);
        // Assert
        assertThrows(NullPointerException.class, generateCollectionInitializer);
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateMapInitializerCodeGenerationException(final MapSetter mapSetter) {
        // Act
        final ThrowableAssert.ThrowingCallable generateMapInitializer = () -> enumMapInitializerCodeBlockerGeneratorServiceImpl.generateMapInitializer(mapSetter);
        // Assert
        assertThatThrownBy(generateMapInitializer)
                .isInstanceOf(CodeGenerationException.class)
                .message()
                .matches("Generation of initializing code blocks for .+ is not supported.")
                .contains(mapSetter.getParamType().getName());
    }

    private static Stream<MapSetter> testGenerateMapInitializerCodeGenerationException() {
        return testIsApplicableFalse();
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateMapInitializer(final MapSetter mapSetter, final CodeBlock expected) {
        // Act
        final CodeBlock actual = enumMapInitializerCodeBlockerGeneratorServiceImpl.generateMapInitializer(mapSetter);
        // Assert
        assertThat(actual).hasToString(expected.toString());
    }

    private static Stream<Arguments> testGenerateMapInitializer() {
        return Stream.of(
                Arguments.of(
                        MapSetter.builder() //
                                .paramName("") //
                                .paramType(EnumMap.class) //
                                .keyType(EnumA.class) //
                                .valueType(Object.class) //
                                .methodName("") //
                                .visibility(Visibility.PRIVATE) //
                                .build(),
                        CodeBlock.builder().add("new java.util.EnumMap<>(com.github.tobi.laa.reflective.fluent.builders.generator.service.impl.EnumMapInitializerCodeBlockGeneratorServiceImplTest.EnumA.class)").build()),
                Arguments.of(
                        MapSetter.builder() //
                                .paramName("") //
                                .paramType(EnumMap.class) //
                                .keyType(EnumB.class) //
                                .valueType(Object.class) //
                                .methodName("") //
                                .visibility(Visibility.PRIVATE) //
                                .build(),
                        CodeBlock.builder().add("new java.util.EnumMap<>(com.github.tobi.laa.reflective.fluent.builders.generator.service.impl.EnumMapInitializerCodeBlockGeneratorServiceImplTest.EnumB.class)").build()));
    }

    private static enum EnumA {}

    private static enum EnumB {}
}