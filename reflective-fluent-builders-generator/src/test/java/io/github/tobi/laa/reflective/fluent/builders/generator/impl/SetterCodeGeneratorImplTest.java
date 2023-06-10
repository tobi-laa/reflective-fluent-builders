package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.TypeNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.*;
import io.github.tobi.laa.reflective.fluent.builders.service.api.SetterService;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.GetAndAdd;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Deque;
import java.util.List;
import java.util.SortedMap;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SetterCodeGeneratorImplTest {

    @InjectMocks
    private SetterCodeGeneratorImpl generator;

    @Mock
    private BuilderClassNameGenerator builderClassNameGenerator;

    @Mock
    private TypeNameGenerator typeNameGenerator;

    @Mock
    private SetterService setterService;

    @ParameterizedTest
    @MethodSource
    void testGenerateNull(final BuilderMetadata builderMetadata, final Setter setter) {
        // Act
        final Executable generate = () -> generator.generate(builderMetadata, setter);
        // Assert
        assertThrows(NullPointerException.class, generate);
        verifyNoInteractions(builderClassNameGenerator, typeNameGenerator, setterService);
    }

    private static Stream<Arguments> testGenerateNull() {
        return Stream.of( //
                Arguments.of(null, null),
                Arguments.of( //
                        BuilderMetadata.builder() //
                                .packageName("ignored") //
                                .name("Ignored") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(SimpleClass.class) //
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
    void testGenerate(final BuilderMetadata builderMetadata, final Setter setter, final String expected) {
        // Arrange
        when(builderClassNameGenerator.generateClassName(any())).thenReturn(ClassName.get(MockType.class));
        when(typeNameGenerator.generateTypeNameForParam(any(Setter.class))).thenReturn(TypeName.get(MockType.class));
        if (setter instanceof CollectionGetAndAdder) {
            when(setterService.dropGetterPrefix(any())).thenReturn(setter.getParamName());
        } else {
            when(setterService.dropSetterPrefix(any())).thenReturn(setter.getParamName());
        }
        // Act
        final MethodSpec actual = generator.generate(builderMetadata, setter);
        // Assert
        assertThat(actual).hasToString(expected);
        verify(builderClassNameGenerator).generateClassName(builderMetadata);
        verify(typeNameGenerator).generateTypeNameForParam(setter);
        if (setter instanceof CollectionGetAndAdder) {
            verify(setterService).dropGetterPrefix(setter.getMethodName());
        } else {
            verify(setterService).dropSetterPrefix(setter.getMethodName());
        }
    }

    private static Stream<Arguments> testGenerate() {
        final var mockTypeName = MockType.class.getName().replace('$', '.');
        final var builderMetadata = BuilderMetadata.builder() //
                .packageName("ignored") //
                .name("Ignored") //
                .builtType(BuilderMetadata.BuiltType.builder() //
                        .type(SimpleClass.class) //
                        .accessibleNonArgsConstructor(true) //
                        .build()) //
                .build();
        return Stream.of( //
                Arguments.of( //
                        builderMetadata, //
                        SimpleSetter.builder() //
                                .methodName("setAnInt") //
                                .paramName("anInt") //
                                .paramType(int.class) //
                                .visibility(Visibility.PUBLIC) //
                                .declaringClass(SimpleClass.class) //
                                .build(), //
                        String.format(
                                "public %1$s anInt(\n" +
                                        "    final %1$s anInt) {\n" +
                                        "  fieldValue.anInt = anInt;\n" +
                                        "  callSetterFor.anInt = true;\n" +
                                        "  return this;\n" +
                                        "}\n",
                                mockTypeName)), //
                Arguments.of( //
                        builderMetadata, //
                        ArraySetter.builder() //
                                .methodName("setFloats") //
                                .paramName("floats") //
                                .paramType(float[].class) //
                                .paramComponentType(float.class) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        String.format(
                                "public %1$s floats(\n" +
                                        "    final %1$s floats) {\n" +
                                        "  fieldValue.floats = floats;\n" +
                                        "  callSetterFor.floats = true;\n" +
                                        "  return this;\n" +
                                        "}\n",
                                mockTypeName)), //
                Arguments.of( //
                        builderMetadata, //
                        MapSetter.builder() //
                                .methodName("setSortedMap") //
                                .paramName("sortedMap") //
                                .paramType(SortedMap.class) //
                                .keyType(Integer.class) //
                                .valueType(Object.class) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        String.format(
                                "public %1$s sortedMap(\n" +
                                        "    final %1$s sortedMap) {\n" +
                                        "  fieldValue.sortedMap = sortedMap;\n" +
                                        "  callSetterFor.sortedMap = true;\n" +
                                        "  return this;\n" +
                                        "}\n",
                                mockTypeName)), //
                Arguments.of( //
                        builderMetadata, //
                        CollectionSetter.builder() //
                                .methodName("setList") //
                                .paramName("list") //
                                .paramType(List.class) //
                                .paramTypeArg(String.class) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        String.format(
                                "public %1$s list(\n" +
                                        "    final %1$s list) {\n" +
                                        "  fieldValue.list = list;\n" +
                                        "  callSetterFor.list = true;\n" +
                                        "  return this;\n" +
                                        "}\n",
                                mockTypeName)), //
                Arguments.of( //
                        builderMetadata, //
                        CollectionGetAndAdder.builder() //
                                .methodName("getList") //
                                .paramName("list") //
                                .paramType(List.class) //
                                .paramTypeArg(String.class) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(GetAndAdd.class) //
                                .build(), //
                        String.format(
                                "public %1$s list(\n" +
                                        "    final %1$s list) {\n" +
                                        "  fieldValue.list = list;\n" +
                                        "  callSetterFor.list = true;\n" +
                                        "  return this;\n" +
                                        "}\n",
                                mockTypeName)));
    }

    private static class MockType {
        // no content
    }
}