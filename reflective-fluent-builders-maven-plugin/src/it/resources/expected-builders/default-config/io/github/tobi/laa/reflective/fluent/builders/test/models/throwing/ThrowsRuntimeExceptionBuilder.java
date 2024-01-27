package io.github.tobi.laa.reflective.fluent.builders.test.models.throwing;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

/**
 * Builder for {@link ThrowsRuntimeException}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ThrowsRuntimeExceptionBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<ThrowsRuntimeException> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link ThrowsRuntimeException} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected ThrowsRuntimeExceptionBuilder(final Supplier<ThrowsRuntimeException> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link ThrowsRuntimeExceptionBuilder} that will work on a new instance of {@link ThrowsRuntimeException} once {@link #build()} is called.
   */
  public static ThrowsRuntimeExceptionBuilder newInstance() {
    return new ThrowsRuntimeExceptionBuilder(ThrowsRuntimeException::new);
  }

  /**
   * Creates an instance of {@link ThrowsRuntimeExceptionBuilder} that will work on an instance of {@link ThrowsRuntimeException} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static ThrowsRuntimeExceptionBuilder withSupplier(
      final Supplier<ThrowsRuntimeException> supplier) {
    return new ThrowsRuntimeExceptionBuilder(supplier);
  }

  /**
   * Sets the value for the {@code aString} property.
   * To be more precise, this will lead to {@link ThrowsRuntimeException#setAString(String)} being called on construction of the object.
   * @param aString the value to set.
   * @return This builder for chained calls.
   */
  public ThrowsRuntimeExceptionBuilder aString(final String aString) {
    this.fieldValue.aString = aString;
    this.callSetterFor.aString = true;
    return this;
  }

  /**
   * Sets the value for the {@code anInt} property.
   * To be more precise, this will lead to {@link ThrowsRuntimeException#setAnInt(int)} being called on construction of the object.
   * @param anInt the value to set.
   * @return This builder for chained calls.
   */
  public ThrowsRuntimeExceptionBuilder anInt(final int anInt) {
    this.fieldValue.anInt = anInt;
    this.callSetterFor.anInt = true;
    return this;
  }

  /**
   * Adds a value to the {@code anItems} property.
   * To be more precise, this will lead to {@link ThrowsRuntimeException#addAnItem(List<String>)} being called on construction of the object.
   * @param anItem the value to add to {@code anItems}.
   * @return This builder for chained calls.
   */
  public ThrowsRuntimeExceptionBuilder anItem(final String anItem) {
    if (this.fieldValue.anItems == null) {
      this.fieldValue.anItems = new ArrayList<>();
    }
    this.fieldValue.anItems.add(anItem);
    this.callSetterFor.anItems = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link ThrowsRuntimeException}.
   * @return The constructed instance. Never {@code null}.
   */
  public ThrowsRuntimeException build() {
    final ThrowsRuntimeException objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.aString) {
      objectToBuild.setAString(this.fieldValue.aString);
    }
    if (this.callSetterFor.anInt) {
      objectToBuild.setAnInt(this.fieldValue.anInt);
    }
    if (this.callSetterFor.anItems && this.fieldValue.anItems != null) {
      this.fieldValue.anItems.forEach(objectToBuild::addAnItem);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean aString;

    boolean anInt;

    boolean anItems;
  }

  private class FieldValue {
    String aString;

    int anInt;

    List<String> anItems;
  }
}
