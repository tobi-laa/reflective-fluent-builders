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

  public FirstSuperClassBuilder five(final int five) {
    fieldValue.five = five;
    callSetterFor.five = true;
    return this;
  }

  public FirstSuperClassBuilder three(final int three) {
    fieldValue.three = three;
    callSetterFor.three = true;
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
    if (callSetterFor.five) {
      objectToBuild.setFive(fieldValue.five);
    }
    if (callSetterFor.three) {
      objectToBuild.setThree(fieldValue.three);
    }
    if (callSetterFor.two) {
      objectToBuild.setTwo(fieldValue.two);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean five;

    boolean three;

    boolean two;
  }

  private class FieldValue {
    int five;

    int three;

    int two;
  }
}
