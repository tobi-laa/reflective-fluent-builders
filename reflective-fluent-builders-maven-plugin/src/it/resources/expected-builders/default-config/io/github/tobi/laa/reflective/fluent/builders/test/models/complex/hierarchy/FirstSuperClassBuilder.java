package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy;

import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class FirstSuperClassBuilder {
  private FirstSuperClass objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private FirstSuperClassBuilder(final FirstSuperClass objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static FirstSuperClassBuilder newInstance() {
    return new FirstSuperClassBuilder(null);
  }

  public static FirstSuperClassBuilder thatModifies(final FirstSuperClass objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new FirstSuperClassBuilder(objectToModify);
  }

  public FirstSuperClassBuilder eight(final int eight) {
    fieldValue.eight = eight;
    callSetterFor.eight = true;
    return this;
  }

  public FirstSuperClassBuilder four(final int four) {
    fieldValue.four = four;
    callSetterFor.four = true;
    return this;
  }

  public FirstSuperClassBuilder seven(final int seven) {
    fieldValue.seven = seven;
    callSetterFor.seven = true;
    return this;
  }

  public FirstSuperClassBuilder six(final int six) {
    fieldValue.six = six;
    callSetterFor.six = true;
    return this;
  }

  public FirstSuperClassBuilder two(final int two) {
    fieldValue.two = two;
    callSetterFor.two = true;
    return this;
  }

  public FirstSuperClass build() {
    if (objectToBuild == null) {
      objectToBuild = new FirstSuperClass();
    }
    if (callSetterFor.eight) {
      objectToBuild.setEight(fieldValue.eight);
    }
    if (callSetterFor.four) {
      objectToBuild.setFour(fieldValue.four);
    }
    if (callSetterFor.seven) {
      objectToBuild.setSeven(fieldValue.seven);
    }
    if (callSetterFor.six) {
      objectToBuild.setSix(fieldValue.six);
    }
    if (callSetterFor.two) {
      objectToBuild.setTwo(fieldValue.two);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean eight;

    boolean four;

    boolean seven;

    boolean six;

    boolean two;
  }

  private class FieldValue {
    int eight;

    int four;

    int seven;

    int six;

    int two;
  }
}
