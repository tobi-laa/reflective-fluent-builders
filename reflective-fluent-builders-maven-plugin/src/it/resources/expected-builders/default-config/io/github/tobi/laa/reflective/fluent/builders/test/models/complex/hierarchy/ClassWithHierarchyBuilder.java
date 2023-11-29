package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy;

import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

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

  protected ClassWithHierarchyBuilder(final Supplier<ClassWithHierarchy> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static ClassWithHierarchyBuilder newInstance() {
    return new ClassWithHierarchyBuilder(ClassWithHierarchy::new);
  }

  public static ClassWithHierarchyBuilder withSupplier(
      final Supplier<ClassWithHierarchy> supplier) {
    return new ClassWithHierarchyBuilder(supplier);
  }

  public ClassWithHierarchyBuilder eight(final int eight) {
    this.fieldValue.eight = eight;
    this.callSetterFor.eight = true;
    return this;
  }

  public ClassWithHierarchyBuilder four(final int four) {
    this.fieldValue.four = four;
    this.callSetterFor.four = true;
    return this;
  }

  public ClassWithHierarchyBuilder one(final int one) {
    this.fieldValue.one = one;
    this.callSetterFor.one = true;
    return this;
  }

  public ClassWithHierarchyBuilder seven(final int seven) {
    this.fieldValue.seven = seven;
    this.callSetterFor.seven = true;
    return this;
  }

  public ClassWithHierarchyBuilder three(final int three) {
    this.fieldValue.three = three;
    this.callSetterFor.three = true;
    return this;
  }

  public ClassWithHierarchyBuilder two(final int two) {
    this.fieldValue.two = two;
    this.callSetterFor.two = true;
    return this;
  }

  public ClassWithHierarchy build() {
    final ClassWithHierarchy objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.eight) {
      objectToBuild.setEight(this.fieldValue.eight);
    }
    if (this.callSetterFor.four) {
      objectToBuild.setFour(this.fieldValue.four);
    }
    if (this.callSetterFor.one) {
      objectToBuild.setOne(this.fieldValue.one);
    }
    if (this.callSetterFor.seven) {
      objectToBuild.setSeven(this.fieldValue.seven);
    }
    if (this.callSetterFor.three) {
      objectToBuild.setThree(this.fieldValue.three);
    }
    if (this.callSetterFor.two) {
      objectToBuild.setTwo(this.fieldValue.two);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean eight;

    boolean four;

    boolean one;

    boolean seven;

    boolean three;

    boolean two;
  }

  private class FieldValue {
    int eight;

    int four;

    int one;

    int seven;

    int three;

    int two;
  }
}
