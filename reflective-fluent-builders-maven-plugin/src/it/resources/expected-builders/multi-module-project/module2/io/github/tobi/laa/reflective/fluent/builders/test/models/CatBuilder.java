package io.github.tobi.laa.reflective.fluent.builders.test.models;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

/**
 * Builder for {@link Cat}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class CatBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("all")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<Cat> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link Cat} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected CatBuilder(final Supplier<Cat> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link CatBuilder} that will work on a new instance of {@link Cat} once {@link #build()} is called.
   */
  public static CatBuilder newInstance() {
    return new CatBuilder(Cat::new);
  }

  /**
   * Creates an instance of {@link CatBuilder} that will work on an instance of {@link Cat} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static CatBuilder withSupplier(final Supplier<Cat> supplier) {
    return new CatBuilder(supplier);
  }

  /**
   * Sets the value for the {@code fur} property.
   * To be more precise, this will lead to {@link Cat#setFur(String)} being called on construction of the object.
   * @param fur the value to set.
   * @return This builder for chained calls.
   */
  public CatBuilder fur(final String fur) {
    this.fieldValue.fur = fur;
    this.callSetterFor.fur = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link Cat}.
   * @return The constructed instance. Never {@code null}.
   */
  public Cat build() {
    final Cat objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.fur) {
      objectToBuild.setFur(this.fieldValue.fur);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean fur;
  }

  private class FieldValue {
    String fur;
  }
}
