package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.google.common.collect.ImmutableList;
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
        generator = new InnerClassForCollectionCodeGenerator(builderClassNameGenerator, typeNameGenerator, ImmutableList.of(initializerGeneratorA, initializerGeneratorB));
    }

    @Test
    void testIsApplicableNull() {
        // Arrange
        final WriteAccessor writeAccessor = null;
        // Act
        final Executable isApplicable = () -> generator.isApplicable(writeAccessor);
        // Assert
        assertThrows(NullPointerException.class, isApplicable);
        verifyNoInteractions(builderClassNameGenerator, initializerGeneratorA, initializerGeneratorB);
    }

    @CartesianTest
    @CartesianTest.MethodFactory("testIsApplicableTrue")
    void testIsApplicableTrue(final WriteAccessor writeAccessor, final boolean genAApplicable) {
        // Arrange
        if (genAApplicable) {
            when(initializerGeneratorA.isApplicable(any())).thenReturn(true);
        } else {
            when(initializerGeneratorB.isApplicable(any())).thenReturn(true);
        }
        // Act
        final boolean actual = generator.isApplicable(writeAccessor);
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
    void testIsApplicableFalseWrongType(final WriteAccessor writeAccessor) {
        // Act
        final boolean actual = generator.isApplicable(writeAccessor);
        // Assert
        assertFalse(actual);
        verifyNoInteractions(initializerGeneratorA, initializerGeneratorB);
    }

    static Stream<WriteAccessor> testIsApplicableFalseWrongType() {
        return testGenerateCodeGenerationExceptionWrongType().map(args -> args.get()[1]).map(WriteAccessor.class::cast);
    }

    @ParameterizedTest
    @MethodSource
    void testIsApplicableFalseNoInitializerGeneratorApplicable(final WriteAccessor writeAccessor) {
        // Act
        final boolean actual = generator.isApplicable(writeAccessor);
        // Assert
        assertFalse(actual);
    }

    static Stream<Setter> testIsApplicableFalseNoInitializerGeneratorApplicable() {
        return Stream.of( //
                Setter.builder() //
                        .methodName("setDeque") //
                        .propertyName("deque") //
                        .propertyType(new CollectionType(Deque.class, TypeUtils.wildcardType().withUpperBounds(Object.class).build())) //
                        .visibility(Visibility.PRIVATE) //
                        .declaringClass(ClassWithCollections.class) //
                        .build(), //
                Setter.builder() //
                        .methodName("setList") //
                        .propertyName("list") //
                        .propertyType(new CollectionType(List.class, String.class)) //
                        .visibility(Visibility.PRIVATE) //
                        .declaringClass(ClassWithCollections.class) //
                        .build());
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateCodeNull(final BuilderMetadata builderMetadata, final WriteAccessor writeAccessor) {
        // Act
        final Executable generate = () -> generator.generate(builderMetadata, writeAccessor);
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
                                .methodName("setDeque") //
                                .propertyName("deque") //
                                .propertyType(new CollectionType(Deque.class, TypeUtils.wildcardType().withUpperBounds(Object.class).build())) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build()));
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateCodeGenerationExceptionWrongType(final BuilderMetadata builderMetadata, final WriteAccessor writeAccessor) {
        // Act
        final ThrowingCallable generate = () -> generator.generate(builderMetadata, writeAccessor);
        // Assert
        assertThatThrownBy(generate) //
                .isInstanceOf(CodeGenerationException.class) //
                .hasMessageMatching("Generation of inner collection class for .+ is not supported.") //
                .hasMessageContaining(writeAccessor.getPropertyType().toString());
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
                                .methodName("setMap") //
                                .propertyName("map") //
                                .propertyType(new MapType(Map.class, String.class, Object.class)) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build()),
                Arguments.of( //
                        BuilderMetadata.builder() //
                                .packageName("ignored") //
                                .name("Ignored") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(SimpleClass.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .build()) //
                                .build(), //
                        Adder.builder() //
                                .methodName("addItem") //
                                .propertyName("items") //
                                .propertyType(new CollectionType(List.class, String.class)) //
                                .paramName("item") //
                                .paramType(new SimpleType(String.class)) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build()));
    }

    @ParameterizedTest
    @MethodSource
    void testGenerateCodeGenerationExceptionNoInitializerGeneratorApplicable(final BuilderMetadata builderMetadata, final WriteAccessor writeAccessor) {
        // Arrange
        when(builderClassNameGenerator.generateClassName(any())).thenReturn(ClassName.get(MockType.class));
        when(typeNameGenerator.generateTypeName(any(Type.class))).then(i -> TypeName.get((Type) i.getArgument(0)));
        when(initializerGeneratorA.isApplicable(any())).thenReturn(true, false);
        // Act
        final ThrowingCallable generate = () -> generator.generate(builderMetadata, writeAccessor);
        // Assert
        assertThatThrownBy(generate) //
                .isInstanceOf(CodeGenerationException.class) //
                .hasMessageMatching("Could not generate initializer for .+") //
                .hasMessageContaining(writeAccessor.getPropertyType().toString());
    }

    static Stream<Arguments> testGenerateCodeGenerationExceptionNoInitializerGeneratorApplicable() {
        return testIsApplicableFalseNoInitializerGeneratorApplicable() //
                .map(writeAccessor -> Arguments.of( //
                        BuilderMetadata.builder() //
                                .packageName("ignored") //
                                .name("Ignored") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(SimpleClass.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .build()) //
                                .build(), //
                        writeAccessor));
    }

    @ParameterizedTest
    @MethodSource
    void testGenerate(final BuilderMetadata builderMetadata, final WriteAccessor writeAccessor, final String expectedGetter, final String expectedInnerClass) {
        // Arrange
        when(builderClassNameGenerator.generateClassName(any())).thenReturn(ClassName.get(MockType.class));
        when(typeNameGenerator.generateTypeName(any(Type.class))).then(i -> TypeName.get((Type) i.getArgument(0)));
        when(initializerGeneratorA.isApplicable(any())).thenReturn(true);
        when(initializerGeneratorA.generateCollectionInitializer(any())).thenReturn(CodeBlock.of("new MockList<>()"));
        // Act
        final CollectionClassSpec actual = generator.generate(builderMetadata, writeAccessor);
        // Assert
        assertThat(actual).isNotNull();
        assertThat(actual.getGetter().toString()).isEqualToNormalizingNewlines(expectedGetter);
        assertThat(actual.getInnerClass().toString()).isEqualToNormalizingNewlines(expectedInnerClass);
        verify(builderClassNameGenerator).generateClassName(builderMetadata);
        final CollectionType collectionType = (CollectionType) writeAccessor.getPropertyType();
        verify(typeNameGenerator).generateTypeName(collectionType.getTypeArg());
        verify(initializerGeneratorA).generateCollectionInitializer(collectionType);
    }

    static Stream<Arguments> testGenerate() {
        final String mockTypeName = MockType.class.getName().replace('$', '.');
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
                                .methodName("setDeque") //
                                .propertyName("deque") //
                                .propertyType(new CollectionType(Deque.class, TypeUtils.wildcardType().withUpperBounds(Object.class).build())) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        String.format(
                                "/**\n" +
                                        " * Returns an inner builder for the collection property {@code deque} for chained calls of adding items to it.\n" +
                                        " * Can be used like follows:\n" +
                                        " * <pre>\n" +
                                        " * builder.deque()\n" +
                                        " *        .add(item1)\n" +
                                        " *        .add(item2)\n" +
                                        " *        .and()\n" +
                                        " *        .build()\n" +
                                        " * </pre>\n" +
                                        " * @return The inner builder for the collection property {@code deque}.\n" +
                                        " */\n" +
                                        "public %1$s.CollectionDeque deque(\n" +
                                        "    ) {\n" +
                                        "  return new %1$s.CollectionDeque();\n" +
                                        "}\n",
                                mockTypeName), //
                        String.format(
                                "public class CollectionDeque {\n" +
                                        "  /**\n" +
                                        "   * Adds an item to the collection property {@code deque}.\n" +
                                        "   * @param item The item to add to the collection {@code deque}.\n" +
                                        "   * @return This builder for chained calls.\n" +
                                        "   */\n" +
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
                                        "  /**\n" +
                                        "   * Returns the builder for the parent object.\n" +
                                        "   * @return The builder for the parent object.\n" +
                                        "   */\n" +
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
                        Getter.builder() //
                                .methodName("getList") //
                                .propertyName("list") //
                                .propertyType(new CollectionType(List.class, String.class)) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        String.format(
                                "/**\n" +
                                        " * Returns an inner builder for the collection property {@code list} for chained calls of adding items to it.\n" +
                                        " * Can be used like follows:\n" +
                                        " * <pre>\n" +
                                        " * builder.list()\n" +
                                        " *        .add(item1)\n" +
                                        " *        .add(item2)\n" +
                                        " *        .and()\n" +
                                        " *        .build()\n" +
                                        " * </pre>\n" +
                                        " * @return The inner builder for the collection property {@code list}.\n" +
                                        " */\n" +
                                        "public %1$s.CollectionList list(\n" +
                                        "    ) {\n" +
                                        "  return new %1$s.CollectionList();\n" +
                                        "}\n",
                                mockTypeName),
                        String.format(
                                "public class CollectionList {\n" +
                                        "  /**\n" +
                                        "   * Adds an item to the collection property {@code list}.\n" +
                                        "   * @param item The item to add to the collection {@code list}.\n" +
                                        "   * @return This builder for chained calls.\n" +
                                        "   */\n" +
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
                                        "  /**\n" +
                                        "   * Returns the builder for the parent object.\n" +
                                        "   * @return The builder for the parent object.\n" +
                                        "   */\n" +
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