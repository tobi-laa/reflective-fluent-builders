package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.CodeBlock;
import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.model.MapSetter;
import io.github.tobi.laa.reflective.fluent.builders.model.Visibility;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections;
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
    void testIsApplicableTrue(final MapSetter mapSetter) {
        // Act
        final boolean isApplicable = generator.isApplicable(mapSetter);
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
        final boolean isApplicable = generator.isApplicable(mapSetter);
        // Assert
        assertFalse(isApplicable);
    }

    private static Stream<MapSetter> testIsApplicableFalse() {
        return Stream.concat(
                        CollectionsApiInitializerCodeGenerator.SUPPORTED_MAPS.stream(),
                        Stream.of(Set.class, List.class, CollectionsApiInitializerCodeGeneratorTest.MyList.class))
                .map(type -> MapSetter.builder() //
                        .propertyName("") //
                        .propertyType(type) //
                        .keyType(Object.class) //
                        .valueType(Object.class)
                        .methodName("") //
                        .visibility(Visibility.PRIVATE) //
                        .declaringClass(ClassWithCollections.class) //
                        .build());
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
    void testGenerateMapInitializerCodeGenerationException(final MapSetter mapSetter) {
        // Act
        final ThrowableAssert.ThrowingCallable generateMapInitializer = () -> generator.generateMapInitializer(mapSetter);
        // Assert
        assertThatThrownBy(generateMapInitializer)
                .isInstanceOf(CodeGenerationException.class)
                .message()
                .matches("Generation of initializing code blocks for .+ is not supported.")
                .contains(mapSetter.getPropertyType().getTypeName());
    }

    private static Stream<MapSetter> testGenerateMapInitializerCodeGenerationException() {
        return testIsApplicableFalse();
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateMapInitializer(final MapSetter mapSetter, final CodeBlock expected) {
        // Act
        final CodeBlock actual = generator.generateMapInitializer(mapSetter);
        // Assert
        assertThat(actual).hasToString(expected.toString());
    }

    private static Stream<Arguments> testGenerateMapInitializer() {
        return Stream.of(
                Arguments.of(
                        MapSetter.builder() //
                                .propertyName("") //
                                .propertyType(EnumMap.class) //
                                .keyType(EnumA.class) //
                                .valueType(Object.class) //
                                .methodName("") //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(),
                        CodeBlock.builder().add("new java.util.EnumMap<>(io.github.tobi.laa.reflective.fluent.builders.generator.impl.EnumMapInitializerCodeGeneratorTest.EnumA.class)").build()),
                Arguments.of(
                        MapSetter.builder() //
                                .propertyName("") //
                                .propertyType(EnumMap.class) //
                                .keyType(EnumB.class) //
                                .valueType(Object.class) //
                                .methodName("") //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(),
                        CodeBlock.builder().add("new java.util.EnumMap<>(io.github.tobi.laa.reflective.fluent.builders.generator.impl.EnumMapInitializerCodeGeneratorTest.EnumB.class)").build()));
    }

    private enum EnumA {}

    private enum EnumB {}
}