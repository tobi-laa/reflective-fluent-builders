package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.second;

import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class SecondSuperClassInDifferentPackageBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<SecondSuperClassInDifferentPackage> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected SecondSuperClassInDifferentPackageBuilder(
      final Supplier<SecondSuperClassInDifferentPackage> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static SecondSuperClassInDifferentPackageBuilder newInstance() {
    return new SecondSuperClassInDifferentPackageBuilder(SecondSuperClassInDifferentPackage::new);
  }

  public static SecondSuperClassInDifferentPackageBuilder withSupplier(
      final Supplier<SecondSuperClassInDifferentPackage> supplier) {
    return new SecondSuperClassInDifferentPackageBuilder(supplier);
  }

  public SecondSuperClassInDifferentPackageBuilder eight(final int eight) {
    this.fieldValue.eight = eight;
    this.callSetterFor.eight = true;
    return this;
  }

  public SecondSuperClassInDifferentPackageBuilder five(final int five) {
    this.fieldValue.five = five;
    this.callSetterFor.five = true;
    return this;
  }

  public SecondSuperClassInDifferentPackageBuilder four(final int four) {
    this.fieldValue.four = four;
    this.callSetterFor.four = true;
    return this;
  }

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
