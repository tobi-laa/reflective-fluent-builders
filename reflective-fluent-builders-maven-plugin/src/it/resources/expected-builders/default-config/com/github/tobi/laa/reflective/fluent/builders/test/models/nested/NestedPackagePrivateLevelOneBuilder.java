package com.github.tobi.laa.reflective.fluent.builders.test.models.nested;

import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "com.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class NestedPackagePrivateLevelOneBuilder {
  private TopLevelClass.NestedPackagePrivateLevelOne objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private NestedPackagePrivateLevelOneBuilder(
      final TopLevelClass.NestedPackagePrivateLevelOne objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static NestedPackagePrivateLevelOneBuilder newInstance() {
    return new NestedPackagePrivateLevelOneBuilder(null);
  }

  public static NestedPackagePrivateLevelOneBuilder thatModifies(
      final TopLevelClass.NestedPackagePrivateLevelOne objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new NestedPackagePrivateLevelOneBuilder(objectToModify);
  }

  public NestedPackagePrivateLevelOneBuilder field(final int field) {
    fieldValue.field = field;
    callSetterFor.field = true;
    return this;
  }

  public TopLevelClass.NestedPackagePrivateLevelOne build() {
    if (objectToBuild == null) {
      objectToBuild = new TopLevelClass.NestedPackagePrivateLevelOne();
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
