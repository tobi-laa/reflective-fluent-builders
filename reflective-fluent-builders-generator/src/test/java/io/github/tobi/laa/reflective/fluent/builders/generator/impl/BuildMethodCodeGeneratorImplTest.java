package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.MethodSpec;
import io.github.tobi.laa.reflective.fluent.builders.model.*;
import io.github.tobi.laa.reflective.fluent.builders.test.ClassGraphExtension;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.ClassWithHierarchy;
import io.github.tobi.laa.reflective.fluent.builders.test.models.jaxb.PetJaxb;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.SortedMap;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BuildMethodCodeGeneratorImplTest {

    @RegisterExtension
    static ClassGraphExtension classInfo = new ClassGraphExtension();

    private final BuildMethodCodeGeneratorImpl generator = new BuildMethodCodeGeneratorImpl();

    @Test
    void testGenerateNull() {
        // Arrange
        final BuilderMetadata builderMetadata = null;
        // Act
        final Executable generate = () -> generator.generateBuildMethod(builderMetadata);
        // Assert
        assertThrows(NullPointerException.class, generate);
    }

    @ParameterizedTest
    @MethodSource
    void testGenerate(final BuilderMetadata builderMetadata, final String expected) {
        // Act
        final MethodSpec actual = generator.generateBuildMethod(builderMetadata);
        // Assert
        assertThat(actual).hasToString(expected);
    }

    private static Stream<Arguments> testGenerate() {
        return Stream.of(
                Arguments.of(
                        BuilderMetadata.builder() //
                                .packageName("com.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                                .name("SimpleClassBuilder") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(SimpleClass.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .setter(SimpleSetter.builder() //
                                                .methodName("setAnInt") //
                                                .paramName("anInt") //
                                                .paramType(int.class) //
                                                .visibility(Visibility.PUBLIC) //
                                                .declaringClass(SimpleClass.class) //
                                                .build()) //
                                        .setter(ArraySetter.builder() //
                                                .methodName("setFloats") //
                                                .paramName("floats") //
                                                .paramType(float[].class) //
                                                .paramComponentType(float.class) //
                                                .visibility(Visibility.PRIVATE) //
                                                .declaringClass(SimpleClass.class) //
                                                .build()) //
                                        .build()) //
                                .build(), //
                        String.format(
                                "public %1$s build() {\n" +
                                        "  final %1$s objectToBuild = objectSupplier.get();\n" +
                                        "  if (this.callSetterFor.anInt) {\n" +
                                        "    objectToBuild.setAnInt(this.fieldValue.anInt);\n" +
                                        "  }\n" +
                                        "  if (this.callSetterFor.floats) {\n" +
                                        "    objectToBuild.setFloats(this.fieldValue.floats);\n" +
                                        "  }\n" +
                                        "  return objectToBuild;\n" +
                                        "}\n",
                                SimpleClass.class.getName())),
                Arguments.of(
                        BuilderMetadata.builder() //
                                .packageName("a.whole.different.pack") //
                                .name("AnotherBuilder") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(ClassWithHierarchy.class)) //
                                        .accessibleNonArgsConstructor(false) //
                                        .setter(MapSetter.builder() //
                                                .methodName("setSortedMap") //
                                                .paramName("sortedMap") //
                                                .paramType(SortedMap.class) //
                                                .keyType(Integer.class) //
                                                .valueType(Object.class) //
                                                .visibility(Visibility.PRIVATE) //
                                                .declaringClass(ClassWithHierarchy.class) //
                                                .build()) //
                                        .setter(CollectionSetter.builder() //
                                                .methodName("setList") //
                                                .paramName("list") //
                                                .paramType(List.class) //
                                                .paramTypeArg(String.class) //
                                                .visibility(Visibility.PRIVATE) //
                                                .declaringClass(ClassWithHierarchy.class) //
                                                .build()) //
                                        .build()) //
                                .build(), //
                        String.format("public %1$s build(\n" +
                                        "    ) {\n" +
                                        "  final %1$s objectToBuild = objectSupplier.get();\n" +
                                        "  if (this.callSetterFor.list) {\n" +
                                        "    objectToBuild.setList(this.fieldValue.list);\n" +
                                        "  }\n" +
                                        "  if (this.callSetterFor.sortedMap) {\n" +
                                        "    objectToBuild.setSortedMap(this.fieldValue.sortedMap);\n" +
                                        "  }\n" +
                                        "  return objectToBuild;\n" +
                                        "}\n",
                                ClassWithHierarchy.class.getName())),
                Arguments.of(
                        BuilderMetadata.builder() //
                                .packageName(PetJaxb.class.getPackage().getName()) //
                                .name("PetJaxbBuilder") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(PetJaxb.class)) //
                                        .accessibleNonArgsConstructor(false) //
                                        .setter(CollectionGetAndAdder.builder() //
                                                .methodName("getSiblings") //
                                                .paramName("siblings") //
                                                .paramType(List.class) //
                                                .paramTypeArg(PetJaxb.class) //
                                                .visibility(Visibility.PRIVATE) //
                                                .declaringClass(PetJaxb.class) //
                                                .build()) //
                                        .build()) //
                                .build(), //
                        String.format("public %1$s build() {\n" +
                                        "  final %1$s objectToBuild = objectSupplier.get();\n" +
                                        "  if (this.callSetterFor.siblings && this.fieldValue.siblings != null) {\n" +
                                        "    this.fieldValue.siblings.forEach(objectToBuild.getSiblings()::add);\n" +
                                        "  }\n" +
                                        "  return objectToBuild;\n" +
                                        "}\n",
                                PetJaxb.class.getName())));
    }
}