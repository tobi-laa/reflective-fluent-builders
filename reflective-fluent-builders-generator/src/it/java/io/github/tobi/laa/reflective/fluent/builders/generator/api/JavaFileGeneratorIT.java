package io.github.tobi.laa.reflective.fluent.builders.generator.api;

import io.github.tobi.laa.reflective.fluent.builders.model.ArraySetter;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.model.SimpleSetter;
import io.github.tobi.laa.reflective.fluent.builders.model.Visibility;
import io.github.tobi.laa.reflective.fluent.builders.test.IntegrationTest;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithGenerics;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.lang.reflect.TypeVariable;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class JavaFileGeneratorIT {

    @Inject
    private JavaFileGenerator javaFileGenerator;

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
                        "import java.lang.SuppressWarnings;\n" +
                        "import java.util.ArrayList;\n" +
                        "import java.util.List;\n" +
                        "import java.util.Objects;\n" +
                        "import java.util.function.Supplier;\n" +
                        "import javax.annotation.processing.Generated;\n" +
                        "\n" +
                        "@Generated(\n" +
                        "    value = \"io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator\",\n" +
                        "    date = \"3333-03-13T00:00Z[UTC]\"\n" +
                        ")\n" +
                        "public class ClassWithGenericsBuilder<T> {\n" +
                        "  /**\n" +
                        "   * This field is solely used to be able to detect generated builders via reflection at a later stage.\n" +
                        "   */\n" +
                        "  @SuppressWarnings(\"unused\")\n" +
                        "  private boolean ______generatedByReflectiveFluentBuildersGenerator;\n" +
                        "\n" +
                        "  private Supplier<ClassWithGenerics> objectSupplier;\n" +
                        "\n" +
                        "  private final CallSetterFor callSetterFor = new CallSetterFor();\n" +
                        "\n" +
                        "  private final FieldValue fieldValue = new FieldValue();\n" +
                        "\n" +
                        "  protected ClassWithGenericsBuilder(final Supplier<ClassWithGenerics> objectSupplier) {\n" +
                        "    this.objectSupplier = Objects.requireNonNull(objectSupplier);\n" +
                        "  }\n" +
                        "\n" +
                        "  public static ClassWithGenericsBuilder newInstance() {\n" +
                        "    return new ClassWithGenericsBuilder(ClassWithGenerics::new);\n" +
                        "  }\n" +
                        "\n" +
                        "  public static ClassWithGenericsBuilder withSupplier(final Supplier<ClassWithGenerics> supplier) {\n" +
                        "    return new ClassWithGenericsBuilder(supplier);\n" +
                        "  }\n" +
                        "\n" +
                        "  public ArrayFloats floats() {\n" +
                        "    return new ArrayFloats();\n" +
                        "  }\n" +
                        "\n" +
                        "  public ClassWithGenericsBuilder anInt(final int anInt) {\n" +
                        "    this.fieldValue.anInt = anInt;\n" +
                        "    this.callSetterFor.anInt = true;\n" +
                        "    return this;\n" +
                        "  }\n" +
                        "\n" +
                        "  public ClassWithGenericsBuilder floats(final float[] floats) {\n" +
                        "    this.fieldValue.floats = floats;\n" +
                        "    this.callSetterFor.floats = true;\n" +
                        "    return this;\n" +
                        "  }\n" +
                        "\n" +
                        "  public ClassWithGenericsBuilder t(final T t) {\n" +
                        "    this.fieldValue.t = t;\n" +
                        "    this.callSetterFor.t = true;\n" +
                        "    return this;\n" +
                        "  }\n" +
                        "\n" +
                        "  public ClassWithGenerics build() {\n" +
                        "    final ClassWithGenerics objectToBuild = objectSupplier.get();\n" +
                        "    if (this.callSetterFor.anInt) {\n" +
                        "      objectToBuild.setAnInt(this.fieldValue.anInt);\n" +
                        "    }\n" +
                        "    if (this.callSetterFor.floats) {\n" +
                        "      objectToBuild.setFloats(this.fieldValue.floats);\n" +
                        "    }\n" +
                        "    if (this.callSetterFor.t) {\n" +
                        "      objectToBuild.setT(this.fieldValue.t);\n" +
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
