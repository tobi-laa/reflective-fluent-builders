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

  public ClassWithHierarchyBuilder one(final int one) {
    this.fieldValue.one = one;
    this.callSetterFor.one = true;
    return this;
  }

  public ClassWithHierarchyBuilder three(final int three) {
    this.fieldValue.three = three;
    this.callSetterFor.three = true;
    return this;
  }

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
