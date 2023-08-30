package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy;

import java.lang.SuppressWarnings;
import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class FirstSuperClassBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private FirstSuperClass objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected FirstSuperClassBuilder(final FirstSuperClass objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  protected FirstSuperClassBuilder() {
    // noop
  }

  public static FirstSuperClassBuilder newInstance() {
    return new FirstSuperClassBuilder();
  }

  public static FirstSuperClassBuilder thatModifies(final FirstSuperClass objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new FirstSuperClassBuilder(objectToModify);
  }

  public FirstSuperClassBuilder eight(final int eight) {
    this.fieldValue.eight = eight;
    this.callSetterFor.eight = true;
    return this;
  }

  public FirstSuperClassBuilder four(final int four) {
    this.fieldValue.four = four;
    this.callSetterFor.four = true;
    return this;
  }

  public FirstSuperClassBuilder seven(final int seven) {
    this.fieldValue.seven = seven;
    this.callSetterFor.seven = true;
    return this;
  }

  public FirstSuperClassBuilder two(final int two) {
    this.fieldValue.two = two;
    this.callSetterFor.two = true;
    return this;
  }

  public FirstSuperClass build() {
    if (this.objectToBuild == null) {
      this.objectToBuild = new FirstSuperClass();
    }
    if (this.callSetterFor.eight) {
      this.objectToBuild.setEight(this.fieldValue.eight);
    }
    if (this.callSetterFor.four) {
      this.objectToBuild.setFour(this.fieldValue.four);
    }
    if (this.callSetterFor.seven) {
      this.objectToBuild.setSeven(this.fieldValue.seven);
    }
    if (this.callSetterFor.two) {
      this.objectToBuild.setTwo(this.fieldValue.two);
    }
    return this.objectToBuild;
  }

  private class CallSetterFor {
    boolean eight;

    boolean four;

    boolean seven;

    boolean two;
  }

  private class FieldValue {
    int eight;

    int four;

    int seven;

    int two;
  }
}
