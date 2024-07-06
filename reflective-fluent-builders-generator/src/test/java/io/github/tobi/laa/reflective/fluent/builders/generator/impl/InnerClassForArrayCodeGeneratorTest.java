package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.ClassName;
import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.model.CollectionClassSpec;
import io.github.tobi.laa.reflective.fluent.builders.model.*;
import io.github.tobi.laa.reflective.fluent.builders.test.ClassGraphExtension;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
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

import java.util.Deque;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InnerClassForArrayCodeGeneratorTest {

    @RegisterExtension
    static ClassGraphExtension classInfo = new ClassGraphExtension();

    @InjectMocks
    private InnerClassForArrayCodeGenerator generator;

    @Mock
    private BuilderClassNameGenerator builderClassNameGenerator;

    @Test
    void testIsApplicableNull() {
        // Arrange
        final Setter setter = null;
        // Act
        final Executable isApplicable = () -> generator.isApplicable(setter);
        // Assert
        assertThrows(NullPointerException.class, isApplicable);
        verifyNoInteractions(builderClassNameGenerator);
    }

    @ParameterizedTest
    @MethodSource
    void testIsApplicableTrue(final Setter setter) {
        // Act
        final boolean actual = generator.isApplicable(setter);
        // Assert
        assertTrue(actual);
    }

    static Stream<Setter> testIsApplicableTrue() {
        return Stream.of( //
                Setter.builder() //
                        .methodName("setFloats") //
                        .propertyName("floats") //
                        .propertyType(new ArrayType(float[].class, float.class)) //
                        .visibility(Visibility.PRIVATE) //
                        .declaringClass(ClassWithCollections.class) //
                        .build(), //
                Setter.builder() //
                        .methodName("setStrings") //
                        .propertyName("strings") //
                        .propertyType(new ArrayType(String[].class, String.class)) //
                        .visibility(Visibility.PRIVATE) //
                        .declaringClass(ClassWithCollections.class) //
                        .build());
    }

    @ParameterizedTest
    @MethodSource
    void testIsApplicableFalse(final Setter setter) {
        // Act
        final boolean actual = generator.isApplicable(setter);
        // Assert
        assertFalse(actual);
    }

    static Stream<Setter> testIsApplicableFalse() {
        return testGenerateCodeGenerationException().map(args -> args.get()[1]).map(Setter.class::cast);
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateCodeNull(final BuilderMetadata builderMetadata, final Setter setter) {
        // Act
        final Executable generate = () -> generator.generate(builderMetadata, setter);
        // Assert
        assertThrows(NullPointerException.class, generate);
        verifyNoInteractions(builderClassNameGenerator);
    }

    static Stream<Arguments> testGenerateCodeNull() {
        return Stream.of( //
                Arguments.of(null, null),
                Arguments.of( //
                        BuilderMetadata.builder() //
                                .packageName("ignored") //
                                .name("Ignored") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(SimpleClass.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .build()) //
                                .build(), //
                        null), //
                Arguments.of( //
                        null, //
                        Setter.builder() //
                                .methodName("setFloats") //
                                .propertyName("floats") //
                                .propertyType(new ArrayType(float[].class, float.class)) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build()));
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateCodeGenerationException(final BuilderMetadata builderMetadata, final Setter setter) {
        // Act
        final ThrowingCallable generate = () -> generator.generate(builderMetadata, setter);
        // Assert
        assertThatThrownBy(generate) //
                .isInstanceOf(CodeGenerationException.class) //
                .hasMessageMatching("Generation of inner array class for .+ is not supported.") //
                .hasMessageContaining(setter.getPropertyType().toString());
        verifyNoInteractions(builderClassNameGenerator);
    }

    static Stream<Arguments> testGenerateCodeGenerationException() {
        return Stream.of( //
                Arguments.of( //
                        BuilderMetadata.builder() //
                                .packageName("ignored") //
                                .name("Ignored") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(SimpleClass.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .build()) //
                                .build(), //
                        Setter.builder() //
                                .methodName("setAnInt") //
                                .propertyName("anInt") //
                                .propertyType(new SimpleType(int.class)) //
                                .visibility(Visibility.PUBLIC) //
                                .declaringClass(SimpleClass.class) //
                                .build()), //
                Arguments.of( //
                        BuilderMetadata.builder() //
                                .packageName("ignored") //
                                .name("Ignored") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(SimpleClass.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .build()) //
                                .build(), //
                        Setter.builder() //
                                .methodName("setDeque") //
                                .propertyName("deque") //
                                .propertyType(new CollectionType(Deque.class, TypeUtils.wildcardType().build())) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build()), //
                Arguments.of( //
                        BuilderMetadata.builder() //
                                .packageName("ignored") //
                                .name("Ignored") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(SimpleClass.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .build()) //
                                .build(), //
                        Setter.builder() //
                                .methodName("setMap") //
                                .propertyName("map") //
                                .propertyType(new MapType(Map.class, String.class, Object.class)) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build()));
    }

    @ParameterizedTest
    @MethodSource
    void testGenerate(final BuilderMetadata builderMetadata, final Setter setter, final String expectedGetter, final String expectedInnerClass) {
        // Arrange
        when(builderClassNameGenerator.generateClassName(any())).thenReturn(ClassName.get(MockType.class));
        // Act
        final CollectionClassSpec actual = generator.generate(builderMetadata, setter);
        // Assert
        assertThat(actual).isNotNull();
        assertThat(actual.getGetter().toString()).isEqualToNormalizingNewlines(expectedGetter);
        assertThat(actual.getInnerClass().toString()).isEqualToNormalizingNewlines(expectedInnerClass);
        verify(builderClassNameGenerator).generateClassName(builderMetadata);
    }

    static Stream<Arguments> testGenerate() {
        final var mockTypeName = MockType.class.getName().replace('$', '.');
        return Stream.of( //
                Arguments.of( //
                        BuilderMetadata.builder() //
                                .packageName("ignored") //
                                .name("Ignored") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(SimpleClass.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .build()) //
                                .build(), //
                        Setter.builder() //
                                .methodName("setFloats") //
                                .propertyName("floats") //
                                .propertyType(new ArrayType(float[].class, float.class)) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        String.format(
                                """
                                        /**
                                         * Returns an inner builder for the array property {@code floats} for chained calls of adding items to it.
                                         * Can be used like follows:
                                         * <pre>
                                         * builder.floats()
                                         *        .add(item1)
                                         *        .add(item2)
                                         *        .and()
                                         *        .build()
                                         * </pre>
                                         * @return The inner builder for the array property {@code floats}.
                                         */
                                        public %1$s.ArrayFloats floats(
                                            ) {
                                          return new %1$s.ArrayFloats();
                                        }
                                        """,
                                mockTypeName), //
                        String.format(
                                """
                                        public class ArrayFloats {
                                          private java.util.List<java.lang.Float> list;

                                          /**
                                           * Adds an item to the array property {@code floats}.
                                           * @param item The item to add to the array {@code floats}.
                                           * @return This builder for chained calls.
                                           */
                                          public %1$s.ArrayFloats add(
                                              final float item) {
                                            if (this.list == null) {
                                              this.list = new java.util.ArrayList<>();
                                            }
                                            this.list.add(item);
                                            %1$s.this.callSetterFor.floats = true;
                                            return this;
                                          }

                                          /**
                                           * Returns the builder for the parent object.
                                           * @return The builder for the parent object.
                                           */
                                          public %1$s and(
                                              ) {
                                            if (this.list != null) {
                                              %1$s.this.fieldValue.floats = new float[this.list.size()];
                                              for (int i = 0; i < this.list.size(); i++) {
                                                %1$s.this.fieldValue.floats[i] = this.list.get(i);
                                              }
                                            }
                                            return %1$s.this;
                                          }
                                        }
                                        """,
                                mockTypeName)), //
                Arguments.of( //
                        BuilderMetadata.builder() //
                                .packageName("ignored") //
                                .name("Ignored") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(SimpleClass.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .build()) //
                                .build(), //
                        Setter.builder() //
                                .methodName("setStrings") //
                                .propertyName("strings") //
                                .propertyType(new ArrayType(String[].class, String.class)) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        String.format(
                                """
                                        /**
                                         * Returns an inner builder for the array property {@code strings} for chained calls of adding items to it.
                                         * Can be used like follows:
                                         * <pre>
                                         * builder.strings()
                                         *        .add(item1)
                                         *        .add(item2)
                                         *        .and()
                                         *        .build()
                                         * </pre>
                                         * @return The inner builder for the array property {@code strings}.
                                         */
                                        public %1$s.ArrayStrings strings(
                                            ) {
                                          return new %1$s.ArrayStrings();
                                        }
                                        """,
                                mockTypeName),
                        String.format(
                                """
                                        public class ArrayStrings {
                                          private java.util.List<java.lang.String> list;

                                          /**
                                           * Adds an item to the array property {@code strings}.
                                           * @param item The item to add to the array {@code strings}.
                                           * @return This builder for chained calls.
                                           */
                                          public %1$s.ArrayStrings add(
                                              final java.lang.String item) {
                                            if (this.list == null) {
                                              this.list = new java.util.ArrayList<>();
                                            }
                                            this.list.add(item);
                                            %1$s.this.callSetterFor.strings = true;
                                            return this;
                                          }

                                          /**
                                           * Returns the builder for the parent object.
                                           * @return The builder for the parent object.
                                           */
                                          public %1$s and(
                                              ) {
                                            if (this.list != null) {
                                              %1$s.this.fieldValue.strings = new java.lang.String[this.list.size()];
                                              for (int i = 0; i < this.list.size(); i++) {
                                                %1$s.this.fieldValue.strings[i] = this.list.get(i);
                                              }
                                            }
                                            return %1$s.this;
                                          }
                                        }
                                        """,
                                mockTypeName)));
    }

    private static class MockType {
        // no content
    }
}