package io.github.tobi.laa.reflective.fluent.builders.test.models.simple.hierarchy;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

/**
 * Builder for {@link Child}.
 */
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

  private final Supplier<Child> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link Child} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected ChildBuilder(final Supplier<Child> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link ChildBuilder} that will work on a new instance of {@link Child} once {@link #build()} is called.
   */
  public static ChildBuilder newInstance() {
    return new ChildBuilder(Child::new);
  }

  /**
   * Creates an instance of {@link ChildBuilder} that will work on an instance of {@link Child} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static ChildBuilder withSupplier(final Supplier<Child> supplier) {
    return new ChildBuilder(supplier);
  }

  /**
   * Sets the value for the {@code childField} property.
   * To be more precise, this will lead to {@link Child#setChildField(String)} being called on construction of the object.
   * @param childField the value to set.
   * @return This builder for chained calls.
   */
  public ChildBuilder childField(final String childField) {
    this.fieldValue.childField = childField;
    this.callSetterFor.childField = true;
    return this;
  }

  /**
   * Sets the value for the {@code parentField} property.
   * To be more precise, this will lead to {@link Parent#setParentField(int)} being called on construction of the object.
   * @param parentField the value to set.
   * @return This builder for chained calls.
   */
  public ChildBuilder parentField(final int parentField) {
    this.fieldValue.parentField = parentField;
    this.callSetterFor.parentField = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link Child}.
   * @return The constructed instance. Never {@code null}.
   */
  public Child build() {
    final Child objectToBuild = this.objectSupplier.get();
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
