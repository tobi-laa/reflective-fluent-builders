package io.github.tobi.laa.reflective.fluent.builders.test.models.visibility;

import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

/**
 * Builder for {@link PackagePrivate}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class PackagePrivateBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("all")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<PackagePrivate> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link PackagePrivate} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected PackagePrivateBuilder(final Supplier<PackagePrivate> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link PackagePrivateBuilder} that will work on a new instance of {@link PackagePrivate} once {@link #build()} is called.
   */
  public static PackagePrivateBuilder newInstance() {
    return new PackagePrivateBuilder(PackagePrivate::new);
  }

  /**
   * Creates an instance of {@link PackagePrivateBuilder} that will work on an instance of {@link PackagePrivate} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static PackagePrivateBuilder withSupplier(final Supplier<PackagePrivate> supplier) {
    return new PackagePrivateBuilder(supplier);
  }

  /**
   * Sets the value for the {@code intField} property.
   * To be more precise, this will lead to {@link PackagePrivate#setIntField(int)} being called on construction of the object.
   * @param intField the value to set.
   * @return This builder for chained calls.
   */
  public PackagePrivateBuilder intField(final int intField) {
    this.fieldValue.intField = intField;
    this.callSetterFor.intField = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link PackagePrivate}.
   * @return The constructed instance. Never {@code null}.
   */
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
