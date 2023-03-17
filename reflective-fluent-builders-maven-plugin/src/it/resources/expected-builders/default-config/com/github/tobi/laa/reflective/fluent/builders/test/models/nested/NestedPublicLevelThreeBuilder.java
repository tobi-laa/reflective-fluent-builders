package com.github.tobi.laa.reflective.fluent.builders.test.models.nested;

import java.util.Objects;
import javax.annotation.Generated;

@Generated(
    value = "com.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class NestedPublicLevelThreeBuilder {
  private TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private NestedPublicLevelThreeBuilder(
      final TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static NestedPublicLevelThreeBuilder newInstance() {
    return new NestedPublicLevelThreeBuilder(null);
  }

  public static NestedPublicLevelThreeBuilder thatModifies(
      final TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new NestedPublicLevelThreeBuilder(objectToModify);
  }

  public NestedPublicLevelThreeBuilder field(final int field) {
    fieldValue.field = field;
    callSetterFor.field = true;
    return this;
  }

  public TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree build() {
    if (objectToBuild == null) {
      objectToBuild = new TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree();
    }
    if (callSetterFor.field) {
      objectToBuild.setField(fieldValue.field);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean field;
  }

  private class FieldValue {
    int field;
  }
}
