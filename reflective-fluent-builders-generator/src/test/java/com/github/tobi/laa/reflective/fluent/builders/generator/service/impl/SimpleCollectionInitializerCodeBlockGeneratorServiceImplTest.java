package com.github.tobi.laa.reflective.fluent.builders.generator.service.impl;

import com.github.tobi.laa.reflective.fluent.builders.generator.exception.CodeGenerationException;
import com.github.tobi.laa.reflective.fluent.builders.generator.model.CollectionSetter;
import com.github.tobi.laa.reflective.fluent.builders.generator.model.Visibility;
import com.squareup.javapoet.CodeBlock;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.Serial;
import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TransferQueue;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class SimpleCollectionInitializerCodeBlockGeneratorServiceImplTest {

    private final SimpleCollectionInitializerCodeBlockGeneratorServiceImpl simpleCollectionInitializerCodeBlockGeneratorService = new SimpleCollectionInitializerCodeBlockGeneratorServiceImpl();

    @Test
    void isApplicableNull() {
        // Act
        final Executable isApplicable = () -> simpleCollectionInitializerCodeBlockGeneratorService.isApplicable(null);
        // Assert
        assertThrows(NullPointerException.class, isApplicable);
    }

    @ParameterizedTest
    @MethodSource
    void isApplicableTrue(final CollectionSetter collectionSetter) {
        // Act
        final boolean isApplicable = simpleCollectionInitializerCodeBlockGeneratorService.isApplicable(collectionSetter);
        // Assert
        assertTrue(isApplicable);
    }

    private static Stream<CollectionSetter> isApplicableTrue() {
        return generateCollectionInitializer().map(args -> args.get()[0]).map(CollectionSetter.class::cast);
    }

    @ParameterizedTest
    @MethodSource
    void isApplicableFalse(final CollectionSetter collectionSetter) {
        // Act
        final boolean isApplicable = simpleCollectionInitializerCodeBlockGeneratorService.isApplicable(collectionSetter);
        // Assert
        assertFalse(isApplicable);
    }

    private static Stream<CollectionSetter> isApplicableFalse() {
        return Stream.of(
                collectionSetter(EnumSet.class),
                collectionSetter(MyList.class));
    }

    @Test
    void generateCollectionInitializerNull() {
        // Act
        final Executable generateCollectionInitializer = () -> simpleCollectionInitializerCodeBlockGeneratorService.generateCollectionInitializer(null);
        // Assert
        assertThrows(NullPointerException.class, generateCollectionInitializer);
    }

    @ParameterizedTest
    @MethodSource
    void generateCollectionInitializerCodeGenerationException(final CollectionSetter collectionSetter) {
        // Act
        final ThrowableAssert.ThrowingCallable generateCollectionInitializer = () -> simpleCollectionInitializerCodeBlockGeneratorService.generateCollectionInitializer(collectionSetter);
        // Assert
        assertThatThrownBy(generateCollectionInitializer)
                .isInstanceOf(CodeGenerationException.class)
                .message()
                .matches("Generation of initializing code blocks for .+ is not supported.")
                .contains(collectionSetter.getParamType().getName());
    }

    private static Stream<CollectionSetter> generateCollectionInitializerCodeGenerationException() {
        return isApplicableFalse();
    }

    @ParameterizedTest
    @MethodSource
    void generateCollectionInitializer(final CollectionSetter collectionSetter, final CodeBlock expected) {
        // Act
        final CodeBlock actual = simpleCollectionInitializerCodeBlockGeneratorService.generateCollectionInitializer(collectionSetter);
        // Assert
        assertThat(actual).hasToString(expected.toString());
    }

    private static Stream<Arguments> generateCollectionInitializer() {
        return Stream.concat( //
                SimpleCollectionInitializerCodeBlockGeneratorServiceImpl.SUPPORTED_COLLECTIONS //
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
                                collectionSetter(SortedSet.class),
                                CodeBlock.builder().add("new java.util.TreeSet<>()").build()),
                        Arguments.of(
                                collectionSetter(TransferQueue.class),
                                CodeBlock.builder().add("new java.util.concurrent.LinkedTransferQueue<>()").build()),
                        Arguments.of(
                                collectionSetter(Collection.class),
                                CodeBlock.builder().add("new java.util.ArrayList<>()").build())));
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

    static class MyList<T> extends ArrayList<T> {
        @Serial
        private static final long serialVersionUID = 3204706075779867698L;
    }
}