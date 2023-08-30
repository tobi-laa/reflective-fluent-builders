package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class BuiltClassAsMemberBuilder0 {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private BuiltClassAsMemberBuilder.BuiltClassAsMember objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected BuiltClassAsMemberBuilder0(
      final BuiltClassAsMemberBuilder.BuiltClassAsMember objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  protected BuiltClassAsMemberBuilder0() {
    // noop
  }

  public static BuiltClassAsMemberBuilder0 newInstance() {
    return new BuiltClassAsMemberBuilder0();
  }

  public static BuiltClassAsMemberBuilder0 thatModifies(
      final BuiltClassAsMemberBuilder.BuiltClassAsMember objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new BuiltClassAsMemberBuilder0(objectToModify);
  }

  public BuiltClassAsMemberBuilder0 aField(final int aField) {
    this.fieldValue.aField = aField;
    this.callSetterFor.aField = true;
    return this;
  }

  public BuiltClassAsMemberBuilder.BuiltClassAsMember build() {
    if (this.objectToBuild == null) {
      this.objectToBuild = new BuiltClassAsMemberBuilder.BuiltClassAsMember();
    }
    if (this.callSetterFor.aField) {
      this.objectToBuild.setAField(this.fieldValue.aField);
    }
    return this.objectToBuild;
  }

  private class CallSetterFor {
    boolean aField;
  }

  private class FieldValue {
    int aField;
  }
}
