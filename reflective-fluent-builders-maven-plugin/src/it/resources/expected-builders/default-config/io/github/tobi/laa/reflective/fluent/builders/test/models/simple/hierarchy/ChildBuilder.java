package io.github.tobi.laa.reflective.fluent.builders.test.models.simple.hierarchy;

import java.lang.String;
import java.util.Objects;
import javax.annotation.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ChildBuilder {
  private Child objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private ChildBuilder(final Child objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static ChildBuilder newInstance() {
    return new ChildBuilder(null);
  }

  public static ChildBuilder thatModifies(final Child objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new ChildBuilder(objectToModify);
  }

  public ChildBuilder childField(final String childField) {
    fieldValue.childField = childField;
    callSetterFor.childField = true;
    return this;
  }

  public ChildBuilder parentField(final int parentField) {
    fieldValue.parentField = parentField;
    callSetterFor.parentField = true;
    return this;
  }

  public Child build() {
    if (objectToBuild == null) {
      objectToBuild = new Child();
    }
    if (callSetterFor.childField) {
      objectToBuild.setChildField(fieldValue.childField);
    }
    if (callSetterFor.parentField) {
      objectToBuild.setParentField(fieldValue.parentField);
    }
    return objectToBuild;
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
