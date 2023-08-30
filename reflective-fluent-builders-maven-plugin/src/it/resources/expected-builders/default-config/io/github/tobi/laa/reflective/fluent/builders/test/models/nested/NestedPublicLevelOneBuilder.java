package io.github.tobi.laa.reflective.fluent.builders.test.models.nested;

import java.lang.SuppressWarnings;
import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class NestedPublicLevelOneBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private TopLevelClass.NestedPublicLevelOne objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected NestedPublicLevelOneBuilder(final TopLevelClass.NestedPublicLevelOne objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  protected NestedPublicLevelOneBuilder() {
    // noop
  }

  public static NestedPublicLevelOneBuilder newInstance() {
    return new NestedPublicLevelOneBuilder();
  }

  public static NestedPublicLevelOneBuilder thatModifies(
      final TopLevelClass.NestedPublicLevelOne objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new NestedPublicLevelOneBuilder(objectToModify);
  }

  public NestedPublicLevelOneBuilder nested(
      final TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo nested) {
    this.fieldValue.nested = nested;
    this.callSetterFor.nested = true;
    return this;
  }

  public TopLevelClass.NestedPublicLevelOne build() {
    if (this.objectToBuild == null) {
      this.objectToBuild = new TopLevelClass.NestedPublicLevelOne();
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
    TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo nested;
  }
}
