package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.model.CollectionGetAndAdder;
import io.github.tobi.laa.reflective.fluent.builders.model.CollectionSetter;
import io.github.tobi.laa.reflective.fluent.builders.model.MapSetter;
import io.github.tobi.laa.reflective.fluent.builders.model.Visibility;
import com.squareup.javapoet.CodeBlock;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.script.Bindings;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CollectionsApiInitializerCodeGeneratorTest {

    private final CollectionsApiInitializerCodeGenerator generator = new CollectionsApiInitializerCodeGenerator();

    @Test
    void testIsApplicableForNullCollectionSetter() {
        // Act
        final Executable isApplicable = () -> generator.isApplicable((CollectionSetter) null);
        // Assert
        assertThrows(NullPointerException.class, isApplicable);
    }

    @ParameterizedTest
    @MethodSource
    void testIsApplicableTrueForCollectionSetter(final CollectionSetter collectionSetter) {
        // Act
        final boolean isApplicable = generator.isApplicable(collectionSetter);
        // Assert
        assertTrue(isApplicable);
    }

    private static Stream<CollectionSetter> testIsApplicableTrueForCollectionSetter() {
        return testGenerateCollectionInitializer().map(args -> args.get()[0]).map(CollectionSetter.class::cast);
    }

    @ParameterizedTest
    @MethodSource
    void testIsApplicableFalseForCollectionSetter(final CollectionSetter collectionSetter) {
        // Act
        final boolean isApplicable = generator.isApplicable(collectionSetter);
        // Assert
        assertFalse(isApplicable);
    }

    private static Stream<CollectionSetter> testIsApplicableFalseForCollectionSetter() {
        return Stream.of(
                collectionSetter(EnumSet.class),
                collectionGetAndAdder(MyList.class));
    }

    @Test
    void testGenerateCollectionInitializerNull() {
        // Act
        final Executable generateCollectionInitializer = () -> generator.generateCollectionInitializer(null);
        // Assert
        assertThrows(NullPointerException.class, generateCollectionInitializer);
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateCollectionInitializerCodeGenerationException(final CollectionSetter collectionSetter) {
        // Act
        final ThrowableAssert.ThrowingCallable generateCollectionInitializer = () -> generator.generateCollectionInitializer(collectionSetter);
        // Assert
        assertThatThrownBy(generateCollectionInitializer)
                .isInstanceOf(CodeGenerationException.class)
                .message()
                .matches("Generation of initializing code blocks for .+ is not supported.")
                .contains(collectionSetter.getParamType().getName());
    }

    private static Stream<CollectionSetter> testGenerateCollectionInitializerCodeGenerationException() {
        return testIsApplicableFalseForCollectionSetter();
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateCollectionInitializer(final CollectionSetter collectionSetter, final CodeBlock expected) {
        // Act
        final CodeBlock actual = generator.generateCollectionInitializer(collectionSetter);
        // Assert
        assertThat(actual).hasToString(expected.toString());
    }

    private static Stream<Arguments> testGenerateCollectionInitializer() {
        return Stream.concat( //
                CollectionsApiInitializerCodeGenerator.SUPPORTED_COLLECTIONS //
                        .stream() //
                        .map(type -> Arguments.of( //
                                collectionSetter(type), //
                                CodeBlock.builder().add("new " + type.getName() + "<>()").build())), //
                Stream.of(
                        Arguments.of(
                                collectionSetter(Vector.class),
                                CodeBlock.builder().add("new java.util.Stack<>()").build()),
                        Arguments.of(
                                collectionSetter(BlockingDeque.class),
                                CodeBlock.builder().add("new java.util.concurrent.LinkedBlockingDeque<>()").build()),
                        Arguments.of(
                                collectionSetter(BlockingQueue.class),
                                CodeBlock.builder().add("new java.util.concurrent.DelayQueue<>()").build()),
                        Arguments.of(
                                collectionSetter(Deque.class),
                                CodeBlock.builder().add("new java.util.ArrayDeque<>()").build()),
                        Arguments.of(
                                collectionSetter(List.class),
                                CodeBlock.builder().add("new java.util.ArrayList<>()").build()),
                        Arguments.of(
                                collectionSetter(NavigableSet.class),
                                CodeBlock.builder().add("new java.util.TreeSet<>()").build()),
                        Arguments.of(
                                collectionSetter(Queue.class),
                                CodeBlock.builder().add("new java.util.ArrayDeque<>()").build()),
                        Arguments.of(
                                collectionSetter(Set.class),
                                CodeBlock.builder().add("new java.util.HashSet<>()").build()),
                        Arguments.of(
                                collectionGetAndAdder(SortedSet.class),
                                CodeBlock.builder().add("new java.util.TreeSet<>()").build()),
                        Arguments.of(
                                collectionGetAndAdder(TransferQueue.class),
                                CodeBlock.builder().add("new java.util.concurrent.LinkedTransferQueue<>()").build()),
                        Arguments.of(
                                collectionGetAndAdder(Collection.class),
                                CodeBlock.builder().add("new java.util.ArrayList<>()").build())));
    }

    @Test
    void testIsApplicableNullForMapSetter() {
        // Act
        final Executable isApplicable = () -> generator.isApplicable((MapSetter) null);
        // Assert
        assertThrows(NullPointerException.class, isApplicable);
    }

    @ParameterizedTest
    @MethodSource
    void testIsApplicableTrueForMapSetter(final MapSetter mapSetter) {
        // Act
        final boolean isApplicable = generator.isApplicable(mapSetter);
        // Assert
        assertTrue(isApplicable);
    }

    private static Stream<MapSetter> testIsApplicableTrueForMapSetter() {
        return testGenerateMapInitializer().map(args -> args.get()[0]).map(MapSetter.class::cast);
    }

    @ParameterizedTest
    @MethodSource
    void testIsApplicableFalseForMapSetter(final MapSetter mapSetter) {
        // Act
        final boolean isApplicable = generator.isApplicable(mapSetter);
        // Assert
        assertFalse(isApplicable);
    }

    private static Stream<MapSetter> testIsApplicableFalseForMapSetter() {
        return Stream.of(
                mapSetter(EnumMap.class),
                mapSetter(MyMap.class));
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
                .contains(mapSetter.getParamType().getName());
    }

    private static Stream<MapSetter> testGenerateMapInitializerCodeGenerationException() {
        return testIsApplicableFalseForMapSetter();
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
        return Stream.concat( //
                CollectionsApiInitializerCodeGenerator.SUPPORTED_MAPS //
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
        private static final long serialVersionUID = 3204706075779867698L;
    }

    private static CollectionSetter collectionSetter(final Class<?> type) {
        return CollectionSetter.builder() //
                .paramName("") //
                .paramType(type) //
                .paramTypeArg(Object.class) //
                .methodName("") //
                .visibility(Visibility.PRIVATE) //
                .build();
    }

    private static CollectionGetAndAdder collectionGetAndAdder(final Class<?> type) {
        return CollectionGetAndAdder.builder() //
                .paramName("") //
                .paramType(type) //
                .paramTypeArg(Object.class) //
                .methodName("") //
                .visibility(Visibility.PRIVATE) //
                .build();
    }

    static class MyList<T> extends ArrayList<T> {
        private static final long serialVersionUID = 3204706075779867698L;
    }
}