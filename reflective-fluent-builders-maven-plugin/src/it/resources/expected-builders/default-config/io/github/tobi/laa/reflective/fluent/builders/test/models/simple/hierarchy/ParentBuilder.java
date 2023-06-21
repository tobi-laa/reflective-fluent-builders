package io.github.tobi.laa.reflective.fluent.builders.test.models.simple.hierarchy;

import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ParentBuilder {
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private Parent objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private ParentBuilder(final Parent objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static ParentBuilder newInstance() {
    return new ParentBuilder(null);
  }

  public static ParentBuilder thatModifies(final Parent objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new ParentBuilder(objectToModify);
  }

  public ParentBuilder parentField(final int parentField) {
    fieldValue.parentField = parentField;
    callSetterFor.parentField = true;
    return this;
  }

  public Parent build() {
    if (objectToBuild == null) {
      objectToBuild = new Parent();
    }
    if (callSetterFor.parentField) {
      objectToBuild.setParentField(fieldValue.parentField);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean parentField;
  }

  private class FieldValue {
    int parentField;
  }
}
