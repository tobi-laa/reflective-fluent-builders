package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.MapInitializerCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.TypeNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.model.CollectionClassSpec;
import io.github.tobi.laa.reflective.fluent.builders.model.*;
import io.github.tobi.laa.reflective.fluent.builders.test.ClassGraphExtension;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junitpioneer.jupiter.cartesian.ArgumentSets;
import org.junitpioneer.jupiter.cartesian.CartesianTest;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Type;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InnerClassForMapCodeGeneratorTest {

    @RegisterExtension
    static ClassGraphExtension classInfo = new ClassGraphExtension();

    private InnerClassForMapCodeGenerator generator;

    @Mock
    private BuilderClassNameGenerator builderClassNameGenerator;

    @Mock
    private TypeNameGenerator typeNameGenerator;

    @Mock
    private MapInitializerCodeGenerator initializerGeneratorA;

    @Mock
    private MapInitializerCodeGenerator initializerGeneratorB;

    @BeforeEach
    void init() {
        generator = new InnerClassForMapCodeGenerator(builderClassNameGenerator, typeNameGenerator, List.of(initializerGeneratorA, initializerGeneratorB));
    }

    @Test
    void testIsApplicableNull() {
        // Arrange
        final Setter setter = null;
        // Act
        final Executable isApplicable = () -> generator.isApplicable(setter);
        // Assert
        assertThrows(NullPointerException.class, isApplicable);
        verifyNoInteractions(builderClassNameGenerator, initializerGeneratorA, initializerGeneratorB);
    }

    @CartesianTest
    @CartesianTest.MethodFactory("testIsApplicableTrue")
    void testIsApplicableTrue(final Setter setter, final boolean genAApplicable) {
        // Arrange
        if (genAApplicable) {
            when(initializerGeneratorA.isApplicable(any())).thenReturn(true);
        } else {
            when(initializerGeneratorB.isApplicable(any())).thenReturn(true);
        }
        // Act
        final boolean actual = generator.isApplicable(setter);
        // Assert
        assertTrue(actual);
    }

    @SuppressWarnings("unused")
    static ArgumentSets testIsApplicableTrue() {
        return ArgumentSets
                .argumentsForFirstParameter(testIsApplicableFalseNoInitializerGeneratorApplicable()) //
                .argumentsForNextParameter(true, false);
    }

    @ParameterizedTest
    @MethodSource
    void testIsApplicableFalseWrongType(final Setter setter) {
        // Act
        final boolean actual = generator.isApplicable(setter);
        // Assert
        assertFalse(actual);
        verifyNoInteractions(initializerGeneratorA, initializerGeneratorB);
    }

    static Stream<Setter> testIsApplicableFalseWrongType() {
        return testGenerateCodeGenerationExceptionWrongType().map(args -> args.get()[1]).map(Setter.class::cast);
    }

    @ParameterizedTest
    @MethodSource
    void testIsApplicableFalseNoInitializerGeneratorApplicable(final Setter setter) {
        // Act
        final boolean actual = generator.isApplicable(setter);
        // Assert
        assertFalse(actual);
    }

    static Stream<Setter> testIsApplicableFalseNoInitializerGeneratorApplicable() {
        return Stream.of( //
                Setter.builder() //
                        .methodName("setMap") //
                        .propertyName("map") //
                        .propertyType(new MapType(Map.class, String.class, TypeUtils.wildcardType().withUpperBounds(Object.class).build())) //
                        .visibility(Visibility.PRIVATE) //
                        .declaringClass(ClassWithCollections.class) //
                        .build(), //
                Setter.builder() //
                        .methodName("setSortedMap") //
                        .propertyName("sortedMap") //
                        .propertyType(new MapType(SortedMap.class, Integer.class, Object.class)) //
                        .visibility(Visibility.PRIVATE) //
                        .declaringClass(ClassWithCollections.class) //
                        .build());
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateCodeNull(final BuilderMetadata builderMetadata, final Setter setter) {
        // Act
        final Executable generate = () -> generator.generate(builderMetadata, setter);
        // Assert
        assertThrows(NullPointerException.class, generate);
        verifyNoInteractions(builderClassNameGenerator, initializerGeneratorA, initializerGeneratorB);
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
                                .methodName("setMap") //
                                .propertyName("map") //
                                .propertyType(new MapType(Map.class, String.class, TypeUtils.wildcardType().withUpperBounds(Object.class).build())) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build()));
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateCodeGenerationExceptionWrongType(final BuilderMetadata builderMetadata, final Setter setter) {
        // Act
        final ThrowingCallable generate = () -> generator.generate(builderMetadata, setter);
        // Assert
        assertThatThrownBy(generate) //
                .isInstanceOf(CodeGenerationException.class) //
                .hasMessageMatching("Generation of inner map class for .+ is not supported.") //
                .hasMessageContaining(setter.getPropertyType().toString());
        verifyNoInteractions(builderClassNameGenerator, initializerGeneratorB, initializerGeneratorB);
    }

    static Stream<Arguments> testGenerateCodeGenerationExceptionWrongType() {
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
                                .methodName("setFloats") //
                                .propertyName("floats") //
                                .propertyType(new ArrayType(float[].class, float.class)) //
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
                                .methodName("setDeque") //
                                .propertyName("deque") //
                                .propertyType(new CollectionType(Deque.class, TypeUtils.wildcardType().withUpperBounds(Object.class).build())) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build()));
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateCodeGenerationExceptionNoInitializerGeneratorApplicable(final BuilderMetadata builderMetadata, final Setter setter) {
        // Arrange
        when(builderClassNameGenerator.generateClassName(any())).thenReturn(ClassName.get(MockType.class));
        when(typeNameGenerator.generateTypeName(any(Type.class))).then(i -> TypeName.get((Type) i.getArgument(0)));
        // Act
        final ThrowingCallable generate = () -> generator.generate(builderMetadata, setter);
        // Assert
        assertThatThrownBy(generate) //
                .isInstanceOf(CodeGenerationException.class) //
                .hasMessageMatching("Could not generate initializer for .+") //
                .hasMessageContaining(setter.getPropertyType().toString());
    }

    static Stream<Arguments> testGenerateCodeGenerationExceptionNoInitializerGeneratorApplicable() {
        return testIsApplicableFalseNoInitializerGeneratorApplicable() //
                .map(setter -> Arguments.of( //
                        BuilderMetadata.builder() //
                                .packageName("ignored") //
                                .name("Ignored") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(SimpleClass.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .build()) //
                                .build(), //
                        setter));
    }

    @ParameterizedTest
    @MethodSource
    void testGenerate(final BuilderMetadata builderMetadata, final Setter setter, final String expectedGetter, final String expectedInnerClass) {
        // Arrange
        when(builderClassNameGenerator.generateClassName(any())).thenReturn(ClassName.get(MockType.class));
        when(typeNameGenerator.generateTypeName(any(Type.class))).then(i -> TypeName.get((Type) i.getArgument(0)));
        when(initializerGeneratorA.isApplicable(any())).thenReturn(true);
        when(initializerGeneratorA.generateMapInitializer(any())).thenReturn(CodeBlock.of("new MockMap<>()"));
        // Act
        final CollectionClassSpec actual = generator.generate(builderMetadata, setter);
        // Assert
        assertThat(actual).isNotNull();
        assertThat(actual.getGetter().toString()).isEqualToNormalizingNewlines(expectedGetter);
        assertThat(actual.getInnerClass().toString()).isEqualToNormalizingNewlines(expectedInnerClass);
        verify(builderClassNameGenerator).generateClassName(builderMetadata);
        final var mapType = (MapType) setter.getPropertyType();
        verify(typeNameGenerator).generateTypeName(mapType.getKeyType());
        verify(typeNameGenerator).generateTypeName(mapType.getValueType());
        verify(initializerGeneratorA).generateMapInitializer(mapType);
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
                                .methodName("setMap") //
                                .propertyName("map") //
                                .propertyType(new MapType(Map.class, String.class, TypeUtils.wildcardType().withUpperBounds(Object.class).build())) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        String.format(
                                """
                                        /**
                                         * Returns an inner builder for the map property {@code map} for chained calls of adding items to it.
                                         * Can be used like follows:
                                         * <pre>
                                         * builder.map()
                                         *        .put(key1, value1)
                                         *        .put(key2, value2)
                                         *        .and()
                                         *        .build()
                                         * </pre>
                                         * @return The inner builder for the map property {@code map}.
                                         */
                                        public %1$s.MapMap map(
                                            ) {
                                          return new %1$s.MapMap();
                                        }
                                        """,
                                mockTypeName), //
                        String.format(
                                """
                                        public class MapMap {
                                          /**
                                           * Adds an entry to the map property {@code map}.
                                           * @param key The key of the entry to add to the map {@code map}.
                                           * @param value The value of the entry to add to the map {@code map}.
                                           * @return This builder for chained calls.
                                           */
                                          public %1$s.MapMap put(
                                              final java.lang.String key, final ? value) {
                                            if (%1$s.this.fieldValue.map == null) {
                                              %1$s.this.fieldValue.map = new MockMap<>();
                                            }
                                            %1$s.this.fieldValue.map.put(key, value);
                                            %1$s.this.callSetterFor.map = true;
                                            return this;
                                          }

                                          /**
                                           * Returns the builder for the parent object.
                                           * @return The builder for the parent object.
                                           */
                                          public %1$s and(
                                              ) {
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
                                .methodName("setSortedMap") //
                                .propertyName("sortedMap") //
                                .propertyType(new MapType(SortedMap.class, Integer.class, Object.class)) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        String.format(
                                """
                                        /**
                                         * Returns an inner builder for the map property {@code sortedMap} for chained calls of adding items to it.
                                         * Can be used like follows:
                                         * <pre>
                                         * builder.sortedMap()
                                         *        .put(key1, value1)
                                         *        .put(key2, value2)
                                         *        .and()
                                         *        .build()
                                         * </pre>
                                         * @return The inner builder for the map property {@code sortedMap}.
                                         */
                                        public %1$s.MapSortedMap sortedMap(
                                            ) {
                                          return new %1$s.MapSortedMap();
                                        }
                                        """,
                                mockTypeName),
                        String.format(
                                """
                                        public class MapSortedMap {
                                          /**
                                           * Adds an entry to the map property {@code sortedMap}.
                                           * @param key The key of the entry to add to the map {@code sortedMap}.
                                           * @param value The value of the entry to add to the map {@code sortedMap}.
                                           * @return This builder for chained calls.
                                           */
                                          public %1$s.MapSortedMap put(
                                              final java.lang.Integer key, final java.lang.Object value) {
                                            if (%1$s.this.fieldValue.sortedMap == null) {
                                              %1$s.this.fieldValue.sortedMap = new MockMap<>();
                                            }
                                            %1$s.this.fieldValue.sortedMap.put(key, value);
                                            %1$s.this.callSetterFor.sortedMap = true;
                                            return this;
                                          }

                                          /**
                                           * Returns the builder for the parent object.
                                           * @return The builder for the parent object.
                                           */
                                          public %1$s and(
                                              ) {
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