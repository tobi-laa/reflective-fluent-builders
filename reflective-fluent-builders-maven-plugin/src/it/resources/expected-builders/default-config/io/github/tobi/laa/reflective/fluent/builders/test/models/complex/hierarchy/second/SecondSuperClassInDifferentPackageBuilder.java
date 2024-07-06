package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.second;

import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

/**
 * Builder for {@link SecondSuperClassInDifferentPackage}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class SecondSuperClassInDifferentPackageBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("all")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<SecondSuperClassInDifferentPackage> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link SecondSuperClassInDifferentPackage} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected SecondSuperClassInDifferentPackageBuilder(
      final Supplier<SecondSuperClassInDifferentPackage> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link SecondSuperClassInDifferentPackageBuilder} that will work on a new instance of {@link SecondSuperClassInDifferentPackage} once {@link #build()} is called.
   */
  public static SecondSuperClassInDifferentPackageBuilder newInstance() {
    return new SecondSuperClassInDifferentPackageBuilder(SecondSuperClassInDifferentPackage::new);
  }

  /**
   * Creates an instance of {@link SecondSuperClassInDifferentPackageBuilder} that will work on an instance of {@link SecondSuperClassInDifferentPackage} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static SecondSuperClassInDifferentPackageBuilder withSupplier(
      final Supplier<SecondSuperClassInDifferentPackage> supplier) {
    return new SecondSuperClassInDifferentPackageBuilder(supplier);
  }

  /**
   * Sets the value for the {@code eight} property.
   * To be more precise, this will lead to {@link io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.AnotherInterface#setEight(int)} being called on construction of the object.
   * @param eight the value to set.
   * @return This builder for chained calls.
   */
  public SecondSuperClassInDifferentPackageBuilder eight(final int eight) {
    this.fieldValue.eight = eight;
    this.callSetterFor.eight = true;
    return this;
  }

  /**
   * Sets the value for the {@code five} property.
   * To be more precise, this will lead to {@link SecondSuperClassInDifferentPackage#setFive(int)} being called on construction of the object.
   * @param five the value to set.
   * @return This builder for chained calls.
   */
  public SecondSuperClassInDifferentPackageBuilder five(final int five) {
    this.fieldValue.five = five;
    this.callSetterFor.five = true;
    return this;
  }

  /**
   * Sets the value for the {@code four} property.
   * To be more precise, this will lead to {@link SecondSuperClassInDifferentPackage#setFour(int)} being called on construction of the object.
   * @param four the value to set.
   * @return This builder for chained calls.
   */
  public SecondSuperClassInDifferentPackageBuilder four(final int four) {
    this.fieldValue.four = four;
    this.callSetterFor.four = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link SecondSuperClassInDifferentPackage}.
   * @return The constructed instance. Never {@code null}.
   */
  public SecondSuperClassInDifferentPackage build() {
    final SecondSuperClassInDifferentPackage objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.eight) {
      objectToBuild.setEight(this.fieldValue.eight);
    }
    if (this.callSetterFor.five) {
      objectToBuild.setFive(this.fieldValue.five);
    }
    if (this.callSetterFor.four) {
      objectToBuild.setFour(this.fieldValue.four);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean eight;

    boolean five;

    boolean four;
  }

  private class FieldValue {
    int eight;

    int five;

    int four;
  }
}
