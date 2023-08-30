package io.github.tobi.laa.reflective.fluent.builders.test.models.simple.hierarchy;

import java.lang.SuppressWarnings;
import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ParentBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private Parent objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected ParentBuilder(final Parent objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  protected ParentBuilder() {
    // noop
  }

  public static ParentBuilder newInstance() {
    return new ParentBuilder();
  }

  public static ParentBuilder thatModifies(final Parent objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new ParentBuilder(objectToModify);
  }

  public ParentBuilder parentField(final int parentField) {
    this.fieldValue.parentField = parentField;
    this.callSetterFor.parentField = true;
    return this;
  }

  public Parent build() {
    if (this.objectToBuild == null) {
      this.objectToBuild = new Parent();
    }
    if (this.callSetterFor.parentField) {
      this.objectToBuild.setParentField(this.fieldValue.parentField);
    }
    return this.objectToBuild;
  }

  private class CallSetterFor {
    boolean parentField;
  }

  private class FieldValue {
    int parentField;
  }
}
