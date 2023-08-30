package io.github.tobi.laa.reflective.fluent.builders.test.models.visibility;

import java.lang.SuppressWarnings;
import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ProtectedConstructorBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private ProtectedConstructor objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected ProtectedConstructorBuilder(final ProtectedConstructor objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  protected ProtectedConstructorBuilder() {
    // noop
  }

  public static ProtectedConstructorBuilder newInstance() {
    return new ProtectedConstructorBuilder();
  }

  public static ProtectedConstructorBuilder thatModifies(
      final ProtectedConstructor objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new ProtectedConstructorBuilder(objectToModify);
  }

  public ProtectedConstructorBuilder intField(final int intField) {
    this.fieldValue.intField = intField;
    this.callSetterFor.intField = true;
    return this;
  }

  public ProtectedConstructor build() {
    if (this.objectToBuild == null) {
      this.objectToBuild = new ProtectedConstructor();
    }
    if (this.callSetterFor.intField) {
      this.objectToBuild.setIntField(this.fieldValue.intField);
    }
    return this.objectToBuild;
  }

  private class CallSetterFor {
    boolean intField;
  }

  private class FieldValue {
    int intField;
  }
}
