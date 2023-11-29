package io.github.tobi.laa.reflective.fluent.builders.test.models;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class DogBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<Dog> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected DogBuilder(final Supplier<Dog> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static DogBuilder newInstance() {
    return new DogBuilder(Dog::new);
  }

  public static DogBuilder withSupplier(final Supplier<Dog> supplier) {
    return new DogBuilder(supplier);
  }

  public DogBuilder name(final String name) {
    this.fieldValue.name = name;
    this.callSetterFor.name = true;
    return this;
  }

  public Dog build() {
    final Dog objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.name) {
      objectToBuild.setName(this.fieldValue.name);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean name;
  }

  private class FieldValue {
    String name;
  }
}
