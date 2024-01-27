package io.github.tobi.laa.reflective.fluent.builders.test.models.throwing;

import java.lang.Exception;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ThrowsExceptionBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<ThrowsException> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected ThrowsExceptionBuilder(final Supplier<ThrowsException> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static ThrowsExceptionBuilder newInstance() {
    return new ThrowsExceptionBuilder(ThrowsException::new);
  }

  public static ThrowsExceptionBuilder withSupplier(final Supplier<ThrowsException> supplier) {
    return new ThrowsExceptionBuilder(supplier);
  }

  public CollectionList list() {
    return new CollectionList();
  }

  public ThrowsExceptionBuilder anInt(final int anInt) {
    this.fieldValue.anInt = anInt;
    this.callSetterFor.anInt = true;
    return this;
  }

  public ThrowsExceptionBuilder anItem(final String anItem) {
    if (this.fieldValue.anItems == null) {
      this.fieldValue.anItems = new ArrayList<>();
    }
    this.fieldValue.anItems.add(anItem);
    this.callSetterFor.anItems = true;
    return this;
  }

  public ThrowsExceptionBuilder list(final List<String> list) {
    this.fieldValue.list = list;
    this.callSetterFor.list = true;
    return this;
  }

  public ThrowsException build() throws Exception {
    final ThrowsException objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.anInt) {
      objectToBuild.setAnInt(this.fieldValue.anInt);
    }
    if (this.callSetterFor.anItems && this.fieldValue.anItems != null) {
      this.fieldValue.anItems.forEach(objectToBuild::addAnItem);
    }
    if (this.callSetterFor.list && this.fieldValue.list != null) {
      this.fieldValue.list.forEach(objectToBuild.getList()::add);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean anInt;

    boolean anItems;

    boolean list;
  }

  private class FieldValue {
    int anInt;

    List<String> anItems;

    List<String> list;
  }

  public class CollectionList {
    public CollectionList add(final String item) {
      if (ThrowsExceptionBuilder.this.fieldValue.list == null) {
        ThrowsExceptionBuilder.this.fieldValue.list = new ArrayList<>();
      }
      ThrowsExceptionBuilder.this.fieldValue.list.add(item);
      ThrowsExceptionBuilder.this.callSetterFor.list = true;
      return this;
    }

    public ThrowsExceptionBuilder and() {
      return ThrowsExceptionBuilder.this;
    }
  }
}
