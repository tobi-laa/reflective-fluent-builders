package com.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import com.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.ClassWithHierarchy;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import com.squareup.javapoet.FieldSpec;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ObjectToBuildFieldCodeGeneratorTest {

    private final ObjectToBuildFieldCodeGenerator generator = new ObjectToBuildFieldCodeGenerator();

    @Test
    void testGenerateNull() {
        // Arrange
        final BuilderMetadata builderMetadata = null;
        // Act
        final Executable generate = () -> generator.generate(builderMetadata);
        // Assert
        assertThrows(NullPointerException.class, generate);
    }

    @ParameterizedTest
    @MethodSource
    void testGenerate(final BuilderMetadata builderMetadata, final String expected) {
        // Act
        final FieldSpec actual = generator.generate(builderMetadata);
        // Assert
        assertThat(actual).isNotNull();
        assertThat(actual.toString()).isEqualToIgnoringNewLines(expected);
    }

    private static Stream<Arguments> testGenerate() {
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
                        String.format("private %s objectToBuild;\n", SimpleClass.class.getName())),
                Arguments.of(
                        BuilderMetadata.builder() //
                                .packageName("a.whole.different.pack") //
                                .name("AnotherBuilder") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(ClassWithHierarchy.class) //
                                        .accessibleNonArgsConstructor(false) //
                                        .build()) //
                                .build(), //
                        String.format("private %s objectToBuild;\n", ClassWithHierarchy.class.getName())));
    }
}