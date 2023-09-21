package io.github.tobi.laa.reflective.fluent.builders.test.models.simple;

import java.lang.Class;
import java.lang.Object;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class SimpleClassBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private Supplier<SimpleClass> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected SimpleClassBuilder(final Supplier<SimpleClass> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static SimpleClassBuilder newInstance() {
    return new SimpleClassBuilder(SimpleClass::new);
  }

  public static SimpleClassBuilder withSupplier(final Supplier<SimpleClass> supplier) {
    return new SimpleClassBuilder(supplier);
  }

  public SimpleClassBuilder aString(final String aString) {
    this.fieldValue.aString = aString;
    this.callSetterFor.aString = true;
    return this;
  }

  public SimpleClassBuilder anInt(final int anInt) {
    this.fieldValue.anInt = anInt;
    this.callSetterFor.anInt = true;
    return this;
  }

  public SimpleClassBuilder booleanField(final boolean booleanField) {
    this.fieldValue.booleanField = booleanField;
    this.callSetterFor.booleanField = true;
    return this;
  }

  public SimpleClassBuilder setClass(final Class<Object> setClass) {
    this.fieldValue.setClass = setClass;
    this.callSetterFor.setClass = true;
    return this;
  }

  public SimpleClass build() {
    final SimpleClass objectToBuild = objectSupplier.get();
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
