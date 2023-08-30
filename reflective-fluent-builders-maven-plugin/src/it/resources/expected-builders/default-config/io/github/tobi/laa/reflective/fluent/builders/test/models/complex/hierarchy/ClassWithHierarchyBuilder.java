package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy;

import java.lang.SuppressWarnings;
import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ClassWithHierarchyBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private ClassWithHierarchy objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected ClassWithHierarchyBuilder(final ClassWithHierarchy objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  protected ClassWithHierarchyBuilder() {
    // noop
  }

  public static ClassWithHierarchyBuilder newInstance() {
    return new ClassWithHierarchyBuilder();
  }

  public static ClassWithHierarchyBuilder thatModifies(final ClassWithHierarchy objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new ClassWithHierarchyBuilder(objectToModify);
  }

  public ClassWithHierarchyBuilder eight(final int eight) {
    this.fieldValue.eight = eight;
    this.callSetterFor.eight = true;
    return this;
  }

  public ClassWithHierarchyBuilder four(final int four) {
    this.fieldValue.four = four;
    this.callSetterFor.four = true;
    return this;
  }

  public ClassWithHierarchyBuilder one(final int one) {
    this.fieldValue.one = one;
    this.callSetterFor.one = true;
    return this;
  }

  public ClassWithHierarchyBuilder seven(final int seven) {
    this.fieldValue.seven = seven;
    this.callSetterFor.seven = true;
    return this;
  }

  public ClassWithHierarchyBuilder three(final int three) {
    this.fieldValue.three = three;
    this.callSetterFor.three = true;
    return this;
  }

  public ClassWithHierarchyBuilder two(final int two) {
    this.fieldValue.two = two;
    this.callSetterFor.two = true;
    return this;
  }

  public ClassWithHierarchy build() {
    if (this.objectToBuild == null) {
      this.objectToBuild = new ClassWithHierarchy();
    }
    if (this.callSetterFor.eight) {
      this.objectToBuild.setEight(this.fieldValue.eight);
    }
    if (this.callSetterFor.four) {
      this.objectToBuild.setFour(this.fieldValue.four);
    }
    if (this.callSetterFor.one) {
      this.objectToBuild.setOne(this.fieldValue.one);
    }
    if (this.callSetterFor.seven) {
      this.objectToBuild.setSeven(this.fieldValue.seven);
    }
    if (this.callSetterFor.three) {
      this.objectToBuild.setThree(this.fieldValue.three);
    }
    if (this.callSetterFor.two) {
      this.objectToBuild.setTwo(this.fieldValue.two);
    }
    return this.objectToBuild;
  }

  private class CallSetterFor {
    boolean eight;

    boolean four;

    boolean one;

    boolean seven;

    boolean three;

    boolean two;
  }

  private class FieldValue {
    int eight;

    int four;

    int one;

    int seven;

    int three;

    int two;
  }
}
