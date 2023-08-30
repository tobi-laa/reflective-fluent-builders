package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import java.lang.SuppressWarnings;
import java.util.Objects;
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

  private ClassWithBuilderExisting objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected ClassWithBuilderExistingBuilder(final ClassWithBuilderExisting objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static ClassWithBuilderExistingBuilder thatModifies(
      final ClassWithBuilderExisting objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new ClassWithBuilderExistingBuilder(objectToModify);
  }

  public ClassWithBuilderExistingBuilder aField(final int aField) {
    this.fieldValue.aField = aField;
    this.callSetterFor.aField = true;
    return this;
  }

  public ClassWithBuilderExisting build() {
    if (this.callSetterFor.aField) {
      this.objectToBuild.setAField(this.fieldValue.aField);
    }
    return this.objectToBuild;
  }

  private class CallSetterFor {
    boolean aField;
  }

  private class FieldValue {
    int aField;
  }
}
