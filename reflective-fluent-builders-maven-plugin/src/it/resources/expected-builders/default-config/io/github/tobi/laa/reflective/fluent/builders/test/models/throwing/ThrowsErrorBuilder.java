package io.github.tobi.laa.reflective.fluent.builders.test.models.throwing;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

/**
 * Builder for {@link ThrowsError}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ThrowsErrorBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("all")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<ThrowsError> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link ThrowsError} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected ThrowsErrorBuilder(final Supplier<ThrowsError> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link ThrowsErrorBuilder} that will work on a new instance of {@link ThrowsError} once {@link #build()} is called.
   */
  public static ThrowsErrorBuilder newInstance() {
    return new ThrowsErrorBuilder(ThrowsError::new);
  }

  /**
   * Creates an instance of {@link ThrowsErrorBuilder} that will work on an instance of {@link ThrowsError} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static ThrowsErrorBuilder withSupplier(final Supplier<ThrowsError> supplier) {
    return new ThrowsErrorBuilder(supplier);
  }

  /**
   * Sets the value for the {@code aString} property.
   * To be more precise, this will lead to {@link ThrowsError#setAString(String)} being called on construction of the object.
   * @param aString the value to set.
   * @return This builder for chained calls.
   */
  public ThrowsErrorBuilder aString(final String aString) {
    this.fieldValue.aString = aString;
    this.callSetterFor.aString = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link ThrowsError}.
   * @return The constructed instance. Never {@code null}.
   */
  public ThrowsError build() {
    final ThrowsError objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.aString) {
      objectToBuild.setAString(this.fieldValue.aString);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean aString;
  }

  private class FieldValue {
    String aString;
  }
}
