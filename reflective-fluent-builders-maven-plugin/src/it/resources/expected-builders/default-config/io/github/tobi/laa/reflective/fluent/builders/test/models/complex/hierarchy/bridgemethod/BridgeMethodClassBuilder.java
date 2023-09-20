package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.bridgemethod;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class BridgeMethodClassBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private BridgeMethodClass objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected BridgeMethodClassBuilder(final BridgeMethodClass objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  protected BridgeMethodClassBuilder() {
    // noop
  }

  public static BridgeMethodClassBuilder newInstance() {
    return new BridgeMethodClassBuilder();
  }

  public static BridgeMethodClassBuilder thatModifies(final BridgeMethodClass objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new BridgeMethodClassBuilder(objectToModify);
  }

  public BridgeMethodClassBuilder something(final String something) {
    this.fieldValue.something = something;
    this.callSetterFor.something = true;
    return this;
  }

  public BridgeMethodClass build() {
    if (this.objectToBuild == null) {
      this.objectToBuild = new BridgeMethodClass();
    }
    if (this.callSetterFor.something) {
      this.objectToBuild.setSomething(this.fieldValue.something);
    }
    return this.objectToBuild;
  }

  private class CallSetterFor {
    boolean something;
  }

  private class FieldValue {
    String something;
  }
}
