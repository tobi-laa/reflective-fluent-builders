package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.MethodSpec;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.ClassWithHierarchy;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultConstructorCodeGeneratorTest {

    private final DefaultConstructorCodeGenerator generator = new DefaultConstructorCodeGenerator();

    @Test
    void testGenerateNull() {
        // Arrange
        final BuilderMetadata builderMetadata = null;
        // Act
        final Executable generate = () -> generator.generate(builderMetadata);
        // Assert
        assertThrows(NullPointerException.class, generate);
    }

    @Test
    void testGenerateEmpty() {
        // Arrange
        final BuilderMetadata builderMetadata = BuilderMetadata.builder() //
                .packageName("a.whole.different.pack") //
                .name("AnotherBuilder") //
                .builtType(BuilderMetadata.BuiltType.builder() //
                        .type(ClassWithHierarchy.class) //
                        .accessibleNonArgsConstructor(false) //
                        .build()) //
                .build();
        // Act
        final Optional<MethodSpec> actual = generator.generate(builderMetadata);
        // Assert
        assertThat(actual).isEmpty();
    }

    @Test
    void testGeneratePresent() {
        // Arrange
        final BuilderMetadata builderMetadata = BuilderMetadata.builder() //
                .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                .name("SimpleClassBuilder") //
                .builtType(BuilderMetadata.BuiltType.builder() //
                        .type(SimpleClass.class) //
                        .accessibleNonArgsConstructor(true) //
                        .build()) //
                .build();
        // Act
        final Optional<MethodSpec> actual = generator.generate(builderMetadata);
        // Assert
        assertThat(actual).isPresent();
        assertThat(actual.get().toString()).isEqualToIgnoringNewLines("protected Constructor() {\n" +
                "  // noop\n" +
                "}\n");
    }
}
