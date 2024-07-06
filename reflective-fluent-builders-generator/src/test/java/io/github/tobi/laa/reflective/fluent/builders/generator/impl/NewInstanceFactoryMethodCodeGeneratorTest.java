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
class NewInstanceFactoryMethodCodeGeneratorTest {

    @RegisterExtension
    static ClassGraphExtension classInfo = new ClassGraphExtension();

    @InjectMocks
    private NewInstanceFactoryMethodCodeGenerator generator;

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

    @Test
    void testGenerateNoAccessibleNonArgsConstructor() {
        // Arrange
        final var builderMetadata = BuilderMetadata.builder() //
                .packageName("a.whole.different.pack") //
                .name("AnotherBuilder") //
                .builtType(BuilderMetadata.BuiltType.builder() //
                        .type(classInfo.get(ClassWithHierarchy.class)) //
                        .accessibleNonArgsConstructor(false) //
                        .build()) //
                .build();
        when(builderClassNameGenerator.generateClassName(any())).thenReturn(ClassName.get(MockType.class));
        // Act
        final Optional<MethodSpec> actual = generator.generate(builderMetadata);
        // Assert
        assertThat(actual).isEmpty();
        verify(builderClassNameGenerator).generateClassName(builderMetadata);
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
                                """
                                        /**
                                         * Creates an instance of {@link %1$s} that will work on a new instance of {@link io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass} once {@link #build()} is called.
                                         */
                                        public static %1$s newInstance(
                                            ) {
                                          return new %1$s(io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass::new);
                                        }
                                        """, MockType.class.getName().replace('$', '.'))));
    }

    private static class MockType {
        // no content
    }
}