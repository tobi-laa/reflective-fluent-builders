package com.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import com.squareup.javapoet.ClassName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BuilderClassNameGeneratorImplTest {

    private final BuilderClassNameGeneratorImpl generator = new BuilderClassNameGeneratorImpl();

    @Test
    void testGenerateClassNameNull() {
        // Arrange
        final BuilderMetadata metadata = null;
        // Act
        final Executable generateClassName = () -> generator.generateClassName(metadata);
        // Assert
        assertThrows(NullPointerException.class, generateClassName);
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateClassName(final BuilderMetadata metadata, final ClassName expected) {
        // Act
        final ClassName actual = generator.generateClassName(metadata);
        // Assert
        assertThat(actual).hasToString(expected.toString());
    }

    private static Stream<Arguments> testGenerateClassName() {
        return Stream.of(
                Arguments.of(
                        BuilderMetadata.builder() //
                                .packageName("com.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                                .name("SimpleClassBuilder") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(SimpleClass.class) //
                                        .accessibleNonArgsConstructor(true) //
                                        .build()) //
                                .build(), //
                        ClassName.bestGuess("com.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClassBuilder")),
                Arguments.of(
                        BuilderMetadata.builder() //
                                .packageName("a.whole.different.pack") //
                                .name("AnotherBuilder") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(SimpleClass.class) //
                                        .accessibleNonArgsConstructor(true) //
                                        .build()) //
                                .build(), //
                        ClassName.bestGuess("a.whole.different.pack.AnotherBuilder")));
    }
}