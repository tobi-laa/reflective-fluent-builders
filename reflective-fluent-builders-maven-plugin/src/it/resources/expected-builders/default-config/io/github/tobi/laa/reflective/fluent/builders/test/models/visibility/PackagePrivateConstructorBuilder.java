package io.github.tobi.laa.reflective.fluent.builders.test.models.visibility;

import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
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

  private final Supplier<PackagePrivateConstructor> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected PackagePrivateConstructorBuilder(
      final Supplier<PackagePrivateConstructor> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static PackagePrivateConstructorBuilder newInstance() {
    return new PackagePrivateConstructorBuilder(PackagePrivateConstructor::new);
  }

  public static PackagePrivateConstructorBuilder withSupplier(
      final Supplier<PackagePrivateConstructor> supplier) {
    return new PackagePrivateConstructorBuilder(supplier);
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
    final PackagePrivateConstructor objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.intField) {
      objectToBuild.setIntField(this.fieldValue.intField);
    }
    if (this.callSetterFor.packagePrivate) {
      objectToBuild.setPackagePrivate(this.fieldValue.packagePrivate);
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
