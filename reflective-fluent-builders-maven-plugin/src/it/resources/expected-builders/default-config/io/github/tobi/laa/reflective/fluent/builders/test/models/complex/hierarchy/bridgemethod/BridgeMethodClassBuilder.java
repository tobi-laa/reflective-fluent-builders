package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.bridgemethod;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
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

  private final Supplier<BridgeMethodClass> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected BridgeMethodClassBuilder(final Supplier<BridgeMethodClass> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static BridgeMethodClassBuilder newInstance() {
    return new BridgeMethodClassBuilder(BridgeMethodClass::new);
  }

  public static BridgeMethodClassBuilder withSupplier(final Supplier<BridgeMethodClass> supplier) {
    return new BridgeMethodClassBuilder(supplier);
  }

  public BridgeMethodClassBuilder something(final String something) {
    this.fieldValue.something = something;
    this.callSetterFor.something = true;
    return this;
  }

  public BridgeMethodClass build() {
    final BridgeMethodClass objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.something) {
      objectToBuild.setSomething(this.fieldValue.something);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean something;
  }

  private class FieldValue {
    String something;
  }
}
