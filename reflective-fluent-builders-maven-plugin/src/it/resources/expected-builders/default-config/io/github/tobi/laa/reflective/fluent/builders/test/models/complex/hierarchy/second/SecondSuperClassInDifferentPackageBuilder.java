package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.second;

import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class SecondSuperClassInDifferentPackageBuilder {
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private SecondSuperClassInDifferentPackage objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private SecondSuperClassInDifferentPackageBuilder(
      final SecondSuperClassInDifferentPackage objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static SecondSuperClassInDifferentPackageBuilder newInstance() {
    return new SecondSuperClassInDifferentPackageBuilder(null);
  }

  public static SecondSuperClassInDifferentPackageBuilder thatModifies(
      final SecondSuperClassInDifferentPackage objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new SecondSuperClassInDifferentPackageBuilder(objectToModify);
  }

  public SecondSuperClassInDifferentPackageBuilder eight(final int eight) {
    fieldValue.eight = eight;
    callSetterFor.eight = true;
    return this;
  }

  public SecondSuperClassInDifferentPackageBuilder five(final int five) {
    fieldValue.five = five;
    callSetterFor.five = true;
    return this;
  }

  public SecondSuperClassInDifferentPackageBuilder four(final int four) {
    fieldValue.four = four;
    callSetterFor.four = true;
    return this;
  }

  public SecondSuperClassInDifferentPackage build() {
    if (objectToBuild == null) {
      objectToBuild = new SecondSuperClassInDifferentPackage();
    }
    if (callSetterFor.eight) {
      objectToBuild.setEight(fieldValue.eight);
    }
    if (callSetterFor.five) {
      objectToBuild.setFive(fieldValue.five);
    }
    if (callSetterFor.four) {
      objectToBuild.setFour(fieldValue.four);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean eight;

    boolean five;

    boolean four;
  }

  private class FieldValue {
    int eight;

    int five;

    int four;
  }
}
