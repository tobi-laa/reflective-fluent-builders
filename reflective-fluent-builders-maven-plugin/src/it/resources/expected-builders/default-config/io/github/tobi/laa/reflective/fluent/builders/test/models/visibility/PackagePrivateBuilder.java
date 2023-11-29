package io.github.tobi.laa.reflective.fluent.builders.test.models.visibility;

import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class PackagePrivateBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<PackagePrivate> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected PackagePrivateBuilder(final Supplier<PackagePrivate> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static PackagePrivateBuilder newInstance() {
    return new PackagePrivateBuilder(PackagePrivate::new);
  }

  public static PackagePrivateBuilder withSupplier(final Supplier<PackagePrivate> supplier) {
    return new PackagePrivateBuilder(supplier);
  }

  public PackagePrivateBuilder intField(final int intField) {
    this.fieldValue.intField = intField;
    this.callSetterFor.intField = true;
    return this;
  }

  public PackagePrivate build() {
    final PackagePrivate objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.intField) {
      objectToBuild.setIntField(this.fieldValue.intField);
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
