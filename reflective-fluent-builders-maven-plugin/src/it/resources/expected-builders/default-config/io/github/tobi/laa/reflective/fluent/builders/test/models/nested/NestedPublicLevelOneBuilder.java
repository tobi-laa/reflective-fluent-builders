package io.github.tobi.laa.reflective.fluent.builders.test.models.nested;

import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class NestedPublicLevelOneBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private Supplier<TopLevelClass.NestedPublicLevelOne> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected NestedPublicLevelOneBuilder(
      final Supplier<TopLevelClass.NestedPublicLevelOne> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static NestedPublicLevelOneBuilder newInstance() {
    return new NestedPublicLevelOneBuilder(TopLevelClass.NestedPublicLevelOne::new);
  }

  public static NestedPublicLevelOneBuilder withSupplier(
      final Supplier<TopLevelClass.NestedPublicLevelOne> supplier) {
    return new NestedPublicLevelOneBuilder(supplier);
  }

  public NestedPublicLevelOneBuilder nested(
      final TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo nested) {
    this.fieldValue.nested = nested;
    this.callSetterFor.nested = true;
    return this;
  }

  public TopLevelClass.NestedPublicLevelOne build() {
    final TopLevelClass.NestedPublicLevelOne objectToBuild = objectSupplier.get();
    if (this.callSetterFor.nested) {
      objectToBuild.setNested(this.fieldValue.nested);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean nested;
  }

  private class FieldValue {
    TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo nested;
  }
}
