package io.github.tobi.laa.reflective.fluent.builders.test.models.nested;

import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class NestedPackagePrivateLevelOneBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<TopLevelClass.NestedPackagePrivateLevelOne> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected NestedPackagePrivateLevelOneBuilder(
      final Supplier<TopLevelClass.NestedPackagePrivateLevelOne> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static NestedPackagePrivateLevelOneBuilder newInstance() {
    return new NestedPackagePrivateLevelOneBuilder(TopLevelClass.NestedPackagePrivateLevelOne::new);
  }

  public static NestedPackagePrivateLevelOneBuilder withSupplier(
      final Supplier<TopLevelClass.NestedPackagePrivateLevelOne> supplier) {
    return new NestedPackagePrivateLevelOneBuilder(supplier);
  }

  public NestedPackagePrivateLevelOneBuilder field(final int field) {
    this.fieldValue.field = field;
    this.callSetterFor.field = true;
    return this;
  }

  public TopLevelClass.NestedPackagePrivateLevelOne build() {
    final TopLevelClass.NestedPackagePrivateLevelOne objectToBuild = this.objectSupplier.get();
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
