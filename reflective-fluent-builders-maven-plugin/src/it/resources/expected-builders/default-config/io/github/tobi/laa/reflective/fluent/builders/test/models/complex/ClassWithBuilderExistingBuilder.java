package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ClassWithBuilderExistingBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<ClassWithBuilderExisting> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected ClassWithBuilderExistingBuilder(
      final Supplier<ClassWithBuilderExisting> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static ClassWithBuilderExistingBuilder withSupplier(
      final Supplier<ClassWithBuilderExisting> supplier) {
    return new ClassWithBuilderExistingBuilder(supplier);
  }

  public ClassWithBuilderExistingBuilder aField(final int aField) {
    this.fieldValue.aField = aField;
    this.callSetterFor.aField = true;
    return this;
  }

  public ClassWithBuilderExisting build() {
    final ClassWithBuilderExisting objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.aField) {
      objectToBuild.setAField(this.fieldValue.aField);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean aField;
  }

  private class FieldValue {
    int aField;
  }
}
