package io.github.tobi.laa.reflective.fluent.builders.test.models.nested;

import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class NestedPublicLevelThreeBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private Supplier<TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected NestedPublicLevelThreeBuilder(
      final Supplier<TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static NestedPublicLevelThreeBuilder newInstance() {
    return new NestedPublicLevelThreeBuilder(TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree::new);
  }

  public static NestedPublicLevelThreeBuilder withSupplier(
      final Supplier<TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree> supplier) {
    return new NestedPublicLevelThreeBuilder(supplier);
  }

  public NestedPublicLevelThreeBuilder field(final int field) {
    this.fieldValue.field = field;
    this.callSetterFor.field = true;
    return this;
  }

  public TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree build() {
    final TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree objectToBuild = objectSupplier.get();
    if (this.callSetterFor.field) {
      objectToBuild.setField(this.fieldValue.field);
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
