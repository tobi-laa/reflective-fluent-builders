package io.github.tobi.laa.reflective.fluent.builders.test.models.nested;

import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class NestedPublicLevelTwoBuilder {
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private NestedPublicLevelTwoBuilder(
      final TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static NestedPublicLevelTwoBuilder newInstance() {
    return new NestedPublicLevelTwoBuilder(null);
  }

  public static NestedPublicLevelTwoBuilder thatModifies(
      final TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new NestedPublicLevelTwoBuilder(objectToModify);
  }

  public NestedPublicLevelTwoBuilder nested(
      final TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree nested) {
    fieldValue.nested = nested;
    callSetterFor.nested = true;
    return this;
  }

  public TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo build() {
    if (objectToBuild == null) {
      objectToBuild = new TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo();
    }
    if (callSetterFor.nested) {
      objectToBuild.setNested(fieldValue.nested);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean nested;
  }

  private class FieldValue {
    TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree nested;
  }
}
