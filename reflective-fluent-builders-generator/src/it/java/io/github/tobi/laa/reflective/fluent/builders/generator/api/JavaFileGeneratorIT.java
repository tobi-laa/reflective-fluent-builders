package io.github.tobi.laa.reflective.fluent.builders.generator.api;

import com.google.inject.Guice;
import io.github.tobi.laa.reflective.fluent.builders.model.ArraySetter;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.model.SimpleSetter;
import io.github.tobi.laa.reflective.fluent.builders.model.Visibility;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithGenerics;
import lombok.SneakyThrows;
import org.eclipse.sisu.space.SpaceModule;
import org.eclipse.sisu.space.URLClassSpace;
import org.eclipse.sisu.wire.WireModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.TypeVariable;

import static org.assertj.core.api.Assertions.assertThat;

class JavaFileGeneratorIT {

    private JavaFileGenerator javaFileGenerator;

    @BeforeEach
    @SneakyThrows
    void init() {
        final var classloader = getClass().getClassLoader();
        final var injector = Guice.createInjector(
                new WireModule(
                        new SpaceModule(new URLClassSpace(classloader))));
        javaFileGenerator = (JavaFileGenerator) injector.getInstance(Class.forName("io.github.tobi.laa.reflective.fluent.builders.generator.impl.JavaFileGeneratorImpl"));
    }

    @Test
    void testGenerateJavaFile() {
        // Arrange
        final var builderMetadata = BuilderMetadata.builder() //
                .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.complex") //
                .name("ClassWithGenericsBuilder") //
                .builtType(BuilderMetadata.BuiltType.builder() //
                        .type(ClassWithGenerics.class) //
                        .accessibleNonArgsConstructor(true) //
                        .setter(SimpleSetter.builder() //
                                .methodName("setAnInt") //
                                .paramName("anInt") //
                                .paramType(int.class) //
                                .visibility(Visibility.PUBLIC) //
                                .declaringClass(ClassWithGenerics.class) //
                                .build()) //
                        .setter(ArraySetter.builder() //
                                .methodName("setFloats") //
                                .paramName("floats") //
                                .paramType(float[].class) //
                                .paramComponentType(float.class) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithGenerics.class) //
                                .build()) //
                        .setter(SimpleSetter.builder() //
                                .methodName("setT") //
                                .paramName("t") //
                                .paramType(typeVariableT()) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithGenerics.class) //
                                .build()) //
                        .build()) //
                .build();
        // Act
        final var actual = javaFileGenerator.generateJavaFile(builderMetadata);
        // Assert
        assertThat(actual).isNotNull();
        assertThat(actual.toString()).isEqualToIgnoringNewLines(
                "package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;\n" +
                        "\n" +
                        "import java.lang.Float;\n" +
                        "import java.util.ArrayList;\n" +
                        "import java.util.List;\n" +
                        "import java.util.Objects;\n" +
                        "import javax.annotation.processing.Generated;\n" +
                        "\n" +
                        "@Generated(\n" +
                        "    value = \"io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator\",\n" +
                        "    date = \"3333-03-13T00:00Z[UTC]\"\n" +
                        ")\n" +
                        "public class ClassWithGenericsBuilder<T> {\n" +
                        "  private boolean ______generatedByReflectiveFluentBuildersGenerator;\n" +
                        "\n" +
                        "  private ClassWithGenerics objectToBuild;\n" +
                        "\n" +
                        "  private final CallSetterFor callSetterFor = new CallSetterFor();\n" +
                        "\n" +
                        "  private final FieldValue fieldValue = new FieldValue();\n" +
                        "\n" +
                        "  private ClassWithGenericsBuilder(final ClassWithGenerics objectToBuild) {\n" +
                        "    this.objectToBuild = objectToBuild;\n" +
                        "  }\n" +
                        "\n" +
                        "  public static ClassWithGenericsBuilder newInstance() {\n" +
                        "    return new ClassWithGenericsBuilder(null);\n" +
                        "  }\n" +
                        "\n" +
                        "  public static ClassWithGenericsBuilder thatModifies(final ClassWithGenerics objectToModify) {\n" +
                        "    Objects.requireNonNull(objectToModify);\n" +
                        "    return new ClassWithGenericsBuilder(objectToModify);\n" +
                        "  }\n" +
                        "\n" +
                        "  public ArrayFloats floats() {\n" +
                        "    return new ArrayFloats();\n" +
                        "  }\n" +
                        "\n" +
                        "  public ClassWithGenericsBuilder anInt(final int anInt) {\n" +
                        "    fieldValue.anInt = anInt;\n" +
                        "    callSetterFor.anInt = true;\n" +
                        "    return this;\n" +
                        "  }\n" +
                        "\n" +
                        "  public ClassWithGenericsBuilder floats(final float[] floats) {\n" +
                        "    fieldValue.floats = floats;\n" +
                        "    callSetterFor.floats = true;\n" +
                        "    return this;\n" +
                        "  }\n" +
                        "\n" +
                        "  public ClassWithGenericsBuilder t(final T t) {\n" +
                        "    fieldValue.t = t;\n" +
                        "    callSetterFor.t = true;\n" +
                        "    return this;\n" +
                        "  }\n" +
                        "\n" +
                        "  public ClassWithGenerics build() {\n" +
                        "    if (objectToBuild == null) {\n" +
                        "      objectToBuild = new ClassWithGenerics();\n" +
                        "    }\n" +
                        "    if (callSetterFor.anInt) {\n" +
                        "      objectToBuild.setAnInt(fieldValue.anInt);\n" +
                        "    }\n" +
                        "    if (callSetterFor.floats) {\n" +
                        "      objectToBuild.setFloats(fieldValue.floats);\n" +
                        "    }\n" +
                        "    if (callSetterFor.t) {\n" +
                        "      objectToBuild.setT(fieldValue.t);\n" +
                        "    }\n" +
                        "    return objectToBuild;\n" +
                        "  }\n" +
                        "\n" +
                        "  private class CallSetterFor {\n" +
                        "    boolean anInt;\n" +
                        "\n" +
                        "    boolean floats;\n" +
                        "\n" +
                        "    boolean t;\n" +
                        "  }\n" +
                        "\n" +
                        "  private class FieldValue {\n" +
                        "    int anInt;\n" +
                        "\n" +
                        "    float[] floats;\n" +
                        "\n" +
                        "    T t;\n" +
                        "  }\n" +
                        "\n" +
                        "  public class ArrayFloats {\n" +
                        "    private List<Float> list;\n" +
                        "\n" +
                        "    public ArrayFloats add(final float item) {\n" +
                        "      if (this.list == null) {\n" +
                        "        this.list = new ArrayList<>();\n" +
                        "      }\n" +
                        "      this.list.add(item);\n" +
                        "      ClassWithGenericsBuilder.this.callSetterFor.floats = true;\n" +
                        "      return this;\n" +
                        "    }\n" +
                        "\n" +
                        "    public ClassWithGenericsBuilder and() {\n" +
                        "      if (this.list != null) {\n" +
                        "        ClassWithGenericsBuilder.this.fieldValue.floats = new float[this.list.size()];\n" +
                        "        for (int i = 0; i < this.list.size(); i++) {\n" +
                        "          ClassWithGenericsBuilder.this.fieldValue.floats[i] = this.list.get(i);\n" +
                        "        }\n" +
                        "      }\n" +
                        "      return ClassWithGenericsBuilder.this;\n" +
                        "    }\n" +
                        "  }\n" +
                        "}\n");
    }

    private TypeVariable<?> typeVariableT() {
        return ClassWithGenerics.class.getTypeParameters()[0];
    }

}
