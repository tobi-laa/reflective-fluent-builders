package io.github.tobi.laa.reflective.fluent.builders.test.models.throwing;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.lang.Throwable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ThrowsThrowableBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<ThrowsThrowable> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected ThrowsThrowableBuilder(final Supplier<ThrowsThrowable> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static ThrowsThrowableBuilder newInstance() {
    return new ThrowsThrowableBuilder(ThrowsThrowable::new);
  }

  public static ThrowsThrowableBuilder withSupplier(final Supplier<ThrowsThrowable> supplier) {
    return new ThrowsThrowableBuilder(supplier);
  }

  public CollectionList list() {
    return new CollectionList();
  }

  public ThrowsThrowableBuilder aLong(final long aLong) {
    this.fieldValue.aLong = aLong;
    this.callSetterFor.aLong = true;
    return this;
  }

  public ThrowsThrowableBuilder aString(final String aString) {
    this.fieldValue.aString = aString;
    this.callSetterFor.aString = true;
    return this;
  }

  public ThrowsThrowableBuilder anInt(final int anInt) {
    this.fieldValue.anInt = anInt;
    this.callSetterFor.anInt = true;
    return this;
  }

  public ThrowsThrowableBuilder anItem(final String anItem) {
    if (this.fieldValue.anItems == null) {
      this.fieldValue.anItems = new ArrayList<>();
    }
    this.fieldValue.anItems.add(anItem);
    this.callSetterFor.anItems = true;
    return this;
  }

  public ThrowsThrowableBuilder list(final List<String> list) {
    this.fieldValue.list = list;
    this.callSetterFor.list = true;
    return this;
  }

  public ThrowsThrowable build() throws Throwable {
    final ThrowsThrowable objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.aLong) {
      objectToBuild.setALong(this.fieldValue.aLong);
    }
    if (this.callSetterFor.aString) {
      objectToBuild.setAString(this.fieldValue.aString);
    }
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
    boolean aLong;

    boolean aString;

    boolean anInt;

    boolean anItems;

    boolean list;
  }

  private class FieldValue {
    long aLong;

    String aString;

    int anInt;

    List<String> anItems;

    List<String> list;
  }

  public class CollectionList {
    public CollectionList add(final String item) {
      if (ThrowsThrowableBuilder.this.fieldValue.list == null) {
        ThrowsThrowableBuilder.this.fieldValue.list = new ArrayList<>();
      }
      ThrowsThrowableBuilder.this.fieldValue.list.add(item);
      ThrowsThrowableBuilder.this.callSetterFor.list = true;
      return this;
    }

    public ThrowsThrowableBuilder and() {
      return ThrowsThrowableBuilder.this;
    }
  }
}
