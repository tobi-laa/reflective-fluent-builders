package io.github.tobi.laa.reflective.fluent.builders.test.models.custom.naming;

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
public class CustomNamingBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<CustomNaming> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected CustomNamingBuilder(final Supplier<CustomNaming> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static CustomNamingBuilder newInstance() {
    return new CustomNamingBuilder(CustomNaming::new);
  }

  public static CustomNamingBuilder withSupplier(final Supplier<CustomNaming> supplier) {
    return new CustomNamingBuilder(supplier);
  }

  public CollectionAnotherFields anotherFields() {
    return new CollectionAnotherFields();
  }

  public CollectionCollectionField collectionField() {
    return new CollectionCollectionField();
  }

  public CustomNamingBuilder anotherField(final List<String> anotherFields) {
    this.fieldValue.anotherFields = anotherFields;
    this.callSetterFor.anotherFields = true;
    return this;
  }

  public CustomNamingBuilder collectionField(final List<String> collectionField) {
    this.fieldValue.collectionField = collectionField;
    this.callSetterFor.collectionField = true;
    return this;
  }

  public CustomNamingBuilder field(final String field) {
    this.fieldValue.field = field;
    this.callSetterFor.field = true;
    return this;
  }

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

  public class CollectionAnotherFields {
    public CollectionAnotherFields add(final String item) {
      if (CustomNamingBuilder.this.fieldValue.anotherFields == null) {
        CustomNamingBuilder.this.fieldValue.anotherFields = new ArrayList<>();
      }
      CustomNamingBuilder.this.fieldValue.anotherFields.add(item);
      CustomNamingBuilder.this.callSetterFor.anotherFields = true;
      return this;
    }

    public CustomNamingBuilder and() {
      return CustomNamingBuilder.this;
    }
  }

  public class CollectionCollectionField {
    public CollectionCollectionField add(final String item) {
      if (CustomNamingBuilder.this.fieldValue.collectionField == null) {
        CustomNamingBuilder.this.fieldValue.collectionField = new ArrayList<>();
      }
      CustomNamingBuilder.this.fieldValue.collectionField.add(item);
      CustomNamingBuilder.this.callSetterFor.collectionField = true;
      return this;
    }

    public CustomNamingBuilder and() {
      return CustomNamingBuilder.this;
    }
  }
}
