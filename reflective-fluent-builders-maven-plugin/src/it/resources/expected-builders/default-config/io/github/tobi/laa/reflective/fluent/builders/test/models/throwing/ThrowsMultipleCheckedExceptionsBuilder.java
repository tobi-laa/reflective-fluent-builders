package io.github.tobi.laa.reflective.fluent.builders.test.models.throwing;

import java.io.IOException;
import java.lang.IllegalAccessException;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.text.ParseException;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ThrowsMultipleCheckedExceptionsBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<ThrowsMultipleCheckedExceptions> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected ThrowsMultipleCheckedExceptionsBuilder(
      final Supplier<ThrowsMultipleCheckedExceptions> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static ThrowsMultipleCheckedExceptionsBuilder newInstance() {
    return new ThrowsMultipleCheckedExceptionsBuilder(ThrowsMultipleCheckedExceptions::new);
  }

  public static ThrowsMultipleCheckedExceptionsBuilder withSupplier(
      final Supplier<ThrowsMultipleCheckedExceptions> supplier) {
    return new ThrowsMultipleCheckedExceptionsBuilder(supplier);
  }

  public ThrowsMultipleCheckedExceptionsBuilder aLong(final long aLong) {
    this.fieldValue.aLong = aLong;
    this.callSetterFor.aLong = true;
    return this;
  }

  public ThrowsMultipleCheckedExceptionsBuilder aString(final String aString) {
    this.fieldValue.aString = aString;
    this.callSetterFor.aString = true;
    return this;
  }

  public ThrowsMultipleCheckedExceptionsBuilder anInt(final int anInt) {
    this.fieldValue.anInt = anInt;
    this.callSetterFor.anInt = true;
    return this;
  }

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
