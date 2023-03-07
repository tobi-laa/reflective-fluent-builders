package com.github.tobi.laa.reflective.fluent.builders.test.models.visibility;

import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "com.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class PackagePrivateBuilder {
  private PackagePrivate objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private PackagePrivateBuilder(final PackagePrivate objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static PackagePrivateBuilder newInstance() {
    return new PackagePrivateBuilder(null);
  }

  public static PackagePrivateBuilder thatModifies(final PackagePrivate objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new PackagePrivateBuilder(objectToModify);
  }

  public PackagePrivateBuilder intField(final int intField) {
    fieldValue.intField = intField;
    callSetterFor.intField = true;
    return this;
  }

  public PackagePrivate build() {
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
