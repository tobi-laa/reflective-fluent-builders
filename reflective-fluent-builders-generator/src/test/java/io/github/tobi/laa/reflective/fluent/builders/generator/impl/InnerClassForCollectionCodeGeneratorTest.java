package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.CollectionInitializerCodeGenerator;
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
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InnerClassForCollectionCodeGeneratorTest {

    @RegisterExtension
    static ClassGraphExtension classInfo = new ClassGraphExtension();

    private InnerClassForCollectionCodeGenerator generator;

    @Mock
    private BuilderClassNameGenerator builderClassNameGenerator;

    @Mock
    private TypeNameGenerator typeNameGenerator;

    @Mock
    private CollectionInitializerCodeGenerator initializerGeneratorA;

    @Mock
    private CollectionInitializerCodeGenerator initializerGeneratorB;

    @BeforeEach
    void init() {
        generator = new InnerClassForCollectionCodeGenerator(builderClassNameGenerator, typeNameGenerator, List.of(initializerGeneratorA, initializerGeneratorB));
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

    private static Stream<Setter> testIsApplicableFalseWrongType() {
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

    private static Stream<Setter> testIsApplicableFalseNoInitializerGeneratorApplicable() {
        return Stream.of( //
                CollectionSetter.builder() //
                        .methodName("setDeque") //
                        .paramName("deque") //
                        .paramType(Deque.class) //
                        .paramTypeArg(TypeUtils.wildcardType().withUpperBounds(Object.class).build()) //
                        .visibility(Visibility.PRIVATE) //
                        .declaringClass(ClassWithCollections.class) //
                        .build(), //
                CollectionSetter.builder() //
                        .methodName("setList") //
                        .paramName("list") //
                        .paramType(List.class) //
                        .paramTypeArg(String.class) //
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

    private static Stream<Arguments> testGenerateCodeNull() {
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
                        CollectionSetter.builder() //
                                .methodName("setDeque") //
                                .paramName("deque") //
                                .paramType(Deque.class) //
                                .paramTypeArg(TypeUtils.wildcardType().build()) //
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
                .hasMessageMatching("Generation of inner collection class for .+ is not supported.") //
                .hasMessageContaining(setter.getParamType().toString());
        verifyNoInteractions(builderClassNameGenerator, initializerGeneratorB, initializerGeneratorB);
    }

    private static Stream<Arguments> testGenerateCodeGenerationExceptionWrongType() {
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
                        SimpleSetter.builder() //
                                .methodName("setAnInt") //
                                .paramName("anInt") //
                                .paramType(int.class) //
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
                        ArraySetter.builder() //
                                .methodName("setFloats") //
                                .paramName("floats") //
                                .paramType(float[].class) //
                                .paramComponentType(float.class) //
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
                        MapSetter.builder() //
                                .methodName("setMap") //
                                .paramName("map") //
                                .paramType(Map.class) //
                                .keyType(String.class) //
                                .valueType(Object.class) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build()));
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateCodeGenerationExceptionNoInitializerGeneratorApplicable(final BuilderMetadata builderMetadata, final Setter setter) {
        // Arrange
        when(builderClassNameGenerator.generateClassName(any())).thenReturn(ClassName.get(MockType.class));
        when(typeNameGenerator.generateTypeNameForParam(any(Type.class))).then(i -> TypeName.get((Type) i.getArgument(0)));
        // Act
        final ThrowingCallable generate = () -> generator.generate(builderMetadata, setter);
        // Assert
        assertThatThrownBy(generate) //
                .isInstanceOf(CodeGenerationException.class) //
                .hasMessageMatching("Could not generate initializer for .+") //
                .hasMessageContaining(setter.getParamType().toString());
    }

    private static Stream<Arguments> testGenerateCodeGenerationExceptionNoInitializerGeneratorApplicable() {
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
    void testGenerate(final BuilderMetadata builderMetadata, final CollectionSetter setter, final String expectedGetter, final String expectedInnerClass) {
        // Arrange
        when(builderClassNameGenerator.generateClassName(any())).thenReturn(ClassName.get(MockType.class));
        when(typeNameGenerator.generateTypeNameForParam(any(Type.class))).then(i -> TypeName.get((Type) i.getArgument(0)));
        when(initializerGeneratorA.isApplicable(any())).thenReturn(true);
        when(initializerGeneratorA.generateCollectionInitializer(any())).thenReturn(CodeBlock.of("new MockList<>()"));
        // Act
        final CollectionClassSpec actual = generator.generate(builderMetadata, setter);
        // Assert
        assertThat(actual).isNotNull();
        assertThat(actual.getGetter().toString()).isEqualToNormalizingNewlines(expectedGetter);
        assertThat(actual.getInnerClass().toString()).isEqualToNormalizingNewlines(expectedInnerClass);
        verify(builderClassNameGenerator).generateClassName(builderMetadata);
        verify(typeNameGenerator).generateTypeNameForParam(setter.getParamTypeArg());
        verify(initializerGeneratorA).generateCollectionInitializer(setter);
    }

    private static Stream<Arguments> testGenerate() {
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
                        CollectionSetter.builder() //
                                .methodName("setDeque") //
                                .paramName("deque") //
                                .paramType(Deque.class) //
                                .paramTypeArg(TypeUtils.wildcardType().withUpperBounds(Object.class).build()) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        String.format(
                                "public %1$s.CollectionDeque deque(\n" +
                                        "    ) {\n" +
                                        "  return new %1$s.CollectionDeque();\n" +
                                        "}\n",
                                mockTypeName), //
                        String.format(
                                "public class CollectionDeque {\n" +
                                        "  public %1$s.CollectionDeque add(\n" +
                                        "      final ? item) {\n" +
                                        "    if (%1$s.this.fieldValue.deque == null) {\n" +
                                        "      %1$s.this.fieldValue.deque = new MockList<>();\n" +
                                        "    }\n" +
                                        "    %1$s.this.fieldValue.deque.add(item);\n" +
                                        "    %1$s.this.callSetterFor.deque = true;\n" +
                                        "    return this;\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public %1$s and(\n" +
                                        "      ) {\n" +
                                        "    return %1$s.this;\n" +
                                        "  }\n" +
                                        "}\n",
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
                        CollectionGetAndAdder.builder() //
                                .methodName("getList") //
                                .paramName("list") //
                                .paramType(List.class) //
                                .paramTypeArg(String.class) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        String.format(
                                "public %1$s.CollectionList list(\n" +
                                        "    ) {\n" +
                                        "  return new %1$s.CollectionList();\n" +
                                        "}\n",
                                mockTypeName),
                        String.format(
                                "public class CollectionList {\n" +
                                        "  public %1$s.CollectionList add(\n" +
                                        "      final java.lang.String item) {\n" +
                                        "    if (%1$s.this.fieldValue.list == null) {\n" +
                                        "      %1$s.this.fieldValue.list = new MockList<>();\n" +
                                        "    }\n" +
                                        "    %1$s.this.fieldValue.list.add(item);\n" +
                                        "    %1$s.this.callSetterFor.list = true;\n" +
                                        "    return this;\n" +
                                        "  }\n" +
                                        "\n" +
                                        "  public %1$s and(\n" +
                                        "      ) {\n" +
                                        "    return %1$s.this;\n" +
                                        "  }\n" +
                                        "}\n",
                                mockTypeName)));
    }

    private static class MockType {
        // no content
    }
}