package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.FieldValue;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.TypeNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.model.EncapsulatingClassSpec;
import io.github.tobi.laa.reflective.fluent.builders.model.*;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.lang.model.element.Modifier;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InnerClassFieldValueCodeGeneratorTest {

    @InjectMocks
    private InnerClassFieldValueCodeGenerator generator;

    @Mock
    private BuilderClassNameGenerator builderClassNameGenerator;

    @Mock
    private TypeNameGenerator typeNameGenerator;

    @Test
    void testGenerateNull() {
        // Arrange
        final BuilderMetadata builderMetadata = null;
        // Act
        final Executable generate = () -> generator.generate(builderMetadata);
        // Assert
        assertThrows(NullPointerException.class, generate);
        verifyNoInteractions(builderClassNameGenerator, typeNameGenerator);
    }

    @ParameterizedTest
    @MethodSource
    void testGenerate(final BuilderMetadata builderMetadata, final EncapsulatingClassSpec expected) {
        // Arrange
        when(builderClassNameGenerator.generateClassName(any())).thenReturn(ClassName.get(MockType.class));
        when(typeNameGenerator.generateTypeNameForParam(any(Setter.class))).thenReturn(TypeName.get(MockType.class));
        // Act
        final EncapsulatingClassSpec actual = generator.generate(builderMetadata);
        // Assert
        assertThat(actual).isNotNull();
        assertThat(actual.getField()).hasToString(expected.getField().toString());
        assertThat(actual.getInnerClass()).hasToString(expected.getInnerClass().toString());
        verify(builderClassNameGenerator).generateClassName(builderMetadata);
        builderMetadata.getBuiltType().getSetters().forEach(verify(typeNameGenerator)::generateTypeNameForParam);
    }

    private static Stream<Arguments> testGenerate() {
        final var fieldValue = ClassName.get(MockType.class).nestedClass(FieldValue.CLASS_NAME);
        return Stream.of(
                Arguments.of(
                        BuilderMetadata.builder() //
                                .packageName("ignored") //
                                .name("Ignored") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(SimpleClass.class) //
                                        .accessibleNonArgsConstructor(true) //
                                        .setter(SimpleSetter.builder()
                                                .methodName("ignored")
                                                .paramName("anInt")
                                                .paramType(int.class)
                                                .visibility(Visibility.PUBLIC)
                                                .build()) //
                                        .setter(SimpleSetter.builder()
                                                .methodName("ignored")
                                                .paramName("string")
                                                .paramType(String.class)
                                                .visibility(Visibility.PUBLIC)
                                                .build()) //
                                        .setter(SimpleSetter.builder()
                                                .methodName("ignored")
                                                .paramName("object")
                                                .paramType(Object.class)
                                                .visibility(Visibility.PUBLIC)
                                                .build()) //
                                        .build()) //
                                .build(), //
                        EncapsulatingClassSpec.builder() //
                                .field(FieldSpec //
                                        .builder(fieldValue, FieldValue.FIELD_NAME, PRIVATE, FINAL) //
                                        .initializer("new $T()", fieldValue) //
                                        .build()) //
                                .innerClass(TypeSpec //
                                        .classBuilder(fieldValue) //
                                        .addModifiers(Modifier.PRIVATE) //
                                        .addField(FieldSpec.builder(TypeName.get(MockType.class), "anInt").build()) //
                                        .addField(FieldSpec.builder(TypeName.get(MockType.class), "object").build()) //                                        .addField(FieldSpec.builder(TypeName.get(MockType.class), "string").build()) //
                                        .addField(FieldSpec.builder(TypeName.get(MockType.class), "string").build()) //
                                        .build()) //
                                .build()),
                Arguments.of(
                        BuilderMetadata.builder() //
                                .packageName("ignored") //
                                .name("Ignored") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(SimpleClass.class) //
                                        .accessibleNonArgsConstructor(true) //
                                        .setter(CollectionSetter.builder() //
                                                .methodName("ignored") //
                                                .paramName("set") //
                                                .paramType(Set.class) //
                                                .paramTypeArg(List.class) //
                                                .visibility(Visibility.PUBLIC) //
                                                .build()) //
                                        .setter(CollectionSetter.builder() //
                                                .methodName("ignored") //
                                                .paramName("deque") //
                                                .paramType(Deque.class) //
                                                .paramTypeArg(TypeUtils.wildcardType().build()) //
                                                .visibility(Visibility.PUBLIC) //
                                                .build()) //
                                        .setter(ArraySetter.builder() //
                                                .methodName("ignored") //
                                                .paramName("floats") //
                                                .paramType(float[].class) //
                                                .paramComponentType(float.class) //
                                                .visibility(Visibility.PUBLIC) //
                                                .build()) //
                                        .setter(MapSetter.builder() //
                                                .methodName("ignored") //
                                                .paramName("map") //
                                                .paramType(Map.class) //
                                                .keyType(String.class) //
                                                .valueType(Object.class) //
                                                .visibility(Visibility.PUBLIC) //
                                                .build()) //
                                        .build()) //
                                .build(), //
                        EncapsulatingClassSpec.builder() //
                                .field(FieldSpec //
                                        .builder(fieldValue, FieldValue.FIELD_NAME, PRIVATE, FINAL) //
                                        .initializer("new $T()", fieldValue) //
                                        .build()) //
                                .innerClass(TypeSpec //
                                        .classBuilder(fieldValue) //
                                        .addModifiers(Modifier.PRIVATE) //
                                        .addField(FieldSpec.builder(TypeName.get(MockType.class), "deque").build()) //
                                        .addField(FieldSpec.builder(TypeName.get(MockType.class), "floats").build()) //                                        .addField(FieldSpec.builder(TypeName.get(MockType.class), "string").build()) //
                                        .addField(FieldSpec.builder(TypeName.get(MockType.class), "map").build()) //                                     .addField(FieldSpec.builder(TypeName.get(MockType.class), "string").build()) //
                                        .addField(FieldSpec.builder(TypeName.get(MockType.class), "set").build()) //
                                        .build()) //
                                .build()));
    }

    private static class MockType {
        // no content
    }
}