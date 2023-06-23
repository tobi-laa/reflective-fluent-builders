package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import java.lang.Float;
import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
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
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ClassWithCollectionsBuilder<T, U> {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private ClassWithCollections objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private ClassWithCollectionsBuilder(final ClassWithCollections objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static ClassWithCollectionsBuilder newInstance() {
    return new ClassWithCollectionsBuilder(null);
  }

  public static ClassWithCollectionsBuilder thatModifies(
      final ClassWithCollections objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new ClassWithCollectionsBuilder(objectToModify);
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
    fieldValue.deque = deque;
    callSetterFor.deque = true;
    return this;
  }

  public ClassWithCollectionsBuilder floats(final float[] floats) {
    fieldValue.floats = floats;
    callSetterFor.floats = true;
    return this;
  }

  public ClassWithCollectionsBuilder ints(final Collection<Integer> ints) {
    fieldValue.ints = ints;
    callSetterFor.ints = true;
    return this;
  }

  public ClassWithCollectionsBuilder list(final List<Object> list) {
    fieldValue.list = list;
    callSetterFor.list = true;
    return this;
  }

  public ClassWithCollectionsBuilder map(final Map<String, Object> map) {
    fieldValue.map = map;
    callSetterFor.map = true;
    return this;
  }

  public ClassWithCollectionsBuilder mapNoTypeArgs(final Map<Object, Object> mapNoTypeArgs) {
    fieldValue.mapNoTypeArgs = mapNoTypeArgs;
    callSetterFor.mapNoTypeArgs = true;
    return this;
  }

  public ClassWithCollectionsBuilder mapTU(final Map<T, U> mapTU) {
    fieldValue.mapTU = mapTU;
    callSetterFor.mapTU = true;
    return this;
  }

  public ClassWithCollectionsBuilder mapWildObj(final Map<Object, Object> mapWildObj) {
    fieldValue.mapWildObj = mapWildObj;
    callSetterFor.mapWildObj = true;
    return this;
  }

  public ClassWithCollectionsBuilder set(final Set<List> set) {
    fieldValue.set = set;
    callSetterFor.set = true;
    return this;
  }

  public ClassWithCollectionsBuilder sortedSetWild(final SortedSet<Object> sortedSetWild) {
    fieldValue.sortedSetWild = sortedSetWild;
    callSetterFor.sortedSetWild = true;
    return this;
  }

  public ClassWithCollections build() {
    if (objectToBuild == null) {
      objectToBuild = new ClassWithCollections();
    }
    if (callSetterFor.deque) {
      objectToBuild.setDeque(fieldValue.deque);
    }
    if (callSetterFor.floats) {
      objectToBuild.setFloats(fieldValue.floats);
    }
    if (callSetterFor.ints) {
      objectToBuild.setInts(fieldValue.ints);
    }
    if (callSetterFor.list) {
      objectToBuild.setList(fieldValue.list);
    }
    if (callSetterFor.map) {
      objectToBuild.setMap(fieldValue.map);
    }
    if (callSetterFor.mapNoTypeArgs) {
      objectToBuild.setMapNoTypeArgs(fieldValue.mapNoTypeArgs);
    }
    if (callSetterFor.mapTU) {
      objectToBuild.setMapTU(fieldValue.mapTU);
    }
    if (callSetterFor.mapWildObj) {
      objectToBuild.setMapWildObj(fieldValue.mapWildObj);
    }
    if (callSetterFor.set) {
      objectToBuild.setSet(fieldValue.set);
    }
    if (callSetterFor.sortedSetWild) {
      objectToBuild.setSortedSetWild(fieldValue.sortedSetWild);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean deque;

    boolean floats;

    boolean ints;

    boolean list;

    boolean map;

    boolean mapNoTypeArgs;

    boolean mapTU;

    boolean mapWildObj;

    boolean set;

    boolean sortedSetWild;
  }

  private class FieldValue {
    Deque<Object> deque;

    float[] floats;

    Collection<Integer> ints;

    List<Object> list;

    Map<String, Object> map;

    Map<Object, Object> mapNoTypeArgs;

    Map<T, U> mapTU;

    Map<Object, Object> mapWildObj;

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
