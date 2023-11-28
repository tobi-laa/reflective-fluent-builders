package io.github.tobi.laa.reflective.fluent.builders.test.models.simple.hierarchy;

import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ParentBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<Parent> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected ParentBuilder(final Supplier<Parent> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static ParentBuilder newInstance() {
    return new ParentBuilder(Parent::new);
  }

  public static ParentBuilder withSupplier(final Supplier<Parent> supplier) {
    return new ParentBuilder(supplier);
  }

  public ParentBuilder parentField(final int parentField) {
    this.fieldValue.parentField = parentField;
    this.callSetterFor.parentField = true;
    return this;
  }

  public Parent build() {
    final Parent objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.parentField) {
      objectToBuild.setParentField(this.fieldValue.parentField);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean parentField;
  }

  private class FieldValue {
    int parentField;
  }
}
