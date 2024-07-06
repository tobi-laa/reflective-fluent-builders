package io.github.tobi.laa.reflective.fluent.builders.generator.api;

import com.squareup.javapoet.MethodSpec;
import io.github.tobi.laa.reflective.fluent.builders.model.*;
import io.github.tobi.laa.reflective.fluent.builders.test.ClassGraphExtension;
import io.github.tobi.laa.reflective.fluent.builders.test.IntegrationTest;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.DirectFieldAccess;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.GetAndAdd;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.inject.Inject;
import java.util.Deque;
import java.util.List;
import java.util.SortedMap;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@IntegrationTest
class SetterCodeGeneratorIT {

    @RegisterExtension
    static ClassGraphExtension classInfo = new ClassGraphExtension();

    @Inject
    private SetterCodeGenerator generator;

    @ParameterizedTest
    @MethodSource
    void testGenerateNull(final BuilderMetadata builderMetadata, final WriteAccessor writeAccessor) {
        // Act
        final Executable generate = () -> generator.generate(builderMetadata, writeAccessor);
        // Assert
        assertThrows(NullPointerException.class, generate);
    }

    static Stream<Arguments> testGenerateNull() {
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
                                .propertyType(new CollectionType(Deque.class, TypeUtils.wildcardType().build())) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build()));
    }

    @ParameterizedTest
    @MethodSource
    void testGenerate(final BuilderMetadata builderMetadata, final WriteAccessor writeAccessor, final String expected) {
        // Act
        final MethodSpec actual = generator.generate(builderMetadata, writeAccessor);
        // Assert
        assertThat(actual).hasToString(expected);
    }

    static Stream<Arguments> testGenerate() {
        final var mockTypeName = MockType.class.getName().replace('$', '.');
        final var builderMetadata = BuilderMetadata.builder() //
                .packageName(MockType.class.getPackageName()) //
                .name("SetterCodeGeneratorIT.MockType") //
                .builtType(BuilderMetadata.BuiltType.builder() //
                        .type(classInfo.get(SimpleClass.class)) //
                        .accessibleNonArgsConstructor(true) //
                        .build()) //
                .build();
        return Stream.of( //
                Arguments.of( //
                        builderMetadata, //
                        Setter.builder() //
                                .methodName("setAnInt") //
                                .propertyName("anInt") //
                                .propertyType(new SimpleType(int.class)) //
                                .visibility(Visibility.PUBLIC) //
                                .declaringClass(SimpleClass.class) //
                                .build(), //
                        String.format(
                                "/**\n" +
                                        " * Sets the value for the {@code anInt} property.\n" +
                                        " * To be more precise, this will lead to {@link io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass#setAnInt(int)} being called on construction of the object.\n" +
                                        " * @param anInt the value to set.\n" +
                                        " * @return This builder for chained calls.\n" +
                                        " */\n" +
                                        "public %1$s anInt(\n" +
                                        "    final int anInt) {\n" +
                                        "  this.fieldValue.anInt = anInt;\n" +
                                        "  this.callSetterFor.anInt = true;\n" +
                                        "  return this;\n" +
                                        "}\n",
                                mockTypeName)), //
                Arguments.of( //
                        builderMetadata, //
                        Setter.builder() //
                                .methodName("setFloats") //
                                .propertyName("floats") //
                                .propertyType(new ArrayType(float[].class, float.class)) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        String.format(
                                "/**\n" +
                                        " * Sets the value for the {@code floats} property.\n" +
                                        " * To be more precise, this will lead to {@link io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections#setFloats(float[])} being called on construction of the object.\n" +
                                        " * @param floats the value to set.\n" +
                                        " * @return This builder for chained calls.\n" +
                                        " */\n" +
                                        "public %1$s floats(\n" +
                                        "    final float[] floats) {\n" +
                                        "  this.fieldValue.floats = floats;\n" +
                                        "  this.callSetterFor.floats = true;\n" +
                                        "  return this;\n" +
                                        "}\n",
                                mockTypeName)), //
                Arguments.of( //
                        builderMetadata, //
                        Setter.builder() //
                                .methodName("setSortedMap") //
                                .propertyName("sortedMap") //
                                .propertyType(new MapType(SortedMap.class, Integer.class, Object.class)) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        String.format(
                                "/**\n" +
                                        " * Sets the value for the {@code sortedMap} property.\n" +
                                        " * To be more precise, this will lead to {@link io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections#setSortedMap(java.util.SortedMap)} being called on construction of the object.\n" +
                                        " * @param sortedMap the value to set.\n" +
                                        " * @return This builder for chained calls.\n" +
                                        " */\n" +
                                        "public %1$s sortedMap(\n" +
                                        "    final java.util.SortedMap sortedMap) {\n" +
                                        "  this.fieldValue.sortedMap = sortedMap;\n" +
                                        "  this.callSetterFor.sortedMap = true;\n" +
                                        "  return this;\n" +
                                        "}\n",
                                mockTypeName)), //
                Arguments.of( //
                        builderMetadata, //
                        Setter.builder() //
                                .methodName("setList") //
                                .propertyName("list") //
                                .propertyType(new CollectionType(List.class, String.class)) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        String.format(
                                "/**\n" +
                                        " * Sets the value for the {@code list} property.\n" +
                                        " * To be more precise, this will lead to {@link io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections#setList(java.util.List)} being called on construction of the object.\n" +
                                        " * @param list the value to set.\n" +
                                        " * @return This builder for chained calls.\n" +
                                        " */\n" +
                                        "public %1$s list(\n" +
                                        "    final java.util.List list) {\n" +
                                        "  this.fieldValue.list = list;\n" +
                                        "  this.callSetterFor.list = true;\n" +
                                        "  return this;\n" +
                                        "}\n",
                                mockTypeName)), //
                Arguments.of( //
                        builderMetadata, //
                        Getter.builder() //
                                .methodName("getList") //
                                .propertyName("list") //
                                .propertyType(new CollectionType(List.class, String.class)) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(GetAndAdd.class) //
                                .build(), //
                        String.format(
                                "/**\n" +
                                        " * Sets the value for the {@code list} property.\n" +
                                        " * To be more precise, this will lead to {@link io.github.tobi.laa.reflective.fluent.builders.test.models.complex.GetAndAdd#getList()} being called on construction of the object.\n" +
                                        " * @param list the value to set.\n" +
                                        " * @return This builder for chained calls.\n" +
                                        " */\n" +
                                        "public %1$s list(\n" +
                                        "    final java.util.List list) {\n" +
                                        "  this.fieldValue.list = list;\n" +
                                        "  this.callSetterFor.list = true;\n" +
                                        "  return this;\n" +
                                        "}\n",
                                mockTypeName)),
                Arguments.of( //
                        builderMetadata, //
                        FieldAccessor.builder() //
                                .propertyName("publicFieldNoSetter") //
                                .propertyType(new SimpleType(int.class)) //
                                .visibility(Visibility.PUBLIC) //
                                .declaringClass(DirectFieldAccess.class) //
                                .build(), //
                        String.format(
                                "/**\n" +
                                        " * Sets the value for the {@code publicFieldNoSetter} property.\n" +
                                        " * To be more precise, this will lead to the field {@link io.github.tobi.laa.reflective.fluent.builders.test.models.complex.DirectFieldAccess#publicFieldNoSetter} being modified directly on construction of the object.\n" +
                                        " * @param publicFieldNoSetter the value to set.\n" +
                                        " * @return This builder for chained calls.\n" +
                                        " */\n" +
                                        "public %1$s publicFieldNoSetter(\n" +
                                        "    final int publicFieldNoSetter) {\n" +
                                        "  this.fieldValue.publicFieldNoSetter = publicFieldNoSetter;\n" +
                                        "  this.callSetterFor.publicFieldNoSetter = true;\n" +
                                        "  return this;\n" +
                                        "}\n",
                                mockTypeName)));
    }

    private static class MockType {
        // no content
    }
}