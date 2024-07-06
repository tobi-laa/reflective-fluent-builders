package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

/**
 * Builder for {@link NameCollisions}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class NameCollisionsBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("all")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<NameCollisions> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link NameCollisions} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected NameCollisionsBuilder(final Supplier<NameCollisions> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link NameCollisionsBuilder} that will work on a new instance of {@link NameCollisions} once {@link #build()} is called.
   */
  public static NameCollisionsBuilder newInstance() {
    return new NameCollisionsBuilder(NameCollisions::new);
  }

  /**
   * Creates an instance of {@link NameCollisionsBuilder} that will work on an instance of {@link NameCollisions} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static NameCollisionsBuilder withSupplier(final Supplier<NameCollisions> supplier) {
    return new NameCollisionsBuilder(supplier);
  }

  /**
   * Sets the value for the {@code anotherField} property.
   * To be more precise, this will lead to {@link NameCollisions#setAnotherField(boolean)} being called on construction of the object.
   * @param anotherField the value to set.
   * @return This builder for chained calls.
   */
  public NameCollisionsBuilder anotherField(final boolean anotherField) {
    this.fieldValue.anotherField = anotherField;
    this.callSetterFor.anotherField = true;
    return this;
  }

  /**
   * Sets the value for the {@code anotherField0} property.
   * To be more precise, this will lead to {@link NameCollisions#setAnotherField(int)} being called on construction of the object.
   * @param anotherField the value to set.
   * @return This builder for chained calls.
   */
  public NameCollisionsBuilder anotherField(final int anotherField) {
    this.fieldValue.anotherField0 = anotherField;
    this.callSetterFor.anotherField0 = true;
    return this;
  }

  /**
   * Sets the value for the {@code anotherField1} property.
   * To be more precise, this will lead to {@link NameCollisions#setAnotherField(String)} being called on construction of the object.
   * @param anotherField the value to set.
   * @return This builder for chained calls.
   */
  public NameCollisionsBuilder anotherField(final String anotherField) {
    this.fieldValue.anotherField1 = anotherField;
    this.callSetterFor.anotherField1 = true;
    return this;
  }

  /**
   * Sets the value for the {@code field} property.
   * To be more precise, this will lead to {@link NameCollisions#setField(int)} being called on construction of the object.
   * @param field the value to set.
   * @return This builder for chained calls.
   */
  public NameCollisionsBuilder field(final int field) {
    this.fieldValue.field = field;
    this.callSetterFor.field = true;
    return this;
  }

  /**
   * Sets the value for the {@code field0} property.
   * To be more precise, this will lead to {@link NameCollisions#setField(String)} being called on construction of the object.
   * @param field the value to set.
   * @return This builder for chained calls.
   */
  public NameCollisionsBuilder field(final String field) {
    this.fieldValue.field0 = field;
    this.callSetterFor.field0 = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link NameCollisions}.
   * @return The constructed instance. Never {@code null}.
   */
  public NameCollisions build() {
    final NameCollisions objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.anotherField) {
      objectToBuild.setAnotherField(this.fieldValue.anotherField);
    }
    if (this.callSetterFor.anotherField0) {
      objectToBuild.setAnotherField(this.fieldValue.anotherField0);
    }
    if (this.callSetterFor.anotherField1) {
      objectToBuild.setAnotherField(this.fieldValue.anotherField1);
    }
    if (this.callSetterFor.field) {
      objectToBuild.setField(this.fieldValue.field);
    }
    if (this.callSetterFor.field0) {
      objectToBuild.setField(this.fieldValue.field0);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean anotherField;

    boolean anotherField0;

    boolean anotherField1;

    boolean field;

    boolean field0;
  }

  private class FieldValue {
    boolean anotherField;

    int anotherField0;

    String anotherField1;

    int field;

    String field0;
  }
}
