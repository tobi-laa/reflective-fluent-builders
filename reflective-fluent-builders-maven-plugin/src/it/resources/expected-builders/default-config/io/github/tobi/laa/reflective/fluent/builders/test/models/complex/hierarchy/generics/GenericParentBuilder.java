package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.generics;

import java.io.Serializable;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

/**
 * Builder for {@link GenericParent}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class GenericParentBuilder<R, S, T> {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<GenericParent> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link GenericParent} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected GenericParentBuilder(final Supplier<GenericParent> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link GenericParentBuilder} that will work on a new instance of {@link GenericParent} once {@link #build()} is called.
   */
  public static GenericParentBuilder newInstance() {
    return new GenericParentBuilder(GenericParent::new);
  }

  /**
   * Creates an instance of {@link GenericParentBuilder} that will work on an instance of {@link GenericParent} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static GenericParentBuilder withSupplier(final Supplier<GenericParent> supplier) {
    return new GenericParentBuilder(supplier);
  }

  /**
   * Returns an inner builder for the collection property {@code genericList} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.genericList()
   *        .add(item1)
   *        .add(item2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the collection property {@code genericList}.
   */
  public CollectionGenericList genericList() {
    return new CollectionGenericList();
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
   * Returns an inner builder for the map property {@code map} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.map()
   *        .put(key1, value1)
   *        .put(key2, value2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the map property {@code map}.
   */
  public MapMap map() {
    return new MapMap();
  }

  /**
   * Sets the value for the {@code generic} property.
   * To be more precise, this will lead to {@link GenericParent#setGeneric(Generic<T>)} being called on construction of the object.
   * @param generic the value to set.
   * @return This builder for chained calls.
   */
  public GenericParentBuilder generic(final Generic<T> generic) {
    this.fieldValue.generic = generic;
    this.callSetterFor.generic = true;
    return this;
  }

  /**
   * Sets the value for the {@code genericList} property.
   * To be more precise, this will lead to {@link GenericParent#getGenericList()} being called on construction of the object.
   * @param genericList the value to set.
   * @return This builder for chained calls.
   */
  public GenericParentBuilder genericList(final List<Serializable> genericList) {
    this.fieldValue.genericList = genericList;
    this.callSetterFor.genericList = true;
    return this;
  }

  /**
   * Sets the value for the {@code list} property.
   * To be more precise, this will lead to {@link GenericParent#setList(List<R>)} being called on construction of the object.
   * @param list the value to set.
   * @return This builder for chained calls.
   */
  public GenericParentBuilder list(final List<R> list) {
    this.fieldValue.list = list;
    this.callSetterFor.list = true;
    return this;
  }

  /**
   * Sets the value for the {@code map} property.
   * To be more precise, this will lead to {@link GenericParent#setMap(Map<S, T>)} being called on construction of the object.
   * @param map the value to set.
   * @return This builder for chained calls.
   */
  public GenericParentBuilder map(final Map<S, T> map) {
    this.fieldValue.map = map;
    this.callSetterFor.map = true;
    return this;
  }

  /**
   * Sets the value for the {@code otherGeneric} property.
   * To be more precise, this will lead to {@link GenericParent#setOtherGeneric(Generic<R>)} being called on construction of the object.
   * @param otherGeneric the value to set.
   * @return This builder for chained calls.
   */
  public GenericParentBuilder otherGeneric(final Generic<R> otherGeneric) {
    this.fieldValue.otherGeneric = otherGeneric;
    this.callSetterFor.otherGeneric = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link GenericParent}.
   * @return The constructed instance. Never {@code null}.
   */
  public GenericParent build() {
    final GenericParent objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.generic) {
      objectToBuild.setGeneric(this.fieldValue.generic);
    }
    if (this.callSetterFor.genericList && this.fieldValue.genericList != null) {
      this.fieldValue.genericList.forEach(objectToBuild.getGenericList()::add);
    }
    if (this.callSetterFor.list) {
      objectToBuild.setList(this.fieldValue.list);
    }
    if (this.callSetterFor.map) {
      objectToBuild.setMap(this.fieldValue.map);
    }
    if (this.callSetterFor.otherGeneric) {
      objectToBuild.setOtherGeneric(this.fieldValue.otherGeneric);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean generic;

    boolean genericList;

    boolean list;

    boolean map;

    boolean otherGeneric;
  }

  private class FieldValue {
    Generic<T> generic;

    List<Serializable> genericList;

    List<R> list;

    Map<S, T> map;

    Generic<R> otherGeneric;
  }

  public class CollectionGenericList {
    /**
     * Adds an item to the collection property {@code genericList}.
     * @param item The item to add to the collection {@code genericList}.
     * @return This builder for chained calls.
     */
    public CollectionGenericList add(final Serializable item) {
      if (GenericParentBuilder.this.fieldValue.genericList == null) {
        GenericParentBuilder.this.fieldValue.genericList = new ArrayList<>();
      }
      GenericParentBuilder.this.fieldValue.genericList.add(item);
      GenericParentBuilder.this.callSetterFor.genericList = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public GenericParentBuilder and() {
      return GenericParentBuilder.this;
    }
  }

  public class CollectionList {
    /**
     * Adds an item to the collection property {@code list}.
     * @param item The item to add to the collection {@code list}.
     * @return This builder for chained calls.
     */
    public CollectionList add(final R item) {
      if (GenericParentBuilder.this.fieldValue.list == null) {
        GenericParentBuilder.this.fieldValue.list = new ArrayList<>();
      }
      GenericParentBuilder.this.fieldValue.list.add(item);
      GenericParentBuilder.this.callSetterFor.list = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public GenericParentBuilder and() {
      return GenericParentBuilder.this;
    }
  }

  public class MapMap {
    /**
     * Adds an entry to the map property {@code map}.
     * @param key The key of the entry to add to the map {@code map}.
     * @param value The value of the entry to add to the map {@code map}.
     * @return This builder for chained calls.
     */
    public MapMap put(final S key, final T value) {
      if (GenericParentBuilder.this.fieldValue.map == null) {
        GenericParentBuilder.this.fieldValue.map = new HashMap<>();
      }
      GenericParentBuilder.this.fieldValue.map.put(key, value);
      GenericParentBuilder.this.callSetterFor.map = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public GenericParentBuilder and() {
      return GenericParentBuilder.this;
    }
  }
}
