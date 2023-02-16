package com.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import com.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import com.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.ClassWithHierarchy;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConstructorCodeGeneratorTest {

    @InjectMocks
    private ConstructorCodeGenerator generator;

    @Mock
    private BuilderClassNameGenerator builderClassNameGenerator;

    @Test
    void testGenerateNull() {
        // Arrange
        final BuilderMetadata builderMetadata = null;
        // Act
        final Executable generate = () -> generator.generate(builderMetadata);
        // Assert
        assertThrows(NullPointerException.class, generate);
        verifyNoInteractions(builderClassNameGenerator);
    }

    @ParameterizedTest
    @MethodSource
    void testGenerate(final BuilderMetadata builderMetadata, final String expected) {
        // Arrange
        when(builderClassNameGenerator.generateClassName(any())).thenReturn(ClassName.get(MockType.class));
        // Act
        final Optional<MethodSpec> actual = generator.generate(builderMetadata);
        // Assert
        assertThat(actual).isPresent();
        assertThat(actual.get().toString()).isEqualToIgnoringNewLines(expected);
        verify(builderClassNameGenerator).generateClassName(builderMetadata);
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
                        "private Constructor(\n" +
                                "    final com.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass objectToBuild) {\n" +
                                "  this.objectToBuild = objectToBuild;\n" +
                                "}\n"),
                Arguments.of(
                        BuilderMetadata.builder() //
                                .packageName("a.whole.different.pack") //
                                .name("AnotherBuilder") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(ClassWithHierarchy.class) //
                                        .accessibleNonArgsConstructor(false) //
                                        .build()) //
                                .build(), //
                        "private Constructor(\n" +
                                "    final com.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.ClassWithHierarchy objectToBuild) {\n" +
                                "  this.objectToBuild = objectToBuild;\n" +
                                "}\n"));
    }

    private static class MockType {
        // no content
    }
}