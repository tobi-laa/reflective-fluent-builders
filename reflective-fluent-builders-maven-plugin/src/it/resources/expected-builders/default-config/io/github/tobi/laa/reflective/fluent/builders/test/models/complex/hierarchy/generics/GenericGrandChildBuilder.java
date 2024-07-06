package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.generics;

import java.lang.Boolean;
import java.lang.Long;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

/**
 * Builder for {@link GenericGrandChild}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class GenericGrandChildBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("all")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<GenericGrandChild> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link GenericGrandChild} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected GenericGrandChildBuilder(final Supplier<GenericGrandChild> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link GenericGrandChildBuilder} that will work on a new instance of {@link GenericGrandChild} once {@link #build()} is called.
   */
  public static GenericGrandChildBuilder newInstance() {
    return new GenericGrandChildBuilder(GenericGrandChild::new);
  }

  /**
   * Creates an instance of {@link GenericGrandChildBuilder} that will work on an instance of {@link GenericGrandChild} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static GenericGrandChildBuilder withSupplier(final Supplier<GenericGrandChild> supplier) {
    return new GenericGrandChildBuilder(supplier);
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
   * To be more precise, this will lead to {@link GenericGrandChild#setGeneric(Generic<Boolean>)} being called on construction of the object.
   * @param generic the value to set.
   * @return This builder for chained calls.
   */
  public GenericGrandChildBuilder generic(final Generic<Boolean> generic) {
    this.fieldValue.generic = generic;
    this.callSetterFor.generic = true;
    return this;
  }

  /**
   * Sets the value for the {@code genericList} property.
   * To be more precise, this will lead to {@link GenericGrandChild#getGenericList()} being called on construction of the object.
   * @param genericList the value to set.
   * @return This builder for chained calls.
   */
  public GenericGrandChildBuilder genericList(final List<Long> genericList) {
    this.fieldValue.genericList = genericList;
    this.callSetterFor.genericList = true;
    return this;
  }

  /**
   * Sets the value for the {@code list} property.
   * To be more precise, this will lead to {@link GenericChild#setList(List<String>)} being called on construction of the object.
   * @param list the value to set.
   * @return This builder for chained calls.
   */
  public GenericGrandChildBuilder list(final List<String> list) {
    this.fieldValue.list = list;
    this.callSetterFor.list = true;
    return this;
  }

  /**
   * Sets the value for the {@code map} property.
   * To be more precise, this will lead to {@link GenericGrandChild#setMap(Map<Long, Boolean>)} being called on construction of the object.
   * @param map the value to set.
   * @return This builder for chained calls.
   */
  public GenericGrandChildBuilder map(final Map<Long, Boolean> map) {
    this.fieldValue.map = map;
    this.callSetterFor.map = true;
    return this;
  }

  /**
   * Sets the value for the {@code otherGeneric} property.
   * To be more precise, this will lead to {@link GenericParent#setOtherGeneric(Generic<String>)} being called on construction of the object.
   * @param otherGeneric the value to set.
   * @return This builder for chained calls.
   */
  public GenericGrandChildBuilder otherGeneric(final Generic<String> otherGeneric) {
    this.fieldValue.otherGeneric = otherGeneric;
    this.callSetterFor.otherGeneric = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link GenericGrandChild}.
   * @return The constructed instance. Never {@code null}.
   */
  public GenericGrandChild build() {
    final GenericGrandChild objectToBuild = this.objectSupplier.get();
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
    Generic<Boolean> generic;

    List<Long> genericList;

    List<String> list;

    Map<Long, Boolean> map;

    Generic<String> otherGeneric;
  }

  public class CollectionGenericList {
    /**
     * Adds an item to the collection property {@code genericList}.
     * @param item The item to add to the collection {@code genericList}.
     * @return This builder for chained calls.
     */
    public CollectionGenericList add(final Long item) {
      if (GenericGrandChildBuilder.this.fieldValue.genericList == null) {
        GenericGrandChildBuilder.this.fieldValue.genericList = new ArrayList<>();
      }
      GenericGrandChildBuilder.this.fieldValue.genericList.add(item);
      GenericGrandChildBuilder.this.callSetterFor.genericList = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public GenericGrandChildBuilder and() {
      return GenericGrandChildBuilder.this;
    }
  }

  public class CollectionList {
    /**
     * Adds an item to the collection property {@code list}.
     * @param item The item to add to the collection {@code list}.
     * @return This builder for chained calls.
     */
    public CollectionList add(final String item) {
      if (GenericGrandChildBuilder.this.fieldValue.list == null) {
        GenericGrandChildBuilder.this.fieldValue.list = new ArrayList<>();
      }
      GenericGrandChildBuilder.this.fieldValue.list.add(item);
      GenericGrandChildBuilder.this.callSetterFor.list = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public GenericGrandChildBuilder and() {
      return GenericGrandChildBuilder.this;
    }
  }

  public class MapMap {
    /**
     * Adds an entry to the map property {@code map}.
     * @param key The key of the entry to add to the map {@code map}.
     * @param value The value of the entry to add to the map {@code map}.
     * @return This builder for chained calls.
     */
    public MapMap put(final Long key, final Boolean value) {
      if (GenericGrandChildBuilder.this.fieldValue.map == null) {
        GenericGrandChildBuilder.this.fieldValue.map = new HashMap<>();
      }
      GenericGrandChildBuilder.this.fieldValue.map.put(key, value);
      GenericGrandChildBuilder.this.callSetterFor.map = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public GenericGrandChildBuilder and() {
      return GenericGrandChildBuilder.this;
    }
  }
}
