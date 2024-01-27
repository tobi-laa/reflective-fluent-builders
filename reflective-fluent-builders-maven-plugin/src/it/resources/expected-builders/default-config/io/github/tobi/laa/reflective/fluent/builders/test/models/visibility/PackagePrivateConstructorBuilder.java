package io.github.tobi.laa.reflective.fluent.builders.test.models.visibility;

import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

/**
 * Builder for {@link PackagePrivateConstructor}.
 */
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

  /**
   * Creates a new instance of {@link PackagePrivateConstructor} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected PackagePrivateConstructorBuilder(
      final Supplier<PackagePrivateConstructor> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link PackagePrivateConstructorBuilder} that will work on a new instance of {@link PackagePrivateConstructor} once {@link #build()} is called.
   */
  public static PackagePrivateConstructorBuilder newInstance() {
    return new PackagePrivateConstructorBuilder(PackagePrivateConstructor::new);
  }

  /**
   * Creates an instance of {@link PackagePrivateConstructorBuilder} that will work on an instance of {@link PackagePrivateConstructor} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static PackagePrivateConstructorBuilder withSupplier(
      final Supplier<PackagePrivateConstructor> supplier) {
    return new PackagePrivateConstructorBuilder(supplier);
  }

  /**
   * Sets the value for the {@code intField} property.
   * To be more precise, this will lead to {@link PackagePrivateConstructor#setIntField(int)} being called on construction of the object.
   * @param intField the value to set.
   * @return This builder for chained calls.
   */
  public PackagePrivateConstructorBuilder intField(final int intField) {
    this.fieldValue.intField = intField;
    this.callSetterFor.intField = true;
    return this;
  }

  /**
   * Sets the value for the {@code packagePrivate} property.
   * To be more precise, this will lead to {@link PackagePrivateConstructor#setPackagePrivate(PackagePrivate)} being called on construction of the object.
   * @param packagePrivate the value to set.
   * @return This builder for chained calls.
   */
  public PackagePrivateConstructorBuilder packagePrivate(final PackagePrivate packagePrivate) {
    this.fieldValue.packagePrivate = packagePrivate;
    this.callSetterFor.packagePrivate = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link PackagePrivateConstructor}.
   * @return The constructed instance. Never {@code null}.
   */
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
