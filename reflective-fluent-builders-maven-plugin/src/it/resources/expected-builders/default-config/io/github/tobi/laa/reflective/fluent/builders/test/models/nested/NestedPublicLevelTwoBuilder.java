package io.github.tobi.laa.reflective.fluent.builders.test.models.nested;

import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class NestedPublicLevelTwoBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private Supplier<TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected NestedPublicLevelTwoBuilder(
      final Supplier<TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static NestedPublicLevelTwoBuilder newInstance() {
    return new NestedPublicLevelTwoBuilder(TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo::new);
  }

  public static NestedPublicLevelTwoBuilder withSupplier(
      final Supplier<TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo> supplier) {
    return new NestedPublicLevelTwoBuilder(supplier);
  }

  public NestedPublicLevelTwoBuilder nested(
      final TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree nested) {
    this.fieldValue.nested = nested;
    this.callSetterFor.nested = true;
    return this;
  }

  public TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo build() {
    final TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo objectToBuild = objectSupplier.get();
    if (this.callSetterFor.nested) {
      objectToBuild.setNested(this.fieldValue.nested);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean nested;
  }

  private class FieldValue {
    TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree nested;
  }
}
