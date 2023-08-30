package io.github.tobi.laa.reflective.fluent.builders.test.models.nested;

import java.lang.SuppressWarnings;
import java.util.Objects;
import javax.annotation.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class NestedPublicLevelThreeBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected NestedPublicLevelThreeBuilder(
      final TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  protected NestedPublicLevelThreeBuilder() {
    // noop
  }

  public static NestedPublicLevelThreeBuilder newInstance() {
    return new NestedPublicLevelThreeBuilder();
  }

  public static NestedPublicLevelThreeBuilder thatModifies(
      final TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new NestedPublicLevelThreeBuilder(objectToModify);
  }

  public NestedPublicLevelThreeBuilder field(final int field) {
    this.fieldValue.field = field;
    this.callSetterFor.field = true;
    return this;
  }

  public TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree build() {
    if (this.objectToBuild == null) {
      this.objectToBuild = new TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree();
    }
    if (this.callSetterFor.field) {
      this.objectToBuild.setField(this.fieldValue.field);
    }
    return this.objectToBuild;
  }

  private class CallSetterFor {
    boolean field;
  }

  private class FieldValue {
    int field;
  }
}
