package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.CodeBlock;
import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.model.MapType;
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

class EnumMapInitializerCodeGeneratorTest {

    private final EnumMapInitializerCodeGenerator generator = new EnumMapInitializerCodeGenerator();

    @Test
    void testIsApplicableNull() {
        // Act
        final Executable isApplicable = () -> generator.isApplicable(null);
        // Assert
        assertThrows(NullPointerException.class, isApplicable);
    }

    @ParameterizedTest
    @MethodSource
    void testIsApplicableTrue(final MapType mapType) {
        // Act
        final boolean isApplicable = generator.isApplicable(mapType);
        // Assert
        assertTrue(isApplicable);
    }

    private static Stream<MapType> testIsApplicableTrue() {
        return testGenerateMapInitializer().map(args -> args.get()[0]).map(MapType.class::cast);
    }

    @ParameterizedTest
    @MethodSource
    void testIsApplicableFalse(final MapType mapType) {
        // Act
        final boolean isApplicable = generator.isApplicable(mapType);
        // Assert
        assertFalse(isApplicable);
    }

    private static Stream<MapType> testIsApplicableFalse() {
        return Stream.concat(
                        CollectionsApiInitializerCodeGenerator.SUPPORTED_MAPS.stream(),
                        Stream.of(Set.class, List.class, CollectionsApiInitializerCodeGeneratorTest.MyList.class))
                .map(type -> new MapType(type, Object.class, Object.class));
    }

    @Test
    void testGenerateMapInitializerNull() {
        // Act
        final Executable generateMapInitializer = () -> generator.generateMapInitializer(null);
        // Assert
        assertThrows(NullPointerException.class, generateMapInitializer);
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateMapInitializerCodeGenerationException(final MapType mapType) {
        // Act
        final ThrowableAssert.ThrowingCallable generateMapInitializer = () -> generator.generateMapInitializer(mapType);
        // Assert
        assertThatThrownBy(generateMapInitializer)
                .isInstanceOf(CodeGenerationException.class)
                .message()
                .matches("Generation of initializing code blocks for .+ is not supported.")
                .contains(mapType.getType().getTypeName());
    }

    private static Stream<MapType> testGenerateMapInitializerCodeGenerationException() {
        return testIsApplicableFalse();
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateMapInitializer(final MapType mapType, final CodeBlock expected) {
        // Act
        final CodeBlock actual = generator.generateMapInitializer(mapType);
        // Assert
        assertThat(actual).hasToString(expected.toString());
    }

    private static Stream<Arguments> testGenerateMapInitializer() {
        return Stream.of(
                Arguments.of(
                        new MapType(EnumMap.class, EnumA.class, Object.class),
                        CodeBlock.builder().add("new java.util.EnumMap<>(io.github.tobi.laa.reflective.fluent.builders.generator.impl.EnumMapInitializerCodeGeneratorTest.EnumA.class)").build()),
                Arguments.of(
                        new MapType(EnumMap.class, EnumB.class, Object.class),
                        CodeBlock.builder().add("new java.util.EnumMap<>(io.github.tobi.laa.reflective.fluent.builders.generator.impl.EnumMapInitializerCodeGeneratorTest.EnumB.class)").build()));
    }

    private enum EnumA {}

    private enum EnumB {}
}