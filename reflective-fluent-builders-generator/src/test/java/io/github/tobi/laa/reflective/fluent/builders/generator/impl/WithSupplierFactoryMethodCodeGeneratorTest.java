package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.test.ClassGraphExtension;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.ClassWithHierarchy;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
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
class WithSupplierFactoryMethodCodeGeneratorTest {

    @RegisterExtension
    static ClassGraphExtension classInfo = new ClassGraphExtension();

    @InjectMocks
    private WithSupplierFactoryMethodCodeGenerator generator;

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

    static Stream<Arguments> testGenerate() {
        return Stream.of(
                Arguments.of(
                        BuilderMetadata.builder() //
                                .packageName("com.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                                .name("SimpleClassBuilder") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(SimpleClass.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .build()) //
                                .build(), //
                        String.format(
                                "/**\n" +
                                        " * Creates an instance of {@link %1$s} that will work on an instance of {@link io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass} that is created initially by the given {@code supplier} once {@link #build()} is called.\n" +
                                        " */\n" +
                                        "public static %1$s withSupplier(\n" +
                                        "    final java.util.function.Supplier<%2$s> supplier) {\n" +
                                        "  return new %1$s(supplier);\n" +
                                        "}\n",
                                MockType.class.getName().replace('$', '.'),
                                SimpleClass.class.getName())),
                Arguments.of(
                        BuilderMetadata.builder() //
                                .packageName("a.whole.different.pack") //
                                .name("AnotherBuilder") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(ClassWithHierarchy.class)) //
                                        .accessibleNonArgsConstructor(false) //
                                        .build()) //
                                .build(), //
                        String.format(
                                "/**\n" +
                                        " * Creates an instance of {@link %1$s} that will work on an instance of {@link io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.ClassWithHierarchy} that is created initially by the given {@code supplier} once {@link #build()} is called.\n" +
                                        " */\n" +
                                        "public static %1$s withSupplier(\n" +
                                        "    final java.util.function.Supplier<%2$s> supplier) {\n" +
                                        "  return new %1$s(supplier);\n" +
                                        "}\n",
                                MockType.class.getName().replace('$', '.'),
                                ClassWithHierarchy.class.getName())));
    }

    private static class MockType {
        // no content
    }
}