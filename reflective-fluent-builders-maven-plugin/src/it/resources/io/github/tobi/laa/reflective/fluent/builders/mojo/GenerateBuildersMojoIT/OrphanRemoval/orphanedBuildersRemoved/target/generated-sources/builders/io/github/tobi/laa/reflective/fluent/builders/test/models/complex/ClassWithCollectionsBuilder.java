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

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ClassWithCollectionsBuilder<T, U> {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<ClassWithCollections> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected ClassWithCollectionsBuilder(final Supplier<ClassWithCollections> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static ClassWithCollectionsBuilder newInstance() {
    return new ClassWithCollectionsBuilder(ClassWithCollections::new);
  }

  public static ClassWithCollectionsBuilder withSupplier(
      final Supplier<ClassWithCollections> supplier) {
    return new ClassWithCollectionsBuilder(supplier);
  }

  public ArrayFloats floats() {
    return new ArrayFloats();
  }

  public CollectionDeque deque() {
    return new CollectionDeque();
  }

  public CollectionInts ints() {
    return new CollectionInts();
  }

  public CollectionList list() {
    return new CollectionList();
  }

  public CollectionSet set() {
    return new CollectionSet();
  }

  public CollectionSortedSetWild sortedSetWild() {
    return new CollectionSortedSetWild();
  }

  public MapMap map() {
    return new MapMap();
  }

  public MapMapNoTypeArgs mapNoTypeArgs() {
    return new MapMapNoTypeArgs();
  }

  public MapMapTU mapTU() {
    return new MapMapTU();
  }

  public MapMapWildObj mapWildObj() {
    return new MapMapWildObj();
  }

  public ClassWithCollectionsBuilder deque(final Deque<Object> deque) {
    this.fieldValue.deque = deque;
    this.callSetterFor.deque = true;
    return this;
  }

  public ClassWithCollectionsBuilder floats(final float[] floats) {
    this.fieldValue.floats = floats;
    this.callSetterFor.floats = true;
    return this;
  }

  public ClassWithCollectionsBuilder ints(final Collection<Integer> ints) {
    this.fieldValue.ints = ints;
    this.callSetterFor.ints = true;
    return this;
  }

  public ClassWithCollectionsBuilder list(final List list) {
    this.fieldValue.list = list;
    this.callSetterFor.list = true;
    return this;
  }

  public ClassWithCollectionsBuilder listWithTwoParams(
      final ListWithTwoParams<String, Integer> listWithTwoParams) {
    this.fieldValue.listWithTwoParams = listWithTwoParams;
    this.callSetterFor.listWithTwoParams = true;
    return this;
  }

  public ClassWithCollectionsBuilder map(final Map<String, Object> map) {
    this.fieldValue.map = map;
    this.callSetterFor.map = true;
    return this;
  }

  public ClassWithCollectionsBuilder mapNoTypeArgs(final Map mapNoTypeArgs) {
    this.fieldValue.mapNoTypeArgs = mapNoTypeArgs;
    this.callSetterFor.mapNoTypeArgs = true;
    return this;
  }

  public ClassWithCollectionsBuilder mapTU(final Map<T, U> mapTU) {
    this.fieldValue.mapTU = mapTU;
    this.callSetterFor.mapTU = true;
    return this;
  }

  public ClassWithCollectionsBuilder mapWildObj(final Map<Object, Object> mapWildObj) {
    this.fieldValue.mapWildObj = mapWildObj;
    this.callSetterFor.mapWildObj = true;
    return this;
  }

  public ClassWithCollectionsBuilder mapWithThreeParams(
      final MapWithThreeParams<String, Integer, Boolean> mapWithThreeParams) {
    this.fieldValue.mapWithThreeParams = mapWithThreeParams;
    this.callSetterFor.mapWithThreeParams = true;
    return this;
  }

  public ClassWithCollectionsBuilder set(final Set<List> set) {
    this.fieldValue.set = set;
    this.callSetterFor.set = true;
    return this;
  }

  public ClassWithCollectionsBuilder sortedSetWild(final SortedSet<Object> sortedSetWild) {
    this.fieldValue.sortedSetWild = sortedSetWild;
    this.callSetterFor.sortedSetWild = true;
    return this;
  }

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

    public ArrayFloats add(final float item) {
      if (this.list == null) {
        this.list = new ArrayList<>();
      }
      this.list.add(item);
      ClassWithCollectionsBuilder.this.callSetterFor.floats = true;
      return this;
    }

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
    public CollectionDeque add(final Object item) {
      if (ClassWithCollectionsBuilder.this.fieldValue.deque == null) {
        ClassWithCollectionsBuilder.this.fieldValue.deque = new ArrayDeque<>();
      }
      ClassWithCollectionsBuilder.this.fieldValue.deque.add(item);
      ClassWithCollectionsBuilder.this.callSetterFor.deque = true;
      return this;
    }

    public ClassWithCollectionsBuilder and() {
      return ClassWithCollectionsBuilder.this;
    }
  }

  public class CollectionInts {
    public CollectionInts add(final Integer item) {
      if (ClassWithCollectionsBuilder.this.fieldValue.ints == null) {
        ClassWithCollectionsBuilder.this.fieldValue.ints = new ArrayList<>();
      }
      ClassWithCollectionsBuilder.this.fieldValue.ints.add(item);
      ClassWithCollectionsBuilder.this.callSetterFor.ints = true;
      return this;
    }

    public ClassWithCollectionsBuilder and() {
      return ClassWithCollectionsBuilder.this;
    }
  }

  public class CollectionList {
    public CollectionList add(final Object item) {
      if (ClassWithCollectionsBuilder.this.fieldValue.list == null) {
        ClassWithCollectionsBuilder.this.fieldValue.list = new ArrayList<>();
      }
      ClassWithCollectionsBuilder.this.fieldValue.list.add(item);
      ClassWithCollectionsBuilder.this.callSetterFor.list = true;
      return this;
    }

    public ClassWithCollectionsBuilder and() {
      return ClassWithCollectionsBuilder.this;
    }
  }

  public class CollectionSet {
    public CollectionSet add(final List item) {
      if (ClassWithCollectionsBuilder.this.fieldValue.set == null) {
        ClassWithCollectionsBuilder.this.fieldValue.set = new HashSet<>();
      }
      ClassWithCollectionsBuilder.this.fieldValue.set.add(item);
      ClassWithCollectionsBuilder.this.callSetterFor.set = true;
      return this;
    }

    public ClassWithCollectionsBuilder and() {
      return ClassWithCollectionsBuilder.this;
    }
  }

  public class CollectionSortedSetWild {
    public CollectionSortedSetWild add(final Object item) {
      if (ClassWithCollectionsBuilder.this.fieldValue.sortedSetWild == null) {
        ClassWithCollectionsBuilder.this.fieldValue.sortedSetWild = new TreeSet<>();
      }
      ClassWithCollectionsBuilder.this.fieldValue.sortedSetWild.add(item);
      ClassWithCollectionsBuilder.this.callSetterFor.sortedSetWild = true;
      return this;
    }

    public ClassWithCollectionsBuilder and() {
      return ClassWithCollectionsBuilder.this;
    }
  }

  public class MapMap {
    public MapMap put(final String key, final Object value) {
      if (ClassWithCollectionsBuilder.this.fieldValue.map == null) {
        ClassWithCollectionsBuilder.this.fieldValue.map = new HashMap<>();
      }
      ClassWithCollectionsBuilder.this.fieldValue.map.put(key, value);
      ClassWithCollectionsBuilder.this.callSetterFor.map = true;
      return this;
    }

    public ClassWithCollectionsBuilder and() {
      return ClassWithCollectionsBuilder.this;
    }
  }

  public class MapMapNoTypeArgs {
    public MapMapNoTypeArgs put(final Object key, final Object value) {
      if (ClassWithCollectionsBuilder.this.fieldValue.mapNoTypeArgs == null) {
        ClassWithCollectionsBuilder.this.fieldValue.mapNoTypeArgs = new HashMap<>();
      }
      ClassWithCollectionsBuilder.this.fieldValue.mapNoTypeArgs.put(key, value);
      ClassWithCollectionsBuilder.this.callSetterFor.mapNoTypeArgs = true;
      return this;
    }

    public ClassWithCollectionsBuilder and() {
      return ClassWithCollectionsBuilder.this;
    }
  }

  public class MapMapTU {
    public MapMapTU put(final T key, final U value) {
      if (ClassWithCollectionsBuilder.this.fieldValue.mapTU == null) {
        ClassWithCollectionsBuilder.this.fieldValue.mapTU = new HashMap<>();
      }
      ClassWithCollectionsBuilder.this.fieldValue.mapTU.put(key, value);
      ClassWithCollectionsBuilder.this.callSetterFor.mapTU = true;
      return this;
    }

    public ClassWithCollectionsBuilder and() {
      return ClassWithCollectionsBuilder.this;
    }
  }

  public class MapMapWildObj {
    public MapMapWildObj put(final Object key, final Object value) {
      if (ClassWithCollectionsBuilder.this.fieldValue.mapWildObj == null) {
        ClassWithCollectionsBuilder.this.fieldValue.mapWildObj = new HashMap<>();
      }
      ClassWithCollectionsBuilder.this.fieldValue.mapWildObj.put(key, value);
      ClassWithCollectionsBuilder.this.callSetterFor.mapWildObj = true;
      return this;
    }

    public ClassWithCollectionsBuilder and() {
      return ClassWithCollectionsBuilder.this;
    }
  }
}
