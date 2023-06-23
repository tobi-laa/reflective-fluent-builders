package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import java.util.Objects;
import javax.annotation.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ClassWithBuilderExistingBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private ClassWithBuilderExisting objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private ClassWithBuilderExistingBuilder(final ClassWithBuilderExisting objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static ClassWithBuilderExistingBuilder thatModifies(
      final ClassWithBuilderExisting objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new ClassWithBuilderExistingBuilder(objectToModify);
  }

  public ClassWithBuilderExistingBuilder aField(final int aField) {
    fieldValue.aField = aField;
    callSetterFor.aField = true;
    return this;
  }

  public ClassWithBuilderExisting build() {
    if (callSetterFor.aField) {
      objectToBuild.setAField(fieldValue.aField);
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
