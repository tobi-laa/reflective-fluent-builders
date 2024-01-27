package io.github.tobi.laa.reflective.fluent.builders.test.models.throwing;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

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

  protected ThrowsRuntimeExceptionBuilder(final Supplier<ThrowsRuntimeException> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static ThrowsRuntimeExceptionBuilder newInstance() {
    return new ThrowsRuntimeExceptionBuilder(ThrowsRuntimeException::new);
  }

  public static ThrowsRuntimeExceptionBuilder withSupplier(
      final Supplier<ThrowsRuntimeException> supplier) {
    return new ThrowsRuntimeExceptionBuilder(supplier);
  }

  public ThrowsRuntimeExceptionBuilder aString(final String aString) {
    this.fieldValue.aString = aString;
    this.callSetterFor.aString = true;
    return this;
  }

  public ThrowsRuntimeExceptionBuilder anInt(final int anInt) {
    this.fieldValue.anInt = anInt;
    this.callSetterFor.anInt = true;
    return this;
  }

  public ThrowsRuntimeExceptionBuilder anItem(final String anItem) {
    if (this.fieldValue.anItems == null) {
      this.fieldValue.anItems = new ArrayList<>();
    }
    this.fieldValue.anItems.add(anItem);
    this.callSetterFor.anItems = true;
    return this;
  }

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
