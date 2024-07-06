package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import java.lang.Boolean;
import java.lang.Float;
import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

/**
 * Builder for {@link ClassWithCollections}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ClassWithCollectionsBuilder<T, U> {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("all")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<ClassWithCollections> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link ClassWithCollections} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected ClassWithCollectionsBuilder(final Supplier<ClassWithCollections> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link ClassWithCollectionsBuilder} that will work on a new instance of {@link ClassWithCollections} once {@link #build()} is called.
   */
  public static ClassWithCollectionsBuilder newInstance() {
    return new ClassWithCollectionsBuilder(ClassWithCollections::new);
  }

  /**
   * Creates an instance of {@link ClassWithCollectionsBuilder} that will work on an instance of {@link ClassWithCollections} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static ClassWithCollectionsBuilder withSupplier(
      final Supplier<ClassWithCollections> supplier) {
    return new ClassWithCollectionsBuilder(supplier);
  }

  /**
   * Returns an inner builder for the array property {@code floats} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.floats()
   *        .add(item1)
   *        .add(item2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the array property {@code floats}.
   */
  public ArrayFloats floats() {
    return new ArrayFloats();
  }

  /**
   * Returns an inner builder for the collection property {@code deque} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.deque()
   *        .add(item1)
   *        .add(item2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the collection property {@code deque}.
   */
  public CollectionDeque deque() {
    return new CollectionDeque();
  }

  /**
   * Returns an inner builder for the collection property {@code ints} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.ints()
   *        .add(item1)
   *        .add(item2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the collection property {@code ints}.
   */
  public CollectionInts ints() {
    return new CollectionInts();
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
   * Returns an inner builder for the collection property {@code set} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.set()
   *        .add(item1)
   *        .add(item2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the collection property {@code set}.
   */
  public CollectionSet set() {
    return new CollectionSet();
  }

  /**
   * Returns an inner builder for the collection property {@code sortedSetWild} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.sortedSetWild()
   *        .add(item1)
   *        .add(item2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the collection property {@code sortedSetWild}.
   */
  public CollectionSortedSetWild sortedSetWild() {
    return new CollectionSortedSetWild();
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
   * Returns an inner builder for the map property {@code mapNoTypeArgs} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.mapNoTypeArgs()
   *        .put(key1, value1)
   *        .put(key2, value2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the map property {@code mapNoTypeArgs}.
   */
  public MapMapNoTypeArgs mapNoTypeArgs() {
    return new MapMapNoTypeArgs();
  }

  /**
   * Returns an inner builder for the map property {@code mapTU} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.mapTU()
   *        .put(key1, value1)
   *        .put(key2, value2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the map property {@code mapTU}.
   */
  public MapMapTU mapTU() {
    return new MapMapTU();
  }

  /**
   * Returns an inner builder for the map property {@code mapWildObj} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.mapWildObj()
   *        .put(key1, value1)
   *        .put(key2, value2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the map property {@code mapWildObj}.
   */
  public MapMapWildObj mapWildObj() {
    return new MapMapWildObj();
  }

  /**
   * Sets the value for the {@code deque} property.
   * To be more precise, this will lead to {@link ClassWithCollections#setDeque(Deque<?>)} being called on construction of the object.
   * @param deque the value to set.
   * @return This builder for chained calls.
   */
  public ClassWithCollectionsBuilder deque(final Deque<Object> deque) {
    this.fieldValue.deque = deque;
    this.callSetterFor.deque = true;
    return this;
  }

  /**
   * Sets the value for the {@code floats} property.
   * To be more precise, this will lead to {@link ClassWithCollections#setFloats(float[])} being called on construction of the object.
   * @param floats the value to set.
   * @return This builder for chained calls.
   */
  public ClassWithCollectionsBuilder floats(final float[] floats) {
    this.fieldValue.floats = floats;
    this.callSetterFor.floats = true;
    return this;
  }

  /**
   * Sets the value for the {@code ints} property.
   * To be more precise, this will lead to {@link ClassWithCollections#setInts(Collection<Integer>)} being called on construction of the object.
   * @param ints the value to set.
   * @return This builder for chained calls.
   */
  public ClassWithCollectionsBuilder ints(final Collection<Integer> ints) {
    this.fieldValue.ints = ints;
    this.callSetterFor.ints = true;
    return this;
  }

  /**
   * Sets the value for the {@code list} property.
   * To be more precise, this will lead to {@link ClassWithCollections#setList(List)} being called on construction of the object.
   * @param list the value to set.
   * @return This builder for chained calls.
   */
  public ClassWithCollectionsBuilder list(final List list) {
    this.fieldValue.list = list;
    this.callSetterFor.list = true;
    return this;
  }

  /**
   * Sets the value for the {@code listWithTwoParams} property.
   * To be more precise, this will lead to {@link ClassWithCollections#setListWithTwoParams(ListWithTwoParams<String, Integer>)} being called on construction of the object.
   * @param listWithTwoParams the value to set.
   * @return This builder for chained calls.
   */
  public ClassWithCollectionsBuilder listWithTwoParams(
      final ListWithTwoParams<String, Integer> listWithTwoParams) {
    this.fieldValue.listWithTwoParams = listWithTwoParams;
    this.callSetterFor.listWithTwoParams = true;
    return this;
  }

  /**
   * Sets the value for the {@code map} property.
   * To be more precise, this will lead to {@link ClassWithCollections#setMap(Map<String, Object>)} being called on construction of the object.
   * @param map the value to set.
   * @return This builder for chained calls.
   */
  public ClassWithCollectionsBuilder map(final Map<String, Object> map) {
    this.fieldValue.map = map;
    this.callSetterFor.map = true;
    return this;
  }

  /**
   * Sets the value for the {@code mapNoTypeArgs} property.
   * To be more precise, this will lead to {@link ClassWithCollections#setMapNoTypeArgs(Map)} being called on construction of the object.
   * @param mapNoTypeArgs the value to set.
   * @return This builder for chained calls.
   */
  public ClassWithCollectionsBuilder mapNoTypeArgs(final Map mapNoTypeArgs) {
    this.fieldValue.mapNoTypeArgs = mapNoTypeArgs;
    this.callSetterFor.mapNoTypeArgs = true;
    return this;
  }

  /**
   * Sets the value for the {@code mapTU} property.
   * To be more precise, this will lead to {@link ClassWithCollections#setMapTU(Map<T, U>)} being called on construction of the object.
   * @param mapTU the value to set.
   * @return This builder for chained calls.
   */
  public ClassWithCollectionsBuilder mapTU(final Map<T, U> mapTU) {
    this.fieldValue.mapTU = mapTU;
    this.callSetterFor.mapTU = true;
    return this;
  }

  /**
   * Sets the value for the {@code mapWildObj} property.
   * To be more precise, this will lead to {@link ClassWithCollections#setMapWildObj(Map<?, Object>)} being called on construction of the object.
   * @param mapWildObj the value to set.
   * @return This builder for chained calls.
   */
  public ClassWithCollectionsBuilder mapWildObj(final Map<Object, Object> mapWildObj) {
    this.fieldValue.mapWildObj = mapWildObj;
    this.callSetterFor.mapWildObj = true;
    return this;
  }

  /**
   * Sets the value for the {@code mapWithThreeParams} property.
   * To be more precise, this will lead to {@link ClassWithCollections#setMapWithThreeParams(MapWithThreeParams<String, Integer, Boolean>)} being called on construction of the object.
   * @param mapWithThreeParams the value to set.
   * @return This builder for chained calls.
   */
  public ClassWithCollectionsBuilder mapWithThreeParams(
      final MapWithThreeParams<String, Integer, Boolean> mapWithThreeParams) {
    this.fieldValue.mapWithThreeParams = mapWithThreeParams;
    this.callSetterFor.mapWithThreeParams = true;
    return this;
  }

  /**
   * Sets the value for the {@code set} property.
   * To be more precise, this will lead to {@link ClassWithCollections#setSet(Set<List>)} being called on construction of the object.
   * @param set the value to set.
   * @return This builder for chained calls.
   */
  public ClassWithCollectionsBuilder set(final Set<List> set) {
    this.fieldValue.set = set;
    this.callSetterFor.set = true;
    return this;
  }

  /**
   * Sets the value for the {@code sortedSetWild} property.
   * To be more precise, this will lead to {@link ClassWithCollections#setSortedSetWild(SortedSet<?>)} being called on construction of the object.
   * @param sortedSetWild the value to set.
   * @return This builder for chained calls.
   */
  public ClassWithCollectionsBuilder sortedSetWild(final SortedSet<Object> sortedSetWild) {
    this.fieldValue.sortedSetWild = sortedSetWild;
    this.callSetterFor.sortedSetWild = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link ClassWithCollections}.
   * @return The constructed instance. Never {@code null}.
   */
  public ClassWithCollections build() {
    final ClassWithCollections objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.deque) {
      objectToBuild.setDeque(this.fieldValue.deque);
    }
    if (this.callSetterFor.floats) {
      objectToBuild.setFloats(this.fieldValue.floats);
    }
    if (this.callSetterFor.ints) {
      objectToBuild.setInts(this.fieldValue.ints);
    }
    if (this.callSetterFor.list) {
      objectToBuild.setList(this.fieldValue.list);
    }
    if (this.callSetterFor.listWithTwoParams) {
      objectToBuild.setListWithTwoParams(this.fieldValue.listWithTwoParams);
    }
    if (this.callSetterFor.map) {
      objectToBuild.setMap(this.fieldValue.map);
    }
    if (this.callSetterFor.mapNoTypeArgs) {
      objectToBuild.setMapNoTypeArgs(this.fieldValue.mapNoTypeArgs);
    }
    if (this.callSetterFor.mapTU) {
      objectToBuild.setMapTU(this.fieldValue.mapTU);
    }
    if (this.callSetterFor.mapWildObj) {
      objectToBuild.setMapWildObj(this.fieldValue.mapWildObj);
    }
    if (this.callSetterFor.mapWithThreeParams) {
      objectToBuild.setMapWithThreeParams(this.fieldValue.mapWithThreeParams);
    }
    if (this.callSetterFor.set) {
      objectToBuild.setSet(this.fieldValue.set);
    }
    if (this.callSetterFor.sortedSetWild) {
      objectToBuild.setSortedSetWild(this.fieldValue.sortedSetWild);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean deque;

    boolean floats;

    boolean ints;

    boolean list;

    boolean listWithTwoParams;

    boolean map;

    boolean mapNoTypeArgs;

    boolean mapTU;

    boolean mapWildObj;

    boolean mapWithThreeParams;

    boolean set;

    boolean sortedSetWild;
  }

  private class FieldValue {
    Deque<Object> deque;

    float[] floats;

    Collection<Integer> ints;

    List list;

    ListWithTwoParams<String, Integer> listWithTwoParams;

    Map<String, Object> map;

    Map mapNoTypeArgs;

    Map<T, U> mapTU;

    Map<Object, Object> mapWildObj;

    MapWithThreeParams<String, Integer, Boolean> mapWithThreeParams;

    Set<List> set;

    SortedSet<Object> sortedSetWild;
  }

  public class ArrayFloats {
    private List<Float> list;

    /**
     * Adds an item to the array property {@code floats}.
     * @param item The item to add to the array {@code floats}.
     * @return This builder for chained calls.
     */
    public ArrayFloats add(final float item) {
      if (this.list == null) {
        this.list = new ArrayList<>();
      }
      this.list.add(item);
      ClassWithCollectionsBuilder.this.callSetterFor.floats = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public ClassWithCollectionsBuilder and() {
      if (this.list != null) {
        ClassWithCollectionsBuilder.this.fieldValue.floats = new float[this.list.size()];
        for (int i = 0; i < this.list.size(); i++) {
          ClassWithCollectionsBuilder.this.fieldValue.floats[i] = this.list.get(i);
        }
      }
      return ClassWithCollectionsBuilder.this;
    }
  }

  public class CollectionDeque {
    /**
     * Adds an item to the collection property {@code deque}.
     * @param item The item to add to the collection {@code deque}.
     * @return This builder for chained calls.
     */
    public CollectionDeque add(final Object item) {
      if (ClassWithCollectionsBuilder.this.fieldValue.deque == null) {
        ClassWithCollectionsBuilder.this.fieldValue.deque = new ArrayDeque<>();
      }
      ClassWithCollectionsBuilder.this.fieldValue.deque.add(item);
      ClassWithCollectionsBuilder.this.callSetterFor.deque = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public ClassWithCollectionsBuilder and() {
      return ClassWithCollectionsBuilder.this;
    }
  }

  public class CollectionInts {
    /**
     * Adds an item to the collection property {@code ints}.
     * @param item The item to add to the collection {@code ints}.
     * @return This builder for chained calls.
     */
    public CollectionInts add(final Integer item) {
      if (ClassWithCollectionsBuilder.this.fieldValue.ints == null) {
        ClassWithCollectionsBuilder.this.fieldValue.ints = new ArrayList<>();
      }
      ClassWithCollectionsBuilder.this.fieldValue.ints.add(item);
      ClassWithCollectionsBuilder.this.callSetterFor.ints = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public ClassWithCollectionsBuilder and() {
      return ClassWithCollectionsBuilder.this;
    }
  }

  public class CollectionList {
    /**
     * Adds an item to the collection property {@code list}.
     * @param item The item to add to the collection {@code list}.
     * @return This builder for chained calls.
     */
    public CollectionList add(final Object item) {
      if (ClassWithCollectionsBuilder.this.fieldValue.list == null) {
        ClassWithCollectionsBuilder.this.fieldValue.list = new ArrayList<>();
      }
      ClassWithCollectionsBuilder.this.fieldValue.list.add(item);
      ClassWithCollectionsBuilder.this.callSetterFor.list = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public ClassWithCollectionsBuilder and() {
      return ClassWithCollectionsBuilder.this;
    }
  }

  public class CollectionSet {
    /**
     * Adds an item to the collection property {@code set}.
     * @param item The item to add to the collection {@code set}.
     * @return This builder for chained calls.
     */
    public CollectionSet add(final List item) {
      if (ClassWithCollectionsBuilder.this.fieldValue.set == null) {
        ClassWithCollectionsBuilder.this.fieldValue.set = new HashSet<>();
      }
      ClassWithCollectionsBuilder.this.fieldValue.set.add(item);
      ClassWithCollectionsBuilder.this.callSetterFor.set = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public ClassWithCollectionsBuilder and() {
      return ClassWithCollectionsBuilder.this;
    }
  }

  public class CollectionSortedSetWild {
    /**
     * Adds an item to the collection property {@code sortedSetWild}.
     * @param item The item to add to the collection {@code sortedSetWild}.
     * @return This builder for chained calls.
     */
    public CollectionSortedSetWild add(final Object item) {
      if (ClassWithCollectionsBuilder.this.fieldValue.sortedSetWild == null) {
        ClassWithCollectionsBuilder.this.fieldValue.sortedSetWild = new TreeSet<>();
      }
      ClassWithCollectionsBuilder.this.fieldValue.sortedSetWild.add(item);
      ClassWithCollectionsBuilder.this.callSetterFor.sortedSetWild = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public ClassWithCollectionsBuilder and() {
      return ClassWithCollectionsBuilder.this;
    }
  }

  public class MapMap {
    /**
     * Adds an entry to the map property {@code map}.
     * @param key The key of the entry to add to the map {@code map}.
     * @param value The value of the entry to add to the map {@code map}.
     * @return This builder for chained calls.
     */
    public MapMap put(final String key, final Object value) {
      if (ClassWithCollectionsBuilder.this.fieldValue.map == null) {
        ClassWithCollectionsBuilder.this.fieldValue.map = new HashMap<>();
      }
      ClassWithCollectionsBuilder.this.fieldValue.map.put(key, value);
      ClassWithCollectionsBuilder.this.callSetterFor.map = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public ClassWithCollectionsBuilder and() {
      return ClassWithCollectionsBuilder.this;
    }
  }

  public class MapMapNoTypeArgs {
    /**
     * Adds an entry to the map property {@code mapNoTypeArgs}.
     * @param key The key of the entry to add to the map {@code mapNoTypeArgs}.
     * @param value The value of the entry to add to the map {@code mapNoTypeArgs}.
     * @return This builder for chained calls.
     */
    public MapMapNoTypeArgs put(final Object key, final Object value) {
      if (ClassWithCollectionsBuilder.this.fieldValue.mapNoTypeArgs == null) {
        ClassWithCollectionsBuilder.this.fieldValue.mapNoTypeArgs = new HashMap<>();
      }
      ClassWithCollectionsBuilder.this.fieldValue.mapNoTypeArgs.put(key, value);
      ClassWithCollectionsBuilder.this.callSetterFor.mapNoTypeArgs = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public ClassWithCollectionsBuilder and() {
      return ClassWithCollectionsBuilder.this;
    }
  }

  public class MapMapTU {
    /**
     * Adds an entry to the map property {@code mapTU}.
     * @param key The key of the entry to add to the map {@code mapTU}.
     * @param value The value of the entry to add to the map {@code mapTU}.
     * @return This builder for chained calls.
     */
    public MapMapTU put(final T key, final U value) {
      if (ClassWithCollectionsBuilder.this.fieldValue.mapTU == null) {
        ClassWithCollectionsBuilder.this.fieldValue.mapTU = new HashMap<>();
      }
      ClassWithCollectionsBuilder.this.fieldValue.mapTU.put(key, value);
      ClassWithCollectionsBuilder.this.callSetterFor.mapTU = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public ClassWithCollectionsBuilder and() {
      return ClassWithCollectionsBuilder.this;
    }
  }

  public class MapMapWildObj {
    /**
     * Adds an entry to the map property {@code mapWildObj}.
     * @param key The key of the entry to add to the map {@code mapWildObj}.
     * @param value The value of the entry to add to the map {@code mapWildObj}.
     * @return This builder for chained calls.
     */
    public MapMapWildObj put(final Object key, final Object value) {
      if (ClassWithCollectionsBuilder.this.fieldValue.mapWildObj == null) {
        ClassWithCollectionsBuilder.this.fieldValue.mapWildObj = new HashMap<>();
      }
      ClassWithCollectionsBuilder.this.fieldValue.mapWildObj.put(key, value);
      ClassWithCollectionsBuilder.this.callSetterFor.mapWildObj = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public ClassWithCollectionsBuilder and() {
      return ClassWithCollectionsBuilder.this;
    }
  }
}
