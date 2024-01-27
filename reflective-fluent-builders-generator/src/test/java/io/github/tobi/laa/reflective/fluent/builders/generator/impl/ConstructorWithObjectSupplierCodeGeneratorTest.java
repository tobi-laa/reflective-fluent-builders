package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.MethodSpec;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.test.ClassGraphExtension;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.ClassWithHierarchy;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConstructorWithObjectSupplierCodeGeneratorTest {

    @RegisterExtension
    static ClassGraphExtension classInfo = new ClassGraphExtension();

    private final ConstructorWithObjectSupplierCodeGenerator generator = new ConstructorWithObjectSupplierCodeGenerator();

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
        final Optional<MethodSpec> actual = generator.generate(builderMetadata);
        // Assert
        assertThat(actual).isPresent();
        assertThat(actual.get().toString()).isEqualToIgnoringNewLines(expected);
    }

    private static Stream<Arguments> testGenerate() {
        return Stream.of(
                Arguments.of(
                        BuilderMetadata.builder() //
                                .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                                .name("SimpleClassBuilder") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(SimpleClass.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .build()) //
                                .build(), //
                        "/**\n" +
                                " * Creates a new instance of {@link io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass} using the given {@code objectSupplier}.\n" +
                                " * Has been set to visibility {@code protected} so that users may choose to inherit the builder.\n" +
                                " */" +
                                "protected Constructor(\n" +
                                "    final java.util.function.Supplier<io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass> objectSupplier) {\n" +
                                "  this.objectSupplier = java.util.Objects.requireNonNull(objectSupplier);\n" +
                                "}\n"),
                Arguments.of(
                        BuilderMetadata.builder() //
                                .packageName("a.whole.different.pack") //
                                .name("AnotherBuilder") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(ClassWithHierarchy.class)) //
                                        .accessibleNonArgsConstructor(false) //
                                        .build()) //
                                .build(), //
                        "/**\n" +
                                " * Creates a new instance of {@link io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.ClassWithHierarchy} using the given {@code objectSupplier}.\n" +
                                " * Has been set to visibility {@code protected} so that users may choose to inherit the builder.\n" +
                                " */" +
                                "protected Constructor(\n" +
                                "    final java.util.function.Supplier<io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.ClassWithHierarchy> objectSupplier) {\n" +
                                "  this.objectSupplier = java.util.Objects.requireNonNull(objectSupplier);\n" +
                                "}\n"));
    }
}