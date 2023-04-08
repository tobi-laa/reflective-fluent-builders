package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.CodeBlock;
import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.model.CollectionSetter;
import io.github.tobi.laa.reflective.fluent.builders.model.Visibility;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class EnumSetInitializerCodeGeneratorTest {

    private final EnumSetInitializerCodeGenerator generator = new EnumSetInitializerCodeGenerator();

    @Test
    void testIApplicableNull() {
        // Act
        final Executable isApplicable = () -> generator.isApplicable(null);
        // Assert
        assertThrows(NullPointerException.class, isApplicable);
    }

    @ParameterizedTest
    @MethodSource
    void testIsApplicableTrue(final CollectionSetter collectionSetter) {
        // Act
        final boolean isApplicable = generator.isApplicable(collectionSetter);
        // Assert
        assertTrue(isApplicable);
    }

    private static Stream<CollectionSetter> testIsApplicableTrue() {
        return testGenerateCollectionInitializer().map(args -> args.get()[0]).map(CollectionSetter.class::cast);
    }

    @ParameterizedTest
    @MethodSource
    void testIsApplicableFalse(final CollectionSetter collectionSetter) {
        // Act
        final boolean isApplicable = generator.isApplicable(collectionSetter);
        // Assert
        assertFalse(isApplicable);
    }

    private static Stream<CollectionSetter> testIsApplicableFalse() {
        return Stream.concat(
                        CollectionsApiInitializerCodeGenerator.SUPPORTED_COLLECTIONS.stream(),
                        Stream.of(Set.class, List.class, CollectionsApiInitializerCodeGeneratorTest.MyList.class))
                .map(type -> CollectionSetter.builder() //
                        .paramName("") //
                        .paramType(type) //
                        .paramTypeArg(Object.class) //
                        .methodName("") //
                        .visibility(Visibility.PRIVATE) //
                        .build());
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
        return testIsApplicableFalse();
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
        return Stream.of(
                Arguments.of(
                        CollectionSetter.builder() //
                                .paramName("") //
                                .paramType(EnumSet.class) //
                                .paramTypeArg(EnumA.class) //
                                .methodName("") //
                                .visibility(Visibility.PRIVATE) //
                                .build(),
                        CodeBlock.builder().add("java.util.EnumSet.noneOf(io.github.tobi.laa.reflective.fluent.builders.generator.impl.EnumSetInitializerCodeGeneratorTest.EnumA.class)").build()),
                Arguments.of(
                        CollectionSetter.builder() //
                                .paramName("") //
                                .paramType(EnumSet.class) //
                                .paramTypeArg(EnumB.class) //
                                .methodName("") //
                                .visibility(Visibility.PRIVATE) //
                                .build(),
                        CodeBlock.builder().add("java.util.EnumSet.noneOf(io.github.tobi.laa.reflective.fluent.builders.generator.impl.EnumSetInitializerCodeGeneratorTest.EnumB.class)").build()));
    }

    private enum EnumA {}

    private enum EnumB {}
}