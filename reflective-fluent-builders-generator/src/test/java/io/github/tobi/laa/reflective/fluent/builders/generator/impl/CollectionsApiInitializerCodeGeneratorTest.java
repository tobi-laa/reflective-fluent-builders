package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.CodeBlock;
import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.model.CollectionType;
import io.github.tobi.laa.reflective.fluent.builders.model.MapType;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.script.Bindings;
import java.io.Serial;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CollectionsApiInitializerCodeGeneratorTest {

    private final CollectionsApiInitializerCodeGenerator generator = new CollectionsApiInitializerCodeGenerator();

    @Test
    void testIsApplicableForNullCollectionType() {
        // Act
        final Executable isApplicable = () -> generator.isApplicable((CollectionType) null);
        // Assert
        assertThrows(NullPointerException.class, isApplicable);
    }

    @ParameterizedTest
    @MethodSource
    void testIsApplicableTrueForCollectionType(final CollectionType collectionType) {
        // Act
        final boolean isApplicable = generator.isApplicable(collectionType);
        // Assert
        assertTrue(isApplicable);
    }

    static Stream<CollectionType> testIsApplicableTrueForCollectionType() {
        return testGenerateCollectionInitializer().map(args -> args.get()[0]).map(CollectionType.class::cast);
    }

    @ParameterizedTest
    @MethodSource
    void testIsApplicableFalseForCollectionType(final CollectionType collectionType) {
        // Act
        final boolean isApplicable = generator.isApplicable(collectionType);
        // Assert
        assertFalse(isApplicable);
    }

    static Stream<CollectionType> testIsApplicableFalseForCollectionType() {
        return Stream.of(
                collectionType(EnumSet.class),
                collectionType(MyList.class));
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
    void testGenerateCollectionInitializerCodeGenerationException(final CollectionType collectionType) {
        // Act
        final ThrowableAssert.ThrowingCallable generateCollectionInitializer = () -> generator.generateCollectionInitializer(collectionType);
        // Assert
        assertThatThrownBy(generateCollectionInitializer)
                .isInstanceOf(CodeGenerationException.class)
                .message()
                .matches("Generation of initializing code blocks for .+ is not supported.")
                .contains(collectionType.getType().getTypeName());
    }

    static Stream<CollectionType> testGenerateCollectionInitializerCodeGenerationException() {
        return testIsApplicableFalseForCollectionType();
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateCollectionInitializer(final CollectionType collectionType, final CodeBlock expected) {
        // Act
        final CodeBlock actual = generator.generateCollectionInitializer(collectionType);
        // Assert
        assertThat(actual).hasToString(expected.toString());
    }

    static Stream<Arguments> testGenerateCollectionInitializer() {
        return Stream.concat( //
                CollectionsApiInitializerCodeGenerator.SUPPORTED_COLLECTIONS //
                        .stream() //
                        .map(type -> Arguments.of( //
                                collectionType(type), //
                                CodeBlock.builder().add("new " + type.getName() + "<>()").build())), //
                Stream.of(
                        Arguments.of(
                                collectionType(Vector.class),
                                CodeBlock.builder().add("new java.util.Stack<>()").build()),
                        Arguments.of(
                                collectionType(BlockingDeque.class),
                                CodeBlock.builder().add("new java.util.concurrent.LinkedBlockingDeque<>()").build()),
                        Arguments.of(
                                collectionType(BlockingQueue.class),
                                CodeBlock.builder().add("new java.util.concurrent.DelayQueue<>()").build()),
                        Arguments.of(
                                collectionType(Deque.class),
                                CodeBlock.builder().add("new java.util.ArrayDeque<>()").build()),
                        Arguments.of(
                                collectionType(List.class),
                                CodeBlock.builder().add("new java.util.ArrayList<>()").build()),
                        Arguments.of(
                                collectionType(NavigableSet.class),
                                CodeBlock.builder().add("new java.util.TreeSet<>()").build()),
                        Arguments.of(
                                collectionType(Queue.class),
                                CodeBlock.builder().add("new java.util.ArrayDeque<>()").build()),
                        Arguments.of(
                                collectionType(Set.class),
                                CodeBlock.builder().add("new java.util.HashSet<>()").build()),
                        Arguments.of(
                                collectionType(SortedSet.class),
                                CodeBlock.builder().add("new java.util.TreeSet<>()").build()),
                        Arguments.of(
                                collectionType(TransferQueue.class),
                                CodeBlock.builder().add("new java.util.concurrent.LinkedTransferQueue<>()").build()),
                        Arguments.of(
                                collectionType(Collection.class),
                                CodeBlock.builder().add("new java.util.ArrayList<>()").build())));
    }

    @Test
    void testIsApplicableNullForMapType() {
        // Act
        final Executable isApplicable = () -> generator.isApplicable((MapType) null);
        // Assert
        assertThrows(NullPointerException.class, isApplicable);
    }

    @ParameterizedTest
    @MethodSource
    void testIsApplicableTrueForMapType(final MapType mapType) {
        // Act
        final boolean isApplicable = generator.isApplicable(mapType);
        // Assert
        assertTrue(isApplicable);
    }

    static Stream<MapType> testIsApplicableTrueForMapType() {
        return testGenerateMapInitializer().map(args -> args.get()[0]).map(MapType.class::cast);
    }

    @ParameterizedTest
    @MethodSource
    void testIsApplicableFalseForMapType(final MapType mapType) {
        // Act
        final boolean isApplicable = generator.isApplicable(mapType);
        // Assert
        assertFalse(isApplicable);
    }

    static Stream<MapType> testIsApplicableFalseForMapType() {
        return Stream.of(
                mapType(EnumMap.class),
                mapType(MyMap.class));
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

    static Stream<MapType> testGenerateMapInitializerCodeGenerationException() {
        return testIsApplicableFalseForMapType();
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateMapInitializer(final MapType mapType, final CodeBlock expected) {
        // Act
        final CodeBlock actual = generator.generateMapInitializer(mapType);
        // Assert
        assertThat(actual).hasToString(expected.toString());
    }

    static Stream<Arguments> testGenerateMapInitializer() {
        return Stream.concat( //
                CollectionsApiInitializerCodeGenerator.SUPPORTED_MAPS //
                        .stream() //
                        .map(type -> Arguments.of( //
                                mapType(type), //
                                CodeBlock.builder().add("new " + type.getName() + "<>()").build())), //
                Stream.of(
                        Arguments.of(
                                mapType(Bindings.class),
                                CodeBlock.builder().add("new javax.script.SimpleBindings<>()").build()),
                        Arguments.of(
                                mapType(ConcurrentMap.class),
                                CodeBlock.builder().add("new java.util.concurrent.ConcurrentHashMap<>()").build()),
                        Arguments.of(
                                mapType(ConcurrentNavigableMap.class),
                                CodeBlock.builder().add("new java.util.concurrent.ConcurrentSkipListMap<>()").build()),
                        Arguments.of(
                                mapType(NavigableMap.class),
                                CodeBlock.builder().add("new java.util.TreeMap<>()").build()),
                        Arguments.of(
                                mapType(SortedMap.class),
                                CodeBlock.builder().add("new java.util.TreeMap<>()").build()),
                        Arguments.of(
                                mapType(AbstractMap.class),
                                CodeBlock.builder().add("new java.util.HashMap<>()").build())));
    }


    private static MapType mapType(final Class<?> type) {
        return new MapType(type, Object.class, Object.class);
    }

    static class MyMap<K, V> extends HashMap<K, V> {
        @Serial
        private static final long serialVersionUID = 3204706075779867698L;
    }

    private static CollectionType collectionType(final Class<?> type) {
        return new CollectionType(type, Object.class);
    }

    static class MyList<T> extends ArrayList<T> {
        @Serial
        private static final long serialVersionUID = 3204706075779867698L;
    }
}