package io.github.tobi.laa.reflective.fluent.builders.test.models.nested;

import java.lang.SuppressWarnings;
import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class NestedPublicLevelTwoBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected NestedPublicLevelTwoBuilder(
      final TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  protected NestedPublicLevelTwoBuilder() {
    // noop
  }

  public static NestedPublicLevelTwoBuilder newInstance() {
    return new NestedPublicLevelTwoBuilder();
  }

  public static NestedPublicLevelTwoBuilder thatModifies(
      final TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new NestedPublicLevelTwoBuilder(objectToModify);
  }

  public NestedPublicLevelTwoBuilder nested(
      final TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree nested) {
    this.fieldValue.nested = nested;
    this.callSetterFor.nested = true;
    return this;
  }

  public TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo build() {
    if (this.objectToBuild == null) {
      this.objectToBuild = new TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo();
    }
    if (this.callSetterFor.nested) {
      this.objectToBuild.setNested(this.fieldValue.nested);
    }
    return this.objectToBuild;
  }

  private class CallSetterFor {
    boolean nested;
  }

  private class FieldValue {
    TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree nested;
  }
}
