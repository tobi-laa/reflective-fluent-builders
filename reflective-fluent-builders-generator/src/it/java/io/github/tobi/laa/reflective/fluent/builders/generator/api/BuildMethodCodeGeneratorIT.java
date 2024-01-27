package io.github.tobi.laa.reflective.fluent.builders.generator.api;

import com.squareup.javapoet.MethodSpec;
import io.github.tobi.laa.reflective.fluent.builders.model.*;
import io.github.tobi.laa.reflective.fluent.builders.test.ClassGraphExtension;
import io.github.tobi.laa.reflective.fluent.builders.test.IntegrationTest;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.DirectFieldAccess;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.ClassWithHierarchy;
import io.github.tobi.laa.reflective.fluent.builders.test.models.jaxb.PetJaxb;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.inject.Inject;
import java.util.List;
import java.util.SortedMap;
import java.util.stream.Stream;

import static org.apache.commons.lang3.reflect.TypeUtils.parameterize;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@IntegrationTest
class BuildMethodCodeGeneratorIT {

    @RegisterExtension
    static ClassGraphExtension classInfo = new ClassGraphExtension();

    @Inject
    private BuildMethodCodeGenerator generator;

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
                                        .writeAccessor(Setter.builder() //
                                                .methodName("setAnInt") //
                                                .propertyName("anInt") //
                                                .propertyType(new SimpleType(int.class)) //
                                                .visibility(Visibility.PUBLIC) //
                                                .declaringClass(SimpleClass.class) //
                                                .build()) //
                                        .writeAccessor(Setter.builder() //
                                                .methodName("setFloats") //
                                                .propertyName("floats") //
                                                .propertyType(new ArrayType(float[].class, float.class)) //
                                                .visibility(Visibility.PRIVATE) //
                                                .declaringClass(SimpleClass.class) //
                                                .build()) //
                                        .build()) //
                                .build(), //
                        String.format(
                                "/**\n" +
                                        " * Performs the actual construction of an instance for {@link %1$s}.\n" +
                                        " * @return The constructed instance. Never {@code null}.\n" +
                                        " */\n" +
                                        "public %1$s build() {\n" +
                                        "  final %1$s objectToBuild = this.objectSupplier.get();\n" +
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
                                        .writeAccessor(Setter.builder() //
                                                .methodName("setSortedMap") //
                                                .propertyName("sortedMap") //
                                                .propertyType(new MapType(SortedMap.class, Integer.class, Object.class)) //
                                                .visibility(Visibility.PRIVATE) //
                                                .declaringClass(ClassWithHierarchy.class) //
                                                .build()) //
                                        .writeAccessor(Setter.builder() //
                                                .methodName("setList") //
                                                .propertyName("list") //
                                                .propertyType(new CollectionType(List.class, String.class)) //
                                                .visibility(Visibility.PRIVATE) //
                                                .declaringClass(ClassWithHierarchy.class) //
                                                .build()) //
                                        .build()) //
                                .build(), //
                        String.format("/**\n" +
                                        " * Performs the actual construction of an instance for {@link %1$s}.\n" +
                                        " * @return The constructed instance. Never {@code null}.\n" +
                                        " */\n" +
                                        "public %1$s build(\n" +
                                        "    ) {\n" +
                                        "  final %1$s objectToBuild = this.objectSupplier.get();\n" +
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
                                        .writeAccessor(Getter.builder() //
                                                .methodName("getSiblings") //
                                                .propertyName("siblings") //
                                                .propertyType(new CollectionType(List.class, PetJaxb.class)) //
                                                .visibility(Visibility.PRIVATE) //
                                                .declaringClass(PetJaxb.class) //
                                                .build()) //
                                        .build()) //
                                .build(), //
                        String.format("/**\n" +
                                        " * Performs the actual construction of an instance for {@link %1$s}.\n" +
                                        " * @return The constructed instance. Never {@code null}.\n" +
                                        " */\n" +
                                        "public %1$s build() {\n" +
                                        "  final %1$s objectToBuild = this.objectSupplier.get();\n" +
                                        "  if (this.callSetterFor.siblings && this.fieldValue.siblings != null) {\n" +
                                        "    this.fieldValue.siblings.forEach(objectToBuild.getSiblings()::add);\n" +
                                        "  }\n" +
                                        "  return objectToBuild;\n" +
                                        "}\n",
                                PetJaxb.class.getName())),
                Arguments.of(
                        BuilderMetadata.builder() //
                                .packageName(DirectFieldAccess.class.getPackageName()) //
                                .name("DirectFieldAccessBuilder") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(DirectFieldAccess.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .writeAccessor(FieldAccessor.builder() //
                                                .propertyName("publicFieldNoSetter") //
                                                .propertyType(new SimpleType(int.class)) //
                                                .visibility(Visibility.PUBLIC) //
                                                .declaringClass(DirectFieldAccess.class) //
                                                .build()) //
                                        .writeAccessor(FieldAccessor.builder() //
                                                .propertyName("publicFinalFieldNoSetter") //
                                                .propertyType(new CollectionType(parameterize(List.class, String.class), String.class)) //
                                                .visibility(Visibility.PUBLIC) //
                                                .isFinal(true) //
                                                .declaringClass(DirectFieldAccess.class) //
                                                .build()) //
                                        .build()) //
                                .build(), //
                        String.format("/**\n" +
                                        " * Performs the actual construction of an instance for {@link %1$s}.\n" +
                                        " * @return The constructed instance. Never {@code null}.\n" +
                                        " */\n" +
                                        "public %1$s build() {\n" +
                                        "  final %1$s objectToBuild = this.objectSupplier.get();\n" +
                                        "  if (this.callSetterFor.publicFieldNoSetter) {\n" +
                                        "    objectToBuild.publicFieldNoSetter = this.fieldValue.publicFieldNoSetter;\n" +
                                        "  }\n" +
                                        "  if (this.callSetterFor.publicFinalFieldNoSetter && this.fieldValue.publicFinalFieldNoSetter != null) {\n" +
                                        "    this.fieldValue.publicFinalFieldNoSetter.forEach(objectToBuild.publicFinalFieldNoSetter::add);\n" +
                                        "  }\n" +
                                        "  return objectToBuild;\n" +
                                        "}\n",
                                DirectFieldAccess.class.getName())));
    }
}