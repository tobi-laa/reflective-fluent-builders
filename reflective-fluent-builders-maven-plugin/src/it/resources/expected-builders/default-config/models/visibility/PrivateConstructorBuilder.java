package com.github.tobi.laa.reflective.fluent.builders.test.models.visibility;

import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "com.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class PrivateConstructorBuilder {
  private PrivateConstructor objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private PrivateConstructorBuilder(final PrivateConstructor objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static PrivateConstructorBuilder newInstance() {
    return new PrivateConstructorBuilder(null);
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
