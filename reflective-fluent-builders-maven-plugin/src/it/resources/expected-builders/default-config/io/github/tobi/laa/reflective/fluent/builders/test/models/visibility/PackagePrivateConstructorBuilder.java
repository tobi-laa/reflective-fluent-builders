package io.github.tobi.laa.reflective.fluent.builders.test.models.visibility;

import java.lang.SuppressWarnings;
import java.util.Objects;
import javax.annotation.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class PackagePrivateConstructorBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private PackagePrivateConstructor objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected PackagePrivateConstructorBuilder(final PackagePrivateConstructor objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  protected PackagePrivateConstructorBuilder() {
    // noop
  }

  public static PackagePrivateConstructorBuilder newInstance() {
    return new PackagePrivateConstructorBuilder();
  }

  public static PackagePrivateConstructorBuilder thatModifies(
      final PackagePrivateConstructor objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new PackagePrivateConstructorBuilder(objectToModify);
  }

  public PackagePrivateConstructorBuilder intField(final int intField) {
    this.fieldValue.intField = intField;
    this.callSetterFor.intField = true;
    return this;
  }

  public PackagePrivateConstructorBuilder packagePrivate(final PackagePrivate packagePrivate) {
    this.fieldValue.packagePrivate = packagePrivate;
    this.callSetterFor.packagePrivate = true;
    return this;
  }

  public PackagePrivateConstructor build() {
    if (this.objectToBuild == null) {
      this.objectToBuild = new PackagePrivateConstructor();
    }
    if (this.callSetterFor.intField) {
      this.objectToBuild.setIntField(this.fieldValue.intField);
    }
    if (this.callSetterFor.packagePrivate) {
      this.objectToBuild.setPackagePrivate(this.fieldValue.packagePrivate);
    }
    return this.objectToBuild;
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
