package io.github.tobi.laa.reflective.fluent.builders.test.models;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

/**
 * Builder for {@link Dog}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class DogBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("all")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<Dog> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link Dog} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected DogBuilder(final Supplier<Dog> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link DogBuilder} that will work on a new instance of {@link Dog} once {@link #build()} is called.
   */
  public static DogBuilder newInstance() {
    return new DogBuilder(Dog::new);
  }

  /**
   * Creates an instance of {@link DogBuilder} that will work on an instance of {@link Dog} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static DogBuilder withSupplier(final Supplier<Dog> supplier) {
    return new DogBuilder(supplier);
  }

  /**
   * Sets the value for the {@code name} property.
   * To be more precise, this will lead to {@link Dog#setName(String)} being called on construction of the object.
   * @param name the value to set.
   * @return This builder for chained calls.
   */
  public DogBuilder name(final String name) {
    this.fieldValue.name = name;
    this.callSetterFor.name = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link Dog}.
   * @return The constructed instance. Never {@code null}.
   */
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
