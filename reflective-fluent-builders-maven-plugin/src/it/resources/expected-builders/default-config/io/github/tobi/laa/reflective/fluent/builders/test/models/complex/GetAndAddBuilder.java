package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

/**
 * Builder for {@link GetAndAdd}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class GetAndAddBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("all")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<GetAndAdd> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link GetAndAdd} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected GetAndAddBuilder(final Supplier<GetAndAdd> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link GetAndAddBuilder} that will work on a new instance of {@link GetAndAdd} once {@link #build()} is called.
   */
  public static GetAndAddBuilder newInstance() {
    return new GetAndAddBuilder(GetAndAdd::new);
  }

  /**
   * Creates an instance of {@link GetAndAddBuilder} that will work on an instance of {@link GetAndAdd} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static GetAndAddBuilder withSupplier(final Supplier<GetAndAdd> supplier) {
    return new GetAndAddBuilder(supplier);
  }

  /**
   * Returns an inner builder for the array property {@code listSetterWrongType} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.listSetterWrongType()
   *        .add(item1)
   *        .add(item2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the array property {@code listSetterWrongType}.
   */
  public ArrayListSetterWrongType listSetterWrongType() {
    return new ArrayListSetterWrongType();
  }

  /**
   * Returns an inner builder for the collection property {@code listGetterAndSetter} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.listGetterAndSetter()
   *        .add(item1)
   *        .add(item2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the collection property {@code listGetterAndSetter}.
   */
  public CollectionListGetterAndSetter listGetterAndSetter() {
    return new CollectionListGetterAndSetter();
  }

  /**
   * Returns an inner builder for the collection property {@code listNoGetter} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.listNoGetter()
   *        .add(item1)
   *        .add(item2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the collection property {@code listNoGetter}.
   */
  public CollectionListNoGetter listNoGetter() {
    return new CollectionListNoGetter();
  }

  /**
   * Returns an inner builder for the collection property {@code listNoSetter} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.listNoSetter()
   *        .add(item1)
   *        .add(item2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the collection property {@code listNoSetter}.
   */
  public CollectionListNoSetter listNoSetter() {
    return new CollectionListNoSetter();
  }

  /**
   * Returns an inner builder for the collection property {@code listSetterWrongType0} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.listSetterWrongType0()
   *        .add(item1)
   *        .add(item2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the collection property {@code listSetterWrongType0}.
   */
  public CollectionListSetterWrongType0 listSetterWrongType0() {
    return new CollectionListSetterWrongType0();
  }

  /**
   * Sets the value for the {@code listGetterAndSetter} property.
   * To be more precise, this will lead to {@link GetAndAdd#setListGetterAndSetter(List<String>)} being called on construction of the object.
   * @param listGetterAndSetter the value to set.
   * @return This builder for chained calls.
   */
  public GetAndAddBuilder listGetterAndSetter(final List<String> listGetterAndSetter) {
    this.fieldValue.listGetterAndSetter = listGetterAndSetter;
    this.callSetterFor.listGetterAndSetter = true;
    return this;
  }

  /**
   * Sets the value for the {@code listNoGetter} property.
   * To be more precise, this will lead to {@link GetAndAdd#setListNoGetter(List<String>)} being called on construction of the object.
   * @param listNoGetter the value to set.
   * @return This builder for chained calls.
   */
  public GetAndAddBuilder listNoGetter(final List<String> listNoGetter) {
    this.fieldValue.listNoGetter = listNoGetter;
    this.callSetterFor.listNoGetter = true;
    return this;
  }

  /**
   * Sets the value for the {@code listNoSetter} property.
   * To be more precise, this will lead to {@link GetAndAdd#getListNoSetter()} being called on construction of the object.
   * @param listNoSetter the value to set.
   * @return This builder for chained calls.
   */
  public GetAndAddBuilder listNoSetter(final List<String> listNoSetter) {
    this.fieldValue.listNoSetter = listNoSetter;
    this.callSetterFor.listNoSetter = true;
    return this;
  }

  /**
   * Sets the value for the {@code listSetterWrongType} property.
   * To be more precise, this will lead to {@link GetAndAdd#setListSetterWrongType(String[])} being called on construction of the object.
   * @param listSetterWrongType the value to set.
   * @return This builder for chained calls.
   */
  public GetAndAddBuilder listSetterWrongType(final String[] listSetterWrongType) {
    this.fieldValue.listSetterWrongType = listSetterWrongType;
    this.callSetterFor.listSetterWrongType = true;
    return this;
  }

  /**
   * Sets the value for the {@code listSetterWrongType0} property.
   * To be more precise, this will lead to {@link GetAndAdd#getListSetterWrongType()} being called on construction of the object.
   * @param listSetterWrongType the value to set.
   * @return This builder for chained calls.
   */
  public GetAndAddBuilder listSetterWrongType(final List<String> listSetterWrongType) {
    this.fieldValue.listSetterWrongType0 = listSetterWrongType;
    this.callSetterFor.listSetterWrongType0 = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link GetAndAdd}.
   * @return The constructed instance. Never {@code null}.
   */
  public GetAndAdd build() {
    final GetAndAdd objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.listGetterAndSetter) {
      objectToBuild.setListGetterAndSetter(this.fieldValue.listGetterAndSetter);
    }
    if (this.callSetterFor.listNoGetter) {
      objectToBuild.setListNoGetter(this.fieldValue.listNoGetter);
    }
    if (this.callSetterFor.listNoSetter && this.fieldValue.listNoSetter != null) {
      this.fieldValue.listNoSetter.forEach(objectToBuild.getListNoSetter()::add);
    }
    if (this.callSetterFor.listSetterWrongType) {
      objectToBuild.setListSetterWrongType(this.fieldValue.listSetterWrongType);
    }
    if (this.callSetterFor.listSetterWrongType0 && this.fieldValue.listSetterWrongType0 != null) {
      this.fieldValue.listSetterWrongType0.forEach(objectToBuild.getListSetterWrongType()::add);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean listGetterAndSetter;

    boolean listNoGetter;

    boolean listNoSetter;

    boolean listSetterWrongType;

    boolean listSetterWrongType0;
  }

  private class FieldValue {
    List<String> listGetterAndSetter;

    List<String> listNoGetter;

    List<String> listNoSetter;

    String[] listSetterWrongType;

    List<String> listSetterWrongType0;
  }

  public class ArrayListSetterWrongType {
    private List<String> list;

    /**
     * Adds an item to the array property {@code listSetterWrongType}.
     * @param item The item to add to the array {@code listSetterWrongType}.
     * @return This builder for chained calls.
     */
    public ArrayListSetterWrongType add(final String item) {
      if (this.list == null) {
        this.list = new ArrayList<>();
      }
      this.list.add(item);
      GetAndAddBuilder.this.callSetterFor.listSetterWrongType = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public GetAndAddBuilder and() {
      if (this.list != null) {
        GetAndAddBuilder.this.fieldValue.listSetterWrongType = new String[this.list.size()];
        for (int i = 0; i < this.list.size(); i++) {
          GetAndAddBuilder.this.fieldValue.listSetterWrongType[i] = this.list.get(i);
        }
      }
      return GetAndAddBuilder.this;
    }
  }

  public class CollectionListGetterAndSetter {
    /**
     * Adds an item to the collection property {@code listGetterAndSetter}.
     * @param item The item to add to the collection {@code listGetterAndSetter}.
     * @return This builder for chained calls.
     */
    public CollectionListGetterAndSetter add(final String item) {
      if (GetAndAddBuilder.this.fieldValue.listGetterAndSetter == null) {
        GetAndAddBuilder.this.fieldValue.listGetterAndSetter = new ArrayList<>();
      }
      GetAndAddBuilder.this.fieldValue.listGetterAndSetter.add(item);
      GetAndAddBuilder.this.callSetterFor.listGetterAndSetter = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public GetAndAddBuilder and() {
      return GetAndAddBuilder.this;
    }
  }

  public class CollectionListNoGetter {
    /**
     * Adds an item to the collection property {@code listNoGetter}.
     * @param item The item to add to the collection {@code listNoGetter}.
     * @return This builder for chained calls.
     */
    public CollectionListNoGetter add(final String item) {
      if (GetAndAddBuilder.this.fieldValue.listNoGetter == null) {
        GetAndAddBuilder.this.fieldValue.listNoGetter = new ArrayList<>();
      }
      GetAndAddBuilder.this.fieldValue.listNoGetter.add(item);
      GetAndAddBuilder.this.callSetterFor.listNoGetter = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public GetAndAddBuilder and() {
      return GetAndAddBuilder.this;
    }
  }

  public class CollectionListNoSetter {
    /**
     * Adds an item to the collection property {@code listNoSetter}.
     * @param item The item to add to the collection {@code listNoSetter}.
     * @return This builder for chained calls.
     */
    public CollectionListNoSetter add(final String item) {
      if (GetAndAddBuilder.this.fieldValue.listNoSetter == null) {
        GetAndAddBuilder.this.fieldValue.listNoSetter = new ArrayList<>();
      }
      GetAndAddBuilder.this.fieldValue.listNoSetter.add(item);
      GetAndAddBuilder.this.callSetterFor.listNoSetter = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public GetAndAddBuilder and() {
      return GetAndAddBuilder.this;
    }
  }

  public class CollectionListSetterWrongType0 {
    /**
     * Adds an item to the collection property {@code listSetterWrongType0}.
     * @param item The item to add to the collection {@code listSetterWrongType0}.
     * @return This builder for chained calls.
     */
    public CollectionListSetterWrongType0 add(final String item) {
      if (GetAndAddBuilder.this.fieldValue.listSetterWrongType0 == null) {
        GetAndAddBuilder.this.fieldValue.listSetterWrongType0 = new ArrayList<>();
      }
      GetAndAddBuilder.this.fieldValue.listSetterWrongType0.add(item);
      GetAndAddBuilder.this.callSetterFor.listSetterWrongType0 = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public GetAndAddBuilder and() {
      return GetAndAddBuilder.this;
    }
  }
}
