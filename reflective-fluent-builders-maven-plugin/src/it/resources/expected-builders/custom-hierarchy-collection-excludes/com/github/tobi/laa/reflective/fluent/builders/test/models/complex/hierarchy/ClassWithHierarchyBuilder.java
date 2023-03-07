package com.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy;

import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "com.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ClassWithHierarchyBuilder {
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

  public ClassWithHierarchy build() {
    if (objectToBuild == null) {
      objectToBuild = new ClassWithHierarchy();
    }
    if (callSetterFor.four) {
      objectToBuild.setFour(fieldValue.four);
    }
    if (callSetterFor.one) {
      objectToBuild.setOne(fieldValue.one);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean four;

    boolean one;
  }

  private class FieldValue {
    int four;

    int one;
  }
}
