package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

/**
 * Builder for {@link ClassWithBuilderExisting}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ClassWithBuilderExistingBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("all")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<ClassWithBuilderExisting> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link ClassWithBuilderExisting} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected ClassWithBuilderExistingBuilder(
      final Supplier<ClassWithBuilderExisting> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link ClassWithBuilderExistingBuilder} that will work on an instance of {@link ClassWithBuilderExisting} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static ClassWithBuilderExistingBuilder withSupplier(
      final Supplier<ClassWithBuilderExisting> supplier) {
    return new ClassWithBuilderExistingBuilder(supplier);
  }

  /**
   * Sets the value for the {@code aField} property.
   * To be more precise, this will lead to {@link ClassWithBuilderExisting#setAField(int)} being called on construction of the object.
   * @param aField the value to set.
   * @return This builder for chained calls.
   */
  public ClassWithBuilderExistingBuilder aField(final int aField) {
    this.fieldValue.aField = aField;
    this.callSetterFor.aField = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link ClassWithBuilderExisting}.
   * @return The constructed instance. Never {@code null}.
   */
  public ClassWithBuilderExisting build() {
    final ClassWithBuilderExisting objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.aField) {
      objectToBuild.setAField(this.fieldValue.aField);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean aField;
  }

  private class FieldValue {
    int aField;
  }

  /**
   * Builder for {@link ClassWithBuilderExisting.ClassWithBuilderExistingBuilder}.
   */
  @Generated(
      value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
      date = "3333-03-13T00:00Z[UTC]"
  )
  public static class ClassWithBuilderExistingBuilderBuilder {
    /**
     * This field is solely used to be able to detect generated builders via reflection at a later stage.
     */
    @SuppressWarnings("all")
    private boolean ______generatedByReflectiveFluentBuildersGenerator;

    private final Supplier<ClassWithBuilderExisting.ClassWithBuilderExistingBuilder> objectSupplier;

    private final CallSetterFor callSetterFor = new CallSetterFor();

    private final FieldValue fieldValue = new FieldValue();

    /**
     * Creates a new instance of {@link ClassWithBuilderExisting.ClassWithBuilderExistingBuilder} using the given {@code objectSupplier}.
     * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
     */
    protected ClassWithBuilderExistingBuilderBuilder(
        final Supplier<ClassWithBuilderExisting.ClassWithBuilderExistingBuilder> objectSupplier) {
      this.objectSupplier = Objects.requireNonNull(objectSupplier);
    }

    /**
     * Creates an instance of {@link ClassWithBuilderExistingBuilderBuilder} that will work on a new instance of {@link ClassWithBuilderExisting.ClassWithBuilderExistingBuilder} once {@link #build()} is called.
     */
    public static ClassWithBuilderExistingBuilderBuilder newInstance() {
      return new ClassWithBuilderExistingBuilderBuilder(ClassWithBuilderExisting.ClassWithBuilderExistingBuilder::new);
    }

    /**
     * Creates an instance of {@link ClassWithBuilderExistingBuilderBuilder} that will work on an instance of {@link ClassWithBuilderExisting.ClassWithBuilderExistingBuilder} that is created initially by the given {@code supplier} once {@link #build()} is called.
     */
    public static ClassWithBuilderExistingBuilderBuilder withSupplier(
        final Supplier<ClassWithBuilderExisting.ClassWithBuilderExistingBuilder> supplier) {
      return new ClassWithBuilderExistingBuilderBuilder(supplier);
    }

    /**
     * Performs the actual construction of an instance for {@link ClassWithBuilderExisting.ClassWithBuilderExistingBuilder}.
     * @return The constructed instance. Never {@code null}.
     */
    public ClassWithBuilderExisting.ClassWithBuilderExistingBuilder build() {
      final ClassWithBuilderExisting.ClassWithBuilderExistingBuilder objectToBuild = this.objectSupplier.get();
      return objectToBuild;
    }

    private class CallSetterFor {
    }

    private class FieldValue {
    }
  }
}
