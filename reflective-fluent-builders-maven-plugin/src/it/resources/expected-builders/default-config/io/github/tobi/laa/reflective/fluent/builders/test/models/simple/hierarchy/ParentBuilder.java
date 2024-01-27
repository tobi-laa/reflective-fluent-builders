package io.github.tobi.laa.reflective.fluent.builders.test.models.simple.hierarchy;

import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

/**
 * Builder for {@link Parent}.
 */
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

  /**
   * Creates a new instance of {@link Parent} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected ParentBuilder(final Supplier<Parent> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link ParentBuilder} that will work on a new instance of {@link Parent} once {@link #build()} is called.
   */
  public static ParentBuilder newInstance() {
    return new ParentBuilder(Parent::new);
  }

  /**
   * Creates an instance of {@link ParentBuilder} that will work on an instance of {@link Parent} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static ParentBuilder withSupplier(final Supplier<Parent> supplier) {
    return new ParentBuilder(supplier);
  }

  /**
   * Sets the value for the {@code parentField} property.
   * To be more precise, this will lead to {@link Parent#setParentField(int)} being called on construction of the object.
   * @param parentField the value to set.
   * @return This builder for chained calls.
   */
  public ParentBuilder parentField(final int parentField) {
    this.fieldValue.parentField = parentField;
    this.callSetterFor.parentField = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link Parent}.
   * @return The constructed instance. Never {@code null}.
   */
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
