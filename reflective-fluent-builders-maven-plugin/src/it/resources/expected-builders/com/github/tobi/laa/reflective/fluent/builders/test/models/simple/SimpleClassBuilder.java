package com.github.tobi.laa.reflective.fluent.builders.test.models.simple;

import java.lang.Class;
import java.lang.String;
import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "com.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class SimpleClassBuilder {
  private SimpleClass objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private SimpleClassBuilder(final SimpleClass objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static SimpleClassBuilder newInstance() {
    return new SimpleClassBuilder(null);
  }

  public static SimpleClassBuilder thatModifies(final SimpleClass objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new SimpleClassBuilder(objectToModify);
  }

  public SimpleClassBuilder aString(final String aString) {
    fieldValue.aString = aString;
    callSetterFor.aString = true;
    return this;
  }

  public SimpleClassBuilder anInt(final int anInt) {
    fieldValue.anInt = anInt;
    callSetterFor.anInt = true;
    return this;
  }

  public SimpleClassBuilder booleanField(final boolean booleanField) {
    fieldValue.booleanField = booleanField;
    callSetterFor.booleanField = true;
    return this;
  }

  public SimpleClassBuilder setClass(final Class setClass) {
    fieldValue.setClass = setClass;
    callSetterFor.setClass = true;
    return this;
  }

  public SimpleClass build() {
    if (objectToBuild == null) {
      objectToBuild = new SimpleClass();
    }
    if (callSetterFor.aString) {
      objectToBuild.setAString(fieldValue.aString);
    }
    if (callSetterFor.anInt) {
      objectToBuild.setAnInt(fieldValue.anInt);
    }
    if (callSetterFor.booleanField) {
      objectToBuild.setBooleanField(fieldValue.booleanField);
    }
    if (callSetterFor.setClass) {
      objectToBuild.setSetClass(fieldValue.setClass);
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

    Class setClass;
  }
}
