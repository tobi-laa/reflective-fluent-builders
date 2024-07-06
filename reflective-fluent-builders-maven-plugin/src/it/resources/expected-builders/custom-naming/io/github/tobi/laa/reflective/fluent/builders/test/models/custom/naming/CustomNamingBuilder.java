package io.github.tobi.laa.reflective.fluent.builders.test.models.custom.naming;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

/**
 * Builder for {@link CustomNaming}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class CustomNamingBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("all")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<CustomNaming> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link CustomNaming} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected CustomNamingBuilder(final Supplier<CustomNaming> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link CustomNamingBuilder} that will work on a new instance of {@link CustomNaming} once {@link #build()} is called.
   */
  public static CustomNamingBuilder newInstance() {
    return new CustomNamingBuilder(CustomNaming::new);
  }

  /**
   * Creates an instance of {@link CustomNamingBuilder} that will work on an instance of {@link CustomNaming} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static CustomNamingBuilder withSupplier(final Supplier<CustomNaming> supplier) {
    return new CustomNamingBuilder(supplier);
  }

  /**
   * Returns an inner builder for the collection property {@code collectionField} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.collectionField()
   *        .add(item1)
   *        .add(item2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the collection property {@code collectionField}.
   */
  public CollectionCollectionField collectionField() {
    return new CollectionCollectionField();
  }

  /**
   * Adds a value to the {@code anotherFields} property.
   * To be more precise, this will lead to {@link CustomNaming#insertAnotherFieldIntoCollection(List<String>)} being called on construction of the object.
   * @param anotherField the value to add to {@code anotherFields}.
   * @return This builder for chained calls.
   */
  public CustomNamingBuilder anotherField(final String anotherField) {
    if (this.fieldValue.anotherFields == null) {
      this.fieldValue.anotherFields = new ArrayList<>();
    }
    this.fieldValue.anotherFields.add(anotherField);
    this.callSetterFor.anotherFields = true;
    return this;
  }

  /**
   * Sets the value for the {@code collectionField} property.
   * To be more precise, this will lead to {@link CustomNaming#retrieveCollectionField()} being called on construction of the object.
   * @param collectionField the value to set.
   * @return This builder for chained calls.
   */
  public CustomNamingBuilder collectionField(final List<String> collectionField) {
    this.fieldValue.collectionField = collectionField;
    this.callSetterFor.collectionField = true;
    return this;
  }

  /**
   * Sets the value for the {@code field} property.
   * To be more precise, this will lead to {@link CustomNaming#modifyField(String)} being called on construction of the object.
   * @param field the value to set.
   * @return This builder for chained calls.
   */
  public CustomNamingBuilder field(final String field) {
    this.fieldValue.field = field;
    this.callSetterFor.field = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link CustomNaming}.
   * @return The constructed instance. Never {@code null}.
   */
  public CustomNaming build() {
    final CustomNaming objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.anotherFields && this.fieldValue.anotherFields != null) {
      this.fieldValue.anotherFields.forEach(objectToBuild::insertAnotherFieldIntoCollection);
    }
    if (this.callSetterFor.collectionField && this.fieldValue.collectionField != null) {
      this.fieldValue.collectionField.forEach(objectToBuild.retrieveCollectionField()::add);
    }
    if (this.callSetterFor.field) {
      objectToBuild.modifyField(this.fieldValue.field);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean anotherFields;

    boolean collectionField;

    boolean field;
  }

  private class FieldValue {
    List<String> anotherFields;

    List<String> collectionField;

    String field;
  }

  public class CollectionCollectionField {
    /**
     * Adds an item to the collection property {@code collectionField}.
     * @param item The item to add to the collection {@code collectionField}.
     * @return This builder for chained calls.
     */
    public CollectionCollectionField add(final String item) {
      if (CustomNamingBuilder.this.fieldValue.collectionField == null) {
        CustomNamingBuilder.this.fieldValue.collectionField = new ArrayList<>();
      }
      CustomNamingBuilder.this.fieldValue.collectionField.add(item);
      CustomNamingBuilder.this.callSetterFor.collectionField = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public CustomNamingBuilder and() {
      return CustomNamingBuilder.this;
    }
  }
}
