package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class NameCollisionsBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private Supplier<NameCollisions> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected NameCollisionsBuilder(final Supplier<NameCollisions> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static NameCollisionsBuilder newInstance() {
    return new NameCollisionsBuilder(NameCollisions::new);
  }

  public static NameCollisionsBuilder withSupplier(final Supplier<NameCollisions> supplier) {
    return new NameCollisionsBuilder(supplier);
  }

  public NameCollisionsBuilder anotherField(final boolean anotherField) {
    this.fieldValue.anotherField = anotherField;
    this.callSetterFor.anotherField = true;
    return this;
  }

  public NameCollisionsBuilder anotherField(final int anotherField0) {
    this.fieldValue.anotherField0 = anotherField0;
    this.callSetterFor.anotherField0 = true;
    return this;
  }

  public NameCollisionsBuilder anotherField(final String anotherField1) {
    this.fieldValue.anotherField1 = anotherField1;
    this.callSetterFor.anotherField1 = true;
    return this;
  }

  public NameCollisionsBuilder field(final int field) {
    this.fieldValue.field = field;
    this.callSetterFor.field = true;
    return this;
  }

  public NameCollisionsBuilder field(final String field0) {
    this.fieldValue.field0 = field0;
    this.callSetterFor.field0 = true;
    return this;
  }

  public NameCollisions build() {
    final NameCollisions objectToBuild = objectSupplier.get();
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
