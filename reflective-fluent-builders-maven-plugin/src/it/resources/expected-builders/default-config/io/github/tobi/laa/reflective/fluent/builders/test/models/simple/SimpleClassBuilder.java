package io.github.tobi.laa.reflective.fluent.builders.test.models.simple;

import java.lang.Class;
import java.lang.Object;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

/**
 * Builder for {@link SimpleClass}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class SimpleClassBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("all")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<SimpleClass> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link SimpleClass} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected SimpleClassBuilder(final Supplier<SimpleClass> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link SimpleClassBuilder} that will work on a new instance of {@link SimpleClass} once {@link #build()} is called.
   */
  public static SimpleClassBuilder newInstance() {
    return new SimpleClassBuilder(SimpleClass::new);
  }

  /**
   * Creates an instance of {@link SimpleClassBuilder} that will work on an instance of {@link SimpleClass} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static SimpleClassBuilder withSupplier(final Supplier<SimpleClass> supplier) {
    return new SimpleClassBuilder(supplier);
  }

  /**
   * Sets the value for the {@code aString} property.
   * To be more precise, this will lead to {@link SimpleClass#setAString(String)} being called on construction of the object.
   * @param aString the value to set.
   * @return This builder for chained calls.
   */
  public SimpleClassBuilder aString(final String aString) {
    this.fieldValue.aString = aString;
    this.callSetterFor.aString = true;
    return this;
  }

  /**
   * Sets the value for the {@code anInt} property.
   * To be more precise, this will lead to {@link SimpleClass#setAnInt(int)} being called on construction of the object.
   * @param anInt the value to set.
   * @return This builder for chained calls.
   */
  public SimpleClassBuilder anInt(final int anInt) {
    this.fieldValue.anInt = anInt;
    this.callSetterFor.anInt = true;
    return this;
  }

  /**
   * Sets the value for the {@code booleanField} property.
   * To be more precise, this will lead to {@link SimpleClass#setBooleanField(boolean)} being called on construction of the object.
   * @param booleanField the value to set.
   * @return This builder for chained calls.
   */
  public SimpleClassBuilder booleanField(final boolean booleanField) {
    this.fieldValue.booleanField = booleanField;
    this.callSetterFor.booleanField = true;
    return this;
  }

  /**
   * Sets the value for the {@code setClass} property.
   * To be more precise, this will lead to {@link SimpleClass#setSetClass(Class<?>)} being called on construction of the object.
   * @param setClass the value to set.
   * @return This builder for chained calls.
   */
  public SimpleClassBuilder setClass(final Class<Object> setClass) {
    this.fieldValue.setClass = setClass;
    this.callSetterFor.setClass = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link SimpleClass}.
   * @return The constructed instance. Never {@code null}.
   */
  public SimpleClass build() {
    final SimpleClass objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.aString) {
      objectToBuild.setAString(this.fieldValue.aString);
    }
    if (this.callSetterFor.anInt) {
      objectToBuild.setAnInt(this.fieldValue.anInt);
    }
    if (this.callSetterFor.booleanField) {
      objectToBuild.setBooleanField(this.fieldValue.booleanField);
    }
    if (this.callSetterFor.setClass) {
      objectToBuild.setSetClass(this.fieldValue.setClass);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean aString;

    boolean anInt;

    boolean booleanField;

    boolean setClass;
  }

  private class FieldValue {
    String aString;

    int anInt;

    boolean booleanField;

    Class<Object> setClass;
  }
}
