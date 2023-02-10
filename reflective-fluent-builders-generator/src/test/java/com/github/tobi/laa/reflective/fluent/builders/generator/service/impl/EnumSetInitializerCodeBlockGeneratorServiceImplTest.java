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

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class EnumSetInitializerCodeBlockGeneratorServiceImplTest {

    private final EnumSetInitializerCodeBlockerGeneratorServiceImpl enumSetInitializerCodeBlockerGeneratorServiceImpl = new EnumSetInitializerCodeBlockerGeneratorServiceImpl();

    @Test
    void testIApplicableNull() {
        // Act
        final Executable isApplicable = () -> enumSetInitializerCodeBlockerGeneratorServiceImpl.isApplicable(null);
        // Assert
        assertThrows(NullPointerException.class, isApplicable);
    }

    @ParameterizedTest
    @MethodSource
    void testIsApplicableTrue(final CollectionSetter collectionSetter) {
        // Act
        final boolean isApplicable = enumSetInitializerCodeBlockerGeneratorServiceImpl.isApplicable(collectionSetter);
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
        final boolean isApplicable = enumSetInitializerCodeBlockerGeneratorServiceImpl.isApplicable(collectionSetter);
        // Assert
        assertFalse(isApplicable);
    }

    private static Stream<CollectionSetter> testIsApplicableFalse() {
        return Stream.concat(
                        SimpleCollectionInitializerCodeBlockGeneratorServiceImpl.SUPPORTED_COLLECTIONS.stream(),
                        Stream.of(Set.class, List.class, SimpleCollectionInitializerCodeBlockGeneratorServiceImplTest.MyList.class))
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
        final Executable generateCollectionInitializer = () -> enumSetInitializerCodeBlockerGeneratorServiceImpl.generateCollectionInitializer(null);
        // Assert
        assertThrows(NullPointerException.class, generateCollectionInitializer);
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateCollectionInitializerCodeGenerationException(final CollectionSetter collectionSetter) {
        // Act
        final ThrowableAssert.ThrowingCallable generateCollectionInitializer = () -> enumSetInitializerCodeBlockerGeneratorServiceImpl.generateCollectionInitializer(collectionSetter);
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
        final CodeBlock actual = enumSetInitializerCodeBlockerGeneratorServiceImpl.generateCollectionInitializer(collectionSetter);
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
                        CodeBlock.builder().add("java.util.EnumSet.noneOf(com.github.tobi.laa.reflective.fluent.builders.generator.service.impl.EnumSetInitializerCodeBlockGeneratorServiceImplTest.EnumA.class)").build()),
                Arguments.of(
                        CollectionSetter.builder() //
                                .paramName("") //
                                .paramType(EnumSet.class) //
                                .paramTypeArg(EnumB.class) //
                                .methodName("") //
                                .visibility(Visibility.PRIVATE) //
                                .build(),
                        CodeBlock.builder().add("java.util.EnumSet.noneOf(com.github.tobi.laa.reflective.fluent.builders.generator.service.impl.EnumSetInitializerCodeBlockGeneratorServiceImplTest.EnumB.class)").build()));
    }

    private static enum EnumA {}

    private static enum EnumB {}
}