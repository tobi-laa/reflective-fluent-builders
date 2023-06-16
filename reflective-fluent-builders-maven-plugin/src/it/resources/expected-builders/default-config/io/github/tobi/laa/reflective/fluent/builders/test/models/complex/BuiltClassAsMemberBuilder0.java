package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class BuiltClassAsMemberBuilder0 {
  private BuiltClassAsMemberBuilder.BuiltClassAsMember objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private BuiltClassAsMemberBuilder0(
      final BuiltClassAsMemberBuilder.BuiltClassAsMember objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static BuiltClassAsMemberBuilder0 newInstance() {
    return new BuiltClassAsMemberBuilder0(null);
  }

  public static BuiltClassAsMemberBuilder0 thatModifies(
      final BuiltClassAsMemberBuilder.BuiltClassAsMember objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new BuiltClassAsMemberBuilder0(objectToModify);
  }

  public BuiltClassAsMemberBuilder0 aField(final int aField) {
    fieldValue.aField = aField;
    callSetterFor.aField = true;
    return this;
  }

  public BuiltClassAsMemberBuilder.BuiltClassAsMember build() {
    if (objectToBuild == null) {
      objectToBuild = new BuiltClassAsMemberBuilder.BuiltClassAsMember();
    }
    if (callSetterFor.aField) {
      objectToBuild.setAField(fieldValue.aField);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean aField;
  }

  private class FieldValue {
    int aField;
  }
}
