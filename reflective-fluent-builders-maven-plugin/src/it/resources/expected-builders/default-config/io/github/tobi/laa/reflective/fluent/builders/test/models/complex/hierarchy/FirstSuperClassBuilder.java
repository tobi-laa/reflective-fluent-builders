package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy;

import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class FirstSuperClassBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private Supplier<FirstSuperClass> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected FirstSuperClassBuilder(final Supplier<FirstSuperClass> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static FirstSuperClassBuilder newInstance() {
    return new FirstSuperClassBuilder(FirstSuperClass::new);
  }

  public static FirstSuperClassBuilder withSupplier(final Supplier<FirstSuperClass> supplier) {
    return new FirstSuperClassBuilder(supplier);
  }

  public FirstSuperClassBuilder eight(final int eight) {
    this.fieldValue.eight = eight;
    this.callSetterFor.eight = true;
    return this;
  }

  public FirstSuperClassBuilder four(final int four) {
    this.fieldValue.four = four;
    this.callSetterFor.four = true;
    return this;
  }

  public FirstSuperClassBuilder seven(final int seven) {
    this.fieldValue.seven = seven;
    this.callSetterFor.seven = true;
    return this;
  }

  public FirstSuperClassBuilder two(final int two) {
    this.fieldValue.two = two;
    this.callSetterFor.two = true;
    return this;
  }

  public FirstSuperClass build() {
    final FirstSuperClass objectToBuild = objectSupplier.get();
    if (this.callSetterFor.eight) {
      objectToBuild.setEight(this.fieldValue.eight);
    }
    if (this.callSetterFor.four) {
      objectToBuild.setFour(this.fieldValue.four);
    }
    if (this.callSetterFor.seven) {
      objectToBuild.setSeven(this.fieldValue.seven);
    }
    if (this.callSetterFor.two) {
      objectToBuild.setTwo(this.fieldValue.two);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean eight;

    boolean four;

    boolean seven;

    boolean two;
  }

  private class FieldValue {
    int eight;

    int four;

    int seven;

    int two;
  }
}
