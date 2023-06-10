package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy;

import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
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

  public ClassWithHierarchyBuilder one(final int one) {
    fieldValue.one = one;
    callSetterFor.one = true;
    return this;
  }

  public ClassWithHierarchyBuilder three(final int three) {
    fieldValue.three = three;
    callSetterFor.three = true;
    return this;
  }

  public ClassWithHierarchy build() {
    if (objectToBuild == null) {
      objectToBuild = new ClassWithHierarchy();
    }
    if (callSetterFor.one) {
      objectToBuild.setOne(fieldValue.one);
    }
    if (callSetterFor.three) {
      objectToBuild.setThree(fieldValue.three);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean one;

    boolean three;
  }

  private class FieldValue {
    int one;

    int three;
  }
}
