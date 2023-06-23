package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy;

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
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private ClassWithHierarchy objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private ClassWithHierarchyBuilder(final ClassWithHierarchy objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static ClassWithHierarchyBuilder newInstance() {
    return new ClassWithHierarchyBuilder(null);
  }

  public static ClassWithHierarchyBuilder thatModifies(final ClassWithHierarchy objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new ClassWithHierarchyBuilder(objectToModify);
  }

  public ClassWithHierarchyBuilder eight(final int eight) {
    fieldValue.eight = eight;
    callSetterFor.eight = true;
    return this;
  }

  public ClassWithHierarchyBuilder four(final int four) {
    fieldValue.four = four;
    callSetterFor.four = true;
    return this;
  }

  public ClassWithHierarchyBuilder one(final int one) {
    fieldValue.one = one;
    callSetterFor.one = true;
    return this;
  }

  public ClassWithHierarchyBuilder seven(final int seven) {
    fieldValue.seven = seven;
    callSetterFor.seven = true;
    return this;
  }

  public ClassWithHierarchyBuilder three(final int three) {
    fieldValue.three = three;
    callSetterFor.three = true;
    return this;
  }

  public ClassWithHierarchyBuilder two(final int two) {
    fieldValue.two = two;
    callSetterFor.two = true;
    return this;
  }

  public ClassWithHierarchy build() {
    if (objectToBuild == null) {
      objectToBuild = new ClassWithHierarchy();
    }
    if (callSetterFor.eight) {
      objectToBuild.setEight(fieldValue.eight);
    }
    if (callSetterFor.four) {
      objectToBuild.setFour(fieldValue.four);
    }
    if (callSetterFor.one) {
      objectToBuild.setOne(fieldValue.one);
    }
    if (callSetterFor.seven) {
      objectToBuild.setSeven(fieldValue.seven);
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
