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

import javax.script.Bindings;
import java.io.Serial;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class SimpleMapInitializerCodeBlockGeneratorServiceImplTest {

    private final SimpleMapInitializerCodeBlockGeneratorServiceImpl simpleMapInitializerCodeBlockGeneratorServiceImpl = new SimpleMapInitializerCodeBlockGeneratorServiceImpl();

    @Test
    void testIsApplicableNull() {
        // Act
        final Executable isApplicable = () -> simpleMapInitializerCodeBlockGeneratorServiceImpl.isApplicable(null);
        // Assert
        assertThrows(NullPointerException.class, isApplicable);
    }

    @ParameterizedTest
    @MethodSource
    void testIsApplicableTrue(final MapSetter mapSetter) {
        // Act
        final boolean isApplicable = simpleMapInitializerCodeBlockGeneratorServiceImpl.isApplicable(mapSetter);
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
        final boolean isApplicable = simpleMapInitializerCodeBlockGeneratorServiceImpl.isApplicable(mapSetter);
        // Assert
        assertFalse(isApplicable);
    }

    private static Stream<MapSetter> testIsApplicableFalse() {
        return Stream.of(
                mapSetter(EnumMap.class),
                mapSetter(MyMap.class));
    }

    @Test
    void testGenerateMapInitializerNull() {
        // Act
        final Executable generateMapInitializer = () -> simpleMapInitializerCodeBlockGeneratorServiceImpl.generateMapInitializer(null);
        // Assert
        assertThrows(NullPointerException.class, generateMapInitializer);
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateMapInitializerCodeGenerationException(final MapSetter mapSetter) {
        // Act
        final ThrowableAssert.ThrowingCallable generateMapInitializer = () -> simpleMapInitializerCodeBlockGeneratorServiceImpl.generateMapInitializer(mapSetter);
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
        final CodeBlock actual = simpleMapInitializerCodeBlockGeneratorServiceImpl.generateMapInitializer(mapSetter);
        // Assert
        assertThat(actual).hasToString(expected.toString());
    }

    private static Stream<Arguments> testGenerateMapInitializer() {
        return Stream.concat( //
                SimpleMapInitializerCodeBlockGeneratorServiceImpl.SUPPORTED_MAPS //
                        .stream() //
                        .map(type -> Arguments.of( //
                                mapSetter(type), //
                                CodeBlock.builder().add("new " + type.getName() + "<>()").build())), //
                Stream.of(
                        Arguments.of(
                                mapSetter(Bindings.class),
                                CodeBlock.builder().add("new javax.script.SimpleBindings<>()").build()),
                        Arguments.of(
                                mapSetter(ConcurrentMap.class),
                                CodeBlock.builder().add("new java.util.concurrent.ConcurrentHashMap<>()").build()),
                        Arguments.of(
                                mapSetter(ConcurrentNavigableMap.class),
                                CodeBlock.builder().add("new java.util.concurrent.ConcurrentSkipListMap<>()").build()),
                        Arguments.of(
                                mapSetter(NavigableMap.class),
                                CodeBlock.builder().add("new java.util.TreeMap<>()").build()),
                        Arguments.of(
                                mapSetter(SortedMap.class),
                                CodeBlock.builder().add("new java.util.TreeMap<>()").build()),
                        Arguments.of(
                                mapSetter(AbstractMap.class),
                                CodeBlock.builder().add("new java.util.HashMap<>()").build())));
    }


    private static MapSetter mapSetter(final Class<?> type) {
        return MapSetter.builder() //
                .paramName("") //
                .paramType(type) //
                .keyType(Object.class) //
                .valueType(Object.class) //
                .methodName("") //
                .visibility(Visibility.PRIVATE) //
                .build();
    }

    static class MyMap<K, V> extends HashMap<K, V> {
        @Serial
        private static final long serialVersionUID = 3204706075779867698L;
    }
}