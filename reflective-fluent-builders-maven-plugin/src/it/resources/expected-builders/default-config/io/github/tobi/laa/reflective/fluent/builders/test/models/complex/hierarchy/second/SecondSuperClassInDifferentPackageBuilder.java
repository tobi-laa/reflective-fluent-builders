package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.second;

import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class SecondSuperClassInDifferentPackageBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private SecondSuperClassInDifferentPackage objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected SecondSuperClassInDifferentPackageBuilder(
      final SecondSuperClassInDifferentPackage objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  protected SecondSuperClassInDifferentPackageBuilder() {
    // noop
  }

  public static SecondSuperClassInDifferentPackageBuilder newInstance() {
    return new SecondSuperClassInDifferentPackageBuilder();
  }

  public static SecondSuperClassInDifferentPackageBuilder thatModifies(
      final SecondSuperClassInDifferentPackage objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new SecondSuperClassInDifferentPackageBuilder(objectToModify);
  }

  public SecondSuperClassInDifferentPackageBuilder eight(final int eight) {
    this.fieldValue.eight = eight;
    this.callSetterFor.eight = true;
    return this;
  }

  public SecondSuperClassInDifferentPackageBuilder five(final int five) {
    this.fieldValue.five = five;
    this.callSetterFor.five = true;
    return this;
  }

  public SecondSuperClassInDifferentPackageBuilder four(final int four) {
    this.fieldValue.four = four;
    this.callSetterFor.four = true;
    return this;
  }

  public SecondSuperClassInDifferentPackage build() {
    if (this.objectToBuild == null) {
      this.objectToBuild = new SecondSuperClassInDifferentPackage();
    }
    if (this.callSetterFor.eight) {
      this.objectToBuild.setEight(this.fieldValue.eight);
    }
    if (this.callSetterFor.five) {
      this.objectToBuild.setFive(this.fieldValue.five);
    }
    if (this.callSetterFor.four) {
      this.objectToBuild.setFour(this.fieldValue.four);
    }
    return this.objectToBuild;
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
