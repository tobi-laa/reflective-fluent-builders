package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.CodeBlock;
import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.model.CollectionType;
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
    void testIsApplicableTrue(final CollectionType collectionType) {
        // Act
        final boolean isApplicable = generator.isApplicable(collectionType);
        // Assert
        assertTrue(isApplicable);
    }

    private static Stream<CollectionType> testIsApplicableTrue() {
        return testGenerateCollectionInitializer().map(args -> args.get()[0]).map(CollectionType.class::cast);
    }

    @ParameterizedTest
    @MethodSource
    void testIsApplicableFalse(final CollectionType collectionType) {
        // Act
        final boolean isApplicable = generator.isApplicable(collectionType);
        // Assert
        assertFalse(isApplicable);
    }

    private static Stream<CollectionType> testIsApplicableFalse() {
        return Stream.concat(
                        CollectionsApiInitializerCodeGenerator.SUPPORTED_COLLECTIONS.stream(),
                        Stream.of(Set.class, List.class, CollectionsApiInitializerCodeGeneratorTest.MyList.class))
                .map(type -> new CollectionType(type, Object.class));
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

    private static Stream<CollectionType> testGenerateCollectionInitializerCodeGenerationException() {
        return testIsApplicableFalse();
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateCollectionInitializer(final CollectionType collectionType, final CodeBlock expected) {
        // Act
        final CodeBlock actual = generator.generateCollectionInitializer(collectionType);
        // Assert
        assertThat(actual).hasToString(expected.toString());
    }

    private static Stream<Arguments> testGenerateCollectionInitializer() {
        return Stream.of(
                Arguments.of(
                        new CollectionType(EnumSet.class, EnumA.class),
                        CodeBlock.builder().add("java.util.EnumSet.noneOf(io.github.tobi.laa.reflective.fluent.builders.generator.impl.EnumSetInitializerCodeGeneratorTest.EnumA.class)").build()),
                Arguments.of(
                        new CollectionType(EnumSet.class, EnumB.class),
                        CodeBlock.builder().add("java.util.EnumSet.noneOf(io.github.tobi.laa.reflective.fluent.builders.generator.impl.EnumSetInitializerCodeGeneratorTest.EnumB.class)").build()));
    }

    private enum EnumA {}

    private enum EnumB {}
}