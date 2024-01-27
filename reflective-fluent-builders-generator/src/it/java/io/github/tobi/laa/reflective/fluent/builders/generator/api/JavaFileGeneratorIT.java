package io.github.tobi.laa.reflective.fluent.builders.generator.api;

import io.github.tobi.laa.reflective.fluent.builders.model.*;
import io.github.tobi.laa.reflective.fluent.builders.test.ClassGraphExtension;
import io.github.tobi.laa.reflective.fluent.builders.test.IntegrationTest;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithGenerics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import javax.inject.Inject;
import java.lang.reflect.TypeVariable;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class JavaFileGeneratorIT {

    @RegisterExtension
    static ClassGraphExtension classInfo = new ClassGraphExtension();

    @Inject
    private JavaFileGenerator javaFileGenerator;

    @Test
    void testGenerateJavaFile() {
        // Arrange
        final var builderMetadata = BuilderMetadata.builder() //
                .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.complex") //
                .name("ClassWithGenericsBuilder") //
                .builtType(BuilderMetadata.BuiltType.builder() //
                        .type(classInfo.get(ClassWithGenerics.class)) //
                        .accessibleNonArgsConstructor(true) //
                        .writeAccessor(Setter.builder() //
                                .methodName("setAnInt") //
                                .propertyName("anInt") //
                                .propertyType(new SimpleType(int.class)) //
                                .visibility(Visibility.PUBLIC) //
                                .declaringClass(ClassWithGenerics.class) //
                                .build()) //
                        .writeAccessor(Setter.builder() //
                                .methodName("setFloats") //
                                .propertyName("floats") //
                                .propertyType(new ArrayType(float[].class, float.class)) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithGenerics.class) //
                                .build()) //
                        .writeAccessor(Setter.builder() //
                                .methodName("setT") //
                                .propertyName("t") //
                                .propertyType(new SimpleType(typeVariableT())) //
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
                        "/**\n" +
                        " * Builder for {@link ClassWithGenerics}.\n" +
                        " */\n" +
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
                        "  private final Supplier<ClassWithGenerics> objectSupplier;\n" +
                        "\n" +
                        "  private final CallSetterFor callSetterFor = new CallSetterFor();\n" +
                        "\n" +
                        "  private final FieldValue fieldValue = new FieldValue();\n" +
                        "\n" +
                        "  /**\n" +
                        "   * Creates a new instance of {@link ClassWithGenerics} using the given {@code objectSupplier}.\n" +
                        "   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.\n" +
                        "   */\n" +
                        "  protected ClassWithGenericsBuilder(final Supplier<ClassWithGenerics> objectSupplier) {\n" +
                        "    this.objectSupplier = Objects.requireNonNull(objectSupplier);\n" +
                        "  }\n" +
                        "\n" +
                        "  /**\n" +
                        "   * Creates an instance of {@link ClassWithGenericsBuilder} that will work on a new instance of {@link ClassWithGenerics} once {@link #build()} is called.\n" +
                        "   */\n" +
                        "  public static ClassWithGenericsBuilder newInstance() {\n" +
                        "    return new ClassWithGenericsBuilder(ClassWithGenerics::new);\n" +
                        "  }\n" +
                        "\n" +
                        "  /**\n" +
                        "   * Creates an instance of {@link ClassWithGenericsBuilder} that will work on an instance of {@link ClassWithGenerics} that is created initially by the given {@code supplier} once {@link #build()} is called.\n" +
                        "   */\n" +
                        "  public static ClassWithGenericsBuilder withSupplier(final Supplier<ClassWithGenerics> supplier) {\n" +
                        "    return new ClassWithGenericsBuilder(supplier);\n" +
                        "  }\n" +
                        "\n" +
                        "  /**\n" +
                        "   * Returns an inner builder for the array property {@code floats} for chained calls of adding items to it.\n" +
                        "   * Can be used like follows:\n" +
                        "   * <pre>\n" +
                        "   * builder.floats()\n" +
                        "   *        .add(item1)\n" +
                        "   *        .add(item2)\n" +
                        "   *        .and()\n" +
                        "   *        .build()\n" +
                        "   * </pre>\n" +
                        "   * @return The inner builder for the array property {@code floats}.\n" +
                        "   */\n" +
                        "  public ArrayFloats floats() {\n" +
                        "    return new ArrayFloats();\n" +
                        "  }\n" +
                        "\n" +
                        "  /**\n" +
                        "   * Sets the value for the {@code anInt} property.\n" +
                        "   * To be more precise, this will lead to {@link ClassWithGenerics#setAnInt(int)} being called on construction of the object.\n" +
                        "   * @param anInt the value to set.\n" +
                        "   * @return This builder for chained calls.\n" +
                        "   */\n" +
                        "  public ClassWithGenericsBuilder anInt(final int anInt) {\n" +
                        "    this.fieldValue.anInt = anInt;\n" +
                        "    this.callSetterFor.anInt = true;\n" +
                        "    return this;\n" +
                        "  }\n" +
                        "\n" +
                        "  /**\n" +
                        "   * Sets the value for the {@code floats} property.\n" +
                        "   * To be more precise, this will lead to {@link ClassWithGenerics#setFloats(float[])} being called on construction of the object.\n" +
                        "   * @param floats the value to set.\n" +
                        "   * @return This builder for chained calls.\n" +
                        "   */\n" +
                        "  public ClassWithGenericsBuilder floats(final float[] floats) {\n" +
                        "    this.fieldValue.floats = floats;\n" +
                        "    this.callSetterFor.floats = true;\n" +
                        "    return this;\n" +
                        "  }\n" +
                        "\n" +
                        "  /**\n" +
                        "   * Sets the value for the {@code t} property.\n" +
                        "   * To be more precise, this will lead to {@link ClassWithGenerics#setT(T)} being called on construction of the object.\n" +
                        "   * @param t the value to set.\n" +
                        "   * @return This builder for chained calls.\n" +
                        "   */\n" +
                        "  public ClassWithGenericsBuilder t(final T t) {\n" +
                        "    this.fieldValue.t = t;\n" +
                        "    this.callSetterFor.t = true;\n" +
                        "    return this;\n" +
                        "  }\n" +
                        "\n" +
                        "  /**\n" +
                        "   * Performs the actual construction of an instance for {@link ClassWithGenerics}.\n" +
                        "   * @return The constructed instance. Never {@code null}.\n" +
                        "   */\n" +
                        "  public ClassWithGenerics build() {\n" +
                        "    final ClassWithGenerics objectToBuild = this.objectSupplier.get();\n" +
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
                        "    /**\n" +
                        "     * Adds an item to the array property {@code floats}.\n" +
                        "     * @param item The item to add to the array {@code floats}.\n" +
                        "     * @return This builder for chained calls.\n" +
                        "     */\n" +
                        "    public ArrayFloats add(final float item) {\n" +
                        "      if (this.list == null) {\n" +
                        "        this.list = new ArrayList<>();\n" +
                        "      }\n" +
                        "      this.list.add(item);\n" +
                        "      ClassWithGenericsBuilder.this.callSetterFor.floats = true;\n" +
                        "      return this;\n" +
                        "    }\n" +
                        "\n" +
                        "    /**\n" +
                        "     * Returns the builder for the parent object.\n" +
                        "     * @return The builder for the parent object.\n" +
                        "     */\n" +
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
                        "}");
    }

    private TypeVariable<?> typeVariableT() {
        return ClassWithGenerics.class.getTypeParameters()[0];
    }

}
