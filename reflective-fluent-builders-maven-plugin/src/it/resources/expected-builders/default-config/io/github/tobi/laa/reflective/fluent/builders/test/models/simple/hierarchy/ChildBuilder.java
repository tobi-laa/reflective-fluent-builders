package io.github.tobi.laa.reflective.fluent.builders.test.models.simple.hierarchy;

import java.lang.String;
import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ChildBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private Child objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected ChildBuilder(final Child objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  protected ChildBuilder() {
    // noop
  }

  public static ChildBuilder newInstance() {
    return new ChildBuilder();
  }

  public static ChildBuilder thatModifies(final Child objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new ChildBuilder(objectToModify);
  }

  public ChildBuilder childField(final String childField) {
    this.fieldValue.childField = childField;
    this.callSetterFor.childField = true;
    return this;
  }

  public ChildBuilder parentField(final int parentField) {
    this.fieldValue.parentField = parentField;
    this.callSetterFor.parentField = true;
    return this;
  }

  public Child build() {
    if (this.objectToBuild == null) {
      this.objectToBuild = new Child();
    }
    if (this.callSetterFor.childField) {
      this.objectToBuild.setChildField(this.fieldValue.childField);
    }
    if (this.callSetterFor.parentField) {
      this.objectToBuild.setParentField(this.fieldValue.parentField);
    }
    return this.objectToBuild;
  }

  private class CallSetterFor {
    boolean childField;

    boolean parentField;
  }

  private class FieldValue {
    String childField;

    int parentField;
  }
}
