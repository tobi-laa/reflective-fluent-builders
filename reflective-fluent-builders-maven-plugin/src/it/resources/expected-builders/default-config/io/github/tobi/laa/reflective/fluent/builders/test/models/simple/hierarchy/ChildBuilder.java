package io.github.tobi.laa.reflective.fluent.builders.test.models.simple.hierarchy;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ChildBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private Supplier<Child> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected ChildBuilder(final Supplier<Child> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static ChildBuilder newInstance() {
    return new ChildBuilder(Child::new);
  }

  public static ChildBuilder withSupplier(final Supplier<Child> supplier) {
    return new ChildBuilder(supplier);
  }

  public ChildBuilder childField(final String childField) {
    this.fieldValue.childField = childField;
    this.callSetterFor.childField = true;
    return this;
  }

  public ChildBuilder parentField(final int parentField) {
    this.fieldValue.parentField = parentField;
    this.callSetterFor.parentField = true;
    return this;
  }

  public Child build() {
    final Child objectToBuild = objectSupplier.get();
    if (this.callSetterFor.childField) {
      objectToBuild.setChildField(this.fieldValue.childField);
    }
    if (this.callSetterFor.parentField) {
      objectToBuild.setParentField(this.fieldValue.parentField);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean childField;

    boolean parentField;
  }

  private class FieldValue {
    String childField;

    int parentField;
  }
}
