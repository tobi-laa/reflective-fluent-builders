package com.github.tobi.laa.reflective.fluent.builders.test.models.visibility;

import java.util.Objects;
import javax.annotation.Generated;

@Generated(
    value = "com.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class PackagePrivateConstructorBuilder {
  private PackagePrivateConstructor objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private PackagePrivateConstructorBuilder(final PackagePrivateConstructor objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static PackagePrivateConstructorBuilder newInstance() {
    return new PackagePrivateConstructorBuilder(null);
  }

  public static PackagePrivateConstructorBuilder thatModifies(
      final PackagePrivateConstructor objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new PackagePrivateConstructorBuilder(objectToModify);
  }

  public PackagePrivateConstructorBuilder intField(final int intField) {
    fieldValue.intField = intField;
    callSetterFor.intField = true;
    return this;
  }

  public PackagePrivateConstructor build() {
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
