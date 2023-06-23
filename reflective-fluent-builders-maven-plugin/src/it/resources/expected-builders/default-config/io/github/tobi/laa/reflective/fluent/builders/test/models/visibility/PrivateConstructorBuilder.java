package io.github.tobi.laa.reflective.fluent.builders.test.models.visibility;

import java.util.Objects;
import javax.annotation.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class PrivateConstructorBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private PrivateConstructor objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private PrivateConstructorBuilder(final PrivateConstructor objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static PrivateConstructorBuilder thatModifies(final PrivateConstructor objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new PrivateConstructorBuilder(objectToModify);
  }

  public PrivateConstructorBuilder intField(final int intField) {
    fieldValue.intField = intField;
    callSetterFor.intField = true;
    return this;
  }

  public PrivateConstructor build() {
    if (callSetterFor.intField) {
      objectToBuild.setIntField(fieldValue.intField);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean intField;
  }

  private class FieldValue {
    int intField;
  }
}
