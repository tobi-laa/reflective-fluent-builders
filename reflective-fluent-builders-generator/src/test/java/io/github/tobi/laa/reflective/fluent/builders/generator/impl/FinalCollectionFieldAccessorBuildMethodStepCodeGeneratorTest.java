package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.model.*;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.DirectFieldAccess;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.apache.commons.lang3.reflect.TypeUtils.parameterize;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FinalCollectionFieldAccessorBuildMethodStepCodeGeneratorTest {

    private FinalCollectionFieldAccessorBuildMethodStepCodeGenerator generator = new FinalCollectionFieldAccessorBuildMethodStepCodeGenerator();

    @Test
    void testIsApplicableNull() {
        // Arrange
        final WriteAccessor writeAccessor = null;
        // Act
        final ThrowingCallable isApplicable = () -> generator.isApplicable(writeAccessor);
        // Assert
        assertThatThrownBy(isApplicable).isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @MethodSource
    void testIsApplicable(final WriteAccessor writeAccessor, final boolean expected) {
        // Act
        final boolean actual = generator.isApplicable(writeAccessor);
        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> testIsApplicable() {
        return Stream.of(
                Arguments.of(
                        Getter.builder() //
                                .methodName("getList") //
                                .propertyName("list") //
                                .propertyType(new CollectionType(List.class, String.class)) //
                                .visibility(Visibility.PUBLIC) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(),
                        false),
                Arguments.of(
                        FieldAccessor.builder() //
                                .propertyName("publicFieldNoSetter") //
                                .propertyType(new SimpleType(int.class)) //
                                .visibility(Visibility.PUBLIC) //
                                .declaringClass(DirectFieldAccess.class) //
                                .build(),
                        false),
                Arguments.of(
                        FieldAccessor.builder() //
                                .propertyName("publicFinalFieldNoSetter") //
                                .propertyType(new SimpleType(int.class)) //
                                .visibility(Visibility.PUBLIC) //
                                .isFinal(true) //
                                .declaringClass(DirectFieldAccess.class) //
                                .build(),
                        false),
                Arguments.of(
                        FieldAccessor.builder() //
                                .propertyName("publicFinalFieldNoSetter") //
                                .propertyType(new CollectionType(parameterize(List.class, String.class), String.class)) //
                                .visibility(Visibility.PUBLIC) //
                                .isFinal(true) //
                                .declaringClass(DirectFieldAccess.class) //
                                .build(),
                        true));
    }

    @Test
    void testGenerateNull() {
        // Arrange
        final WriteAccessor writeAccessor = null;
        // Act
        final ThrowingCallable generate = () -> generator.generate(writeAccessor);
        // Assert
        assertThatThrownBy(generate).isInstanceOf(NullPointerException.class);
    }

    @Test
    void testGenerateCodeGenerationException() {
        // Arrange
        final WriteAccessor writeAccessor = Getter.builder() //
                .methodName("getList") //
                .propertyName("list") //
                .propertyType(new CollectionType(List.class, String.class)) //
                .visibility(Visibility.PUBLIC) //
                .declaringClass(ClassWithCollections.class) //
                .build();
        // Act
        final ThrowingCallable generate = () -> generator.generate(writeAccessor);
        // Assert
        assertThatThrownBy(generate)
                .isInstanceOf(CodeGenerationException.class)
                .message().contains(writeAccessor.getClass().getSimpleName());
    }
}
