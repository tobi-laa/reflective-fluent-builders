package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.bridgemethod;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

/**
 * Builder for {@link BridgeMethodClass}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class BridgeMethodClassBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("all")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<BridgeMethodClass> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link BridgeMethodClass} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected BridgeMethodClassBuilder(final Supplier<BridgeMethodClass> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link BridgeMethodClassBuilder} that will work on a new instance of {@link BridgeMethodClass} once {@link #build()} is called.
   */
  public static BridgeMethodClassBuilder newInstance() {
    return new BridgeMethodClassBuilder(BridgeMethodClass::new);
  }

  /**
   * Creates an instance of {@link BridgeMethodClassBuilder} that will work on an instance of {@link BridgeMethodClass} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static BridgeMethodClassBuilder withSupplier(final Supplier<BridgeMethodClass> supplier) {
    return new BridgeMethodClassBuilder(supplier);
  }

  /**
   * Sets the value for the {@code something} property.
   * To be more precise, this will lead to {@link BridgeMethodAbstract#setSomething(String)} being called on construction of the object.
   * @param something the value to set.
   * @return This builder for chained calls.
   */
  public BridgeMethodClassBuilder something(final String something) {
    this.fieldValue.something = something;
    this.callSetterFor.something = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link BridgeMethodClass}.
   * @return The constructed instance. Never {@code null}.
   */
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
