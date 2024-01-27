package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy;

import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

/**
 * Builder for {@link ClassWithHierarchy}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ClassWithHierarchyBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<ClassWithHierarchy> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link ClassWithHierarchy} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected ClassWithHierarchyBuilder(final Supplier<ClassWithHierarchy> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link ClassWithHierarchyBuilder} that will work on a new instance of {@link ClassWithHierarchy} once {@link #build()} is called.
   */
  public static ClassWithHierarchyBuilder newInstance() {
    return new ClassWithHierarchyBuilder(ClassWithHierarchy::new);
  }

  /**
   * Creates an instance of {@link ClassWithHierarchyBuilder} that will work on an instance of {@link ClassWithHierarchy} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static ClassWithHierarchyBuilder withSupplier(
      final Supplier<ClassWithHierarchy> supplier) {
    return new ClassWithHierarchyBuilder(supplier);
  }

  /**
   * Sets the value for the {@code one} property.
   * To be more precise, this will lead to {@link ClassWithHierarchy#setOne(int)} being called on construction of the object.
   * @param one the value to set.
   * @return This builder for chained calls.
   */
  public ClassWithHierarchyBuilder one(final int one) {
    this.fieldValue.one = one;
    this.callSetterFor.one = true;
    return this;
  }

  /**
   * Sets the value for the {@code three} property.
   * To be more precise, this will lead to {@link AnInterface#setThree(int)} being called on construction of the object.
   * @param three the value to set.
   * @return This builder for chained calls.
   */
  public ClassWithHierarchyBuilder three(final int three) {
    this.fieldValue.three = three;
    this.callSetterFor.three = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link ClassWithHierarchy}.
   * @return The constructed instance. Never {@code null}.
   */
  public ClassWithHierarchy build() {
    final ClassWithHierarchy objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.one) {
      objectToBuild.setOne(this.fieldValue.one);
    }
    if (this.callSetterFor.three) {
      objectToBuild.setThree(this.fieldValue.three);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean one;

    boolean three;
  }

  private class FieldValue {
    int one;

    int three;
  }
}
