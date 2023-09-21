package io.github.tobi.laa.reflective.fluent.builders.test.models.nested;

import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class NestedProtectedLevelOneBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private Supplier<TopLevelClass.NestedProtectedLevelOne> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected NestedProtectedLevelOneBuilder(
      final Supplier<TopLevelClass.NestedProtectedLevelOne> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static NestedProtectedLevelOneBuilder newInstance() {
    return new NestedProtectedLevelOneBuilder(TopLevelClass.NestedProtectedLevelOne::new);
  }

  public static NestedProtectedLevelOneBuilder withSupplier(
      final Supplier<TopLevelClass.NestedProtectedLevelOne> supplier) {
    return new NestedProtectedLevelOneBuilder(supplier);
  }

  public NestedProtectedLevelOneBuilder field(final int field) {
    this.fieldValue.field = field;
    this.callSetterFor.field = true;
    return this;
  }

  public TopLevelClass.NestedProtectedLevelOne build() {
    final TopLevelClass.NestedProtectedLevelOne objectToBuild = objectSupplier.get();
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
