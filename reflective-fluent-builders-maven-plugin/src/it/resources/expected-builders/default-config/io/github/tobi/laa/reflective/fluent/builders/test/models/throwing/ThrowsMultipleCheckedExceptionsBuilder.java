package io.github.tobi.laa.reflective.fluent.builders.test.models.throwing;

import java.io.IOException;
import java.lang.IllegalAccessException;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.text.ParseException;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

/**
 * Builder for {@link ThrowsMultipleCheckedExceptions}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ThrowsMultipleCheckedExceptionsBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("all")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<ThrowsMultipleCheckedExceptions> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link ThrowsMultipleCheckedExceptions} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected ThrowsMultipleCheckedExceptionsBuilder(
      final Supplier<ThrowsMultipleCheckedExceptions> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link ThrowsMultipleCheckedExceptionsBuilder} that will work on a new instance of {@link ThrowsMultipleCheckedExceptions} once {@link #build()} is called.
   */
  public static ThrowsMultipleCheckedExceptionsBuilder newInstance() {
    return new ThrowsMultipleCheckedExceptionsBuilder(ThrowsMultipleCheckedExceptions::new);
  }

  /**
   * Creates an instance of {@link ThrowsMultipleCheckedExceptionsBuilder} that will work on an instance of {@link ThrowsMultipleCheckedExceptions} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static ThrowsMultipleCheckedExceptionsBuilder withSupplier(
      final Supplier<ThrowsMultipleCheckedExceptions> supplier) {
    return new ThrowsMultipleCheckedExceptionsBuilder(supplier);
  }

  /**
   * Sets the value for the {@code aLong} property.
   * To be more precise, this will lead to {@link ThrowsMultipleCheckedExceptions#setALong(long)} being called on construction of the object.
   * @param aLong the value to set.
   * @return This builder for chained calls.
   */
  public ThrowsMultipleCheckedExceptionsBuilder aLong(final long aLong) {
    this.fieldValue.aLong = aLong;
    this.callSetterFor.aLong = true;
    return this;
  }

  /**
   * Sets the value for the {@code aString} property.
   * To be more precise, this will lead to {@link ThrowsMultipleCheckedExceptions#setAString(String)} being called on construction of the object.
   * @param aString the value to set.
   * @return This builder for chained calls.
   */
  public ThrowsMultipleCheckedExceptionsBuilder aString(final String aString) {
    this.fieldValue.aString = aString;
    this.callSetterFor.aString = true;
    return this;
  }

  /**
   * Sets the value for the {@code anInt} property.
   * To be more precise, this will lead to {@link ThrowsMultipleCheckedExceptions#setAnInt(int)} being called on construction of the object.
   * @param anInt the value to set.
   * @return This builder for chained calls.
   */
  public ThrowsMultipleCheckedExceptionsBuilder anInt(final int anInt) {
    this.fieldValue.anInt = anInt;
    this.callSetterFor.anInt = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link ThrowsMultipleCheckedExceptions}.
   * @return The constructed instance. Never {@code null}.
   * @throws IOException If thrown by an accessor of ThrowsMultipleCheckedExceptions, i.e. a setter, getter or adder.
   * @throws IllegalAccessException If thrown by an accessor of ThrowsMultipleCheckedExceptions, i.e. a setter, getter or adder.
   * @throws ParseException If thrown by an accessor of ThrowsMultipleCheckedExceptions, i.e. a setter, getter or adder.
   */
  public ThrowsMultipleCheckedExceptions build() throws IOException, IllegalAccessException,
      ParseException {
    final ThrowsMultipleCheckedExceptions objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.aLong) {
      objectToBuild.setALong(this.fieldValue.aLong);
    }
    if (this.callSetterFor.aString) {
      objectToBuild.setAString(this.fieldValue.aString);
    }
    if (this.callSetterFor.anInt) {
      objectToBuild.setAnInt(this.fieldValue.anInt);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean aLong;

    boolean aString;

    boolean anInt;
  }

  private class FieldValue {
    long aLong;

    String aString;

    int anInt;
  }
}
