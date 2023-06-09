package io.github.tobi.laa.reflective.fluent.builders.test.models.visibility;

import java.util.Objects;
import javax.annotation.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class PackagePrivateConstructorBuilder {
  private PackagePrivateConstructor objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private PackagePrivateConstructorBuilder(final PackagePrivateConstructor objectToBuild) {
    this.objectToBuild = objectToBuild;
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

  public PackagePrivateConstructorBuilder packagePrivate(final PackagePrivate packagePrivate) {
    fieldValue.packagePrivate = packagePrivate;
    callSetterFor.packagePrivate = true;
    return this;
  }

  public PackagePrivateConstructor build() {
    if (callSetterFor.intField) {
      objectToBuild.setIntField(fieldValue.intField);
    }
    if (callSetterFor.packagePrivate) {
      objectToBuild.setPackagePrivate(fieldValue.packagePrivate);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean intField;

    boolean packagePrivate;
  }

  private class FieldValue {
    int intField;

    PackagePrivate packagePrivate;
  }
}
