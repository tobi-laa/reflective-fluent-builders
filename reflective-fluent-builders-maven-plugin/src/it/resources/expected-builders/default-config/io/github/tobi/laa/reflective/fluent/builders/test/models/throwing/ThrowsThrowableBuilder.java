package io.github.tobi.laa.reflective.fluent.builders.test.models.throwing;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.lang.Throwable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

/**
 * Builder for {@link ThrowsThrowable}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ThrowsThrowableBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("all")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<ThrowsThrowable> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link ThrowsThrowable} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected ThrowsThrowableBuilder(final Supplier<ThrowsThrowable> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link ThrowsThrowableBuilder} that will work on a new instance of {@link ThrowsThrowable} once {@link #build()} is called.
   */
  public static ThrowsThrowableBuilder newInstance() {
    return new ThrowsThrowableBuilder(ThrowsThrowable::new);
  }

  /**
   * Creates an instance of {@link ThrowsThrowableBuilder} that will work on an instance of {@link ThrowsThrowable} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static ThrowsThrowableBuilder withSupplier(final Supplier<ThrowsThrowable> supplier) {
    return new ThrowsThrowableBuilder(supplier);
  }

  /**
   * Returns an inner builder for the collection property {@code list} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.list()
   *        .add(item1)
   *        .add(item2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the collection property {@code list}.
   */
  public CollectionList list() {
    return new CollectionList();
  }

  /**
   * Sets the value for the {@code aLong} property.
   * To be more precise, this will lead to {@link ThrowsThrowable#setALong(long)} being called on construction of the object.
   * @param aLong the value to set.
   * @return This builder for chained calls.
   */
  public ThrowsThrowableBuilder aLong(final long aLong) {
    this.fieldValue.aLong = aLong;
    this.callSetterFor.aLong = true;
    return this;
  }

  /**
   * Sets the value for the {@code aString} property.
   * To be more precise, this will lead to {@link ThrowsThrowable#setAString(String)} being called on construction of the object.
   * @param aString the value to set.
   * @return This builder for chained calls.
   */
  public ThrowsThrowableBuilder aString(final String aString) {
    this.fieldValue.aString = aString;
    this.callSetterFor.aString = true;
    return this;
  }

  /**
   * Sets the value for the {@code anInt} property.
   * To be more precise, this will lead to {@link ThrowsThrowable#setAnInt(int)} being called on construction of the object.
   * @param anInt the value to set.
   * @return This builder for chained calls.
   */
  public ThrowsThrowableBuilder anInt(final int anInt) {
    this.fieldValue.anInt = anInt;
    this.callSetterFor.anInt = true;
    return this;
  }

  /**
   * Adds a value to the {@code anItems} property.
   * To be more precise, this will lead to {@link ThrowsThrowable#addAnItem(List<String>)} being called on construction of the object.
   * @param anItem the value to add to {@code anItems}.
   * @return This builder for chained calls.
   */
  public ThrowsThrowableBuilder anItem(final String anItem) {
    if (this.fieldValue.anItems == null) {
      this.fieldValue.anItems = new ArrayList<>();
    }
    this.fieldValue.anItems.add(anItem);
    this.callSetterFor.anItems = true;
    return this;
  }

  /**
   * Sets the value for the {@code list} property.
   * To be more precise, this will lead to {@link ThrowsThrowable#getList()} being called on construction of the object.
   * @param list the value to set.
   * @return This builder for chained calls.
   */
  public ThrowsThrowableBuilder list(final List<String> list) {
    this.fieldValue.list = list;
    this.callSetterFor.list = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link ThrowsThrowable}.
   * @return The constructed instance. Never {@code null}.
   * @throws Throwable If thrown by an accessor of ThrowsThrowable, i.e. a setter, getter or adder.
   */
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
    /**
     * Adds an item to the collection property {@code list}.
     * @param item The item to add to the collection {@code list}.
     * @return This builder for chained calls.
     */
    public CollectionList add(final String item) {
      if (ThrowsThrowableBuilder.this.fieldValue.list == null) {
        ThrowsThrowableBuilder.this.fieldValue.list = new ArrayList<>();
      }
      ThrowsThrowableBuilder.this.fieldValue.list.add(item);
      ThrowsThrowableBuilder.this.callSetterFor.list = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public ThrowsThrowableBuilder and() {
      return ThrowsThrowableBuilder.this;
    }
  }
}
