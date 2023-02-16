package com.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator;
import com.github.tobi.laa.reflective.fluent.builders.model.ArraySetter;
import com.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import com.github.tobi.laa.reflective.fluent.builders.model.SimpleSetter;
import com.github.tobi.laa.reflective.fluent.builders.model.Visibility;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import com.google.inject.Guice;
import org.eclipse.sisu.space.SpaceModule;
import org.eclipse.sisu.space.URLClassSpace;
import org.eclipse.sisu.wire.WireModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JavaFileGeneratorIntegrationTest {

    private JavaFileGenerator javaFileGenerator;

    @BeforeEach
    void init() {
        final var classloader = getClass().getClassLoader();
        final var injector = Guice.createInjector(
                new WireModule(
                        new SpaceModule(new URLClassSpace(classloader))));
        javaFileGenerator = injector.getInstance(JavaFileGeneratorImpl.class);
    }

    @Test
    void testGenerateJavaFile() {
        // Arrange
        final var builderMetadata = BuilderMetadata.builder() //
                .packageName("com.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                .name("SimpleClassBuilder") //
                .builtType(BuilderMetadata.BuiltType.builder() //
                        .type(SimpleClass.class) //
                        .accessibleNonArgsConstructor(true) //
                        .setter(SimpleSetter.builder() //
                                .methodName("setAnInt") //
                                .paramName("anInt") //
                                .paramType(int.class) //
                                .visibility(Visibility.PUBLIC) //
                                .build()) //
                        .setter(ArraySetter.builder() //
                                .methodName("setFloats") //
                                .paramName("floats") //
                                .paramType(float[].class) //
                                .paramComponentType(float.class) //
                                .visibility(Visibility.PRIVATE) //
                                .build()) //
                        .build()) //
                .build();
        // Act
        final var actual = javaFileGenerator.generateJavaFile(builderMetadata);
        // Assert
        assertThat(actual).isNotNull();
        assertThat(actual.toString()).isEqualToIgnoringNewLines(
                "package com.github.tobi.laa.reflective.fluent.builders.test.models.simple;\n" +
                        "\n" +
                        "import java.lang.Float;\n" +
                        "import java.util.ArrayList;\n" +
                        "import java.util.List;\n" +
                        "import java.util.Objects;\n" +
                        "import javax.annotation.processing.Generated;\n" +
                        "\n" +
                        "@Generated(\n" +
                        "    value = \"com.github.tobi.laa.reflective.fluent.builders.generator.impl.JavaFileGeneratorImpl\",\n" +
                        "    date = \"3333-03-13T00:00Z[UTC]\"\n" +
                        ")\n" +
                        "public class SimpleClassBuilder {\n" +
                        "  private SimpleClass objectToBuild;\n" +
                        "\n" +
                        "  private final CallSetterFor callSetterFor = new CallSetterFor();\n" +
                        "\n" +
                        "  private final FieldValue fieldValue = new FieldValue();\n" +
                        "\n" +
                        "  private SimpleClassBuilder(final SimpleClass objectToBuild) {\n" +
                        "    this.objectToBuild = objectToBuild;\n" +
                        "  }\n" +
                        "\n" +
                        "  public static SimpleClassBuilder newInstance() {\n" +
                        "    return new SimpleClassBuilder(null);\n" +
                        "  }\n" +
                        "\n" +
                        "  public static SimpleClassBuilder thatModifies(final SimpleClass objectToModify) {\n" +
                        "    Objects.requireNonNull(objectToModify);\n" +
                        "    return new SimpleClassBuilder(objectToModify);\n" +
                        "  }\n" +
                        "\n" +
                        "  public ArrayFloats floats() {\n" +
                        "    return new ArrayFloats();\n" +
                        "  }\n" +
                        "\n" +
                        "  public SimpleClassBuilder anInt(final int anInt) {\n" +
                        "    fieldValue.anInt = anInt;\n" +
                        "    callSetterFor.anInt = true;\n" +
                        "    return this;\n" +
                        "  }\n" +
                        "\n" +
                        "  public SimpleClassBuilder floats(final float[] floats) {\n" +
                        "    fieldValue.floats = floats;\n" +
                        "    callSetterFor.floats = true;\n" +
                        "    return this;\n" +
                        "  }\n" +
                        "\n" +
                        "  public SimpleClass build() {\n" +
                        "    if (objectToBuild == null) {\n" +
                        "      objectToBuild = new SimpleClass();\n" +
                        "    }\n" +
                        "    if (callSetterFor.anInt) {\n" +
                        "      objectToBuild.fieldValue(setAnInt.anInt);\n" +
                        "    }\n" +
                        "    if (callSetterFor.floats) {\n" +
                        "      objectToBuild.fieldValue(setFloats.floats);\n" +
                        "    }\n" +
                        "    return objectToBuild;\n" +
                        "  }\n" +
                        "\n" +
                        "  private class CallSetterFor {\n" +
                        "    boolean anInt;\n" +
                        "\n" +
                        "    boolean floats;\n" +
                        "  }\n" +
                        "\n" +
                        "  private class FieldValue {\n" +
                        "    int anInt;\n" +
                        "\n" +
                        "    float[] floats;\n" +
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
                        "      SimpleClassBuilder.this.callSetterFor.floats = true;\n" +
                        "      return this;\n" +
                        "    }\n" +
                        "\n" +
                        "    public SimpleClassBuilder and() {\n" +
                        "      if (this.list != null) {\n" +
                        "        SimpleClassBuilder.this.fieldValue.floats = list.toArray(new float[0]);\n" +
                        "      }\n" +
                        "      return SimpleClassBuilder.this;\n" +
                        "    }\n" +
                        "  }\n" +
                        "}");
    }
}
