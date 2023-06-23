package io.github.tobi.laa.reflective.fluent.builders.test.models.nested;

import java.util.Objects;
import javax.annotation.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class NestedPublicLevelOneBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private TopLevelClass.NestedPublicLevelOne objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private NestedPublicLevelOneBuilder(final TopLevelClass.NestedPublicLevelOne objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static NestedPublicLevelOneBuilder newInstance() {
    return new NestedPublicLevelOneBuilder(null);
  }

  public static NestedPublicLevelOneBuilder thatModifies(
      final TopLevelClass.NestedPublicLevelOne objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new NestedPublicLevelOneBuilder(objectToModify);
  }

  public NestedPublicLevelOneBuilder nested(
      final TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo nested) {
    fieldValue.nested = nested;
    callSetterFor.nested = true;
    return this;
  }

  public TopLevelClass.NestedPublicLevelOne build() {
    if (objectToBuild == null) {
      objectToBuild = new TopLevelClass.NestedPublicLevelOne();
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
    TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo nested;
  }
}
