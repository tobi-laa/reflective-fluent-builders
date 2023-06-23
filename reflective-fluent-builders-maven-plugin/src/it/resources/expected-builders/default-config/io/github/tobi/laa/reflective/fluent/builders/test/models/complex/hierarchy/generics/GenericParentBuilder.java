package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.generics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class GenericParentBuilder<R, S, T> {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private GenericParent objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private GenericParentBuilder(final GenericParent objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static GenericParentBuilder newInstance() {
    return new GenericParentBuilder(null);
  }

  public static GenericParentBuilder thatModifies(final GenericParent objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new GenericParentBuilder(objectToModify);
  }

  public CollectionList list() {
    return new CollectionList();
  }

  public MapMap map() {
    return new MapMap();
  }

  public GenericParentBuilder generic(final Generic<T> generic) {
    fieldValue.generic = generic;
    callSetterFor.generic = true;
    return this;
  }

  public GenericParentBuilder list(final List<R> list) {
    fieldValue.list = list;
    callSetterFor.list = true;
    return this;
  }

  public GenericParentBuilder map(final Map<S, T> map) {
    fieldValue.map = map;
    callSetterFor.map = true;
    return this;
  }

  public GenericParentBuilder otherGeneric(final Generic<R> otherGeneric) {
    fieldValue.otherGeneric = otherGeneric;
    callSetterFor.otherGeneric = true;
    return this;
  }

  public GenericParent build() {
    if (objectToBuild == null) {
      objectToBuild = new GenericParent();
    }
    if (callSetterFor.generic) {
      objectToBuild.setGeneric(fieldValue.generic);
    }
    if (callSetterFor.list) {
      objectToBuild.setList(fieldValue.list);
    }
    if (callSetterFor.map) {
      objectToBuild.setMap(fieldValue.map);
    }
    if (callSetterFor.otherGeneric) {
      objectToBuild.setOtherGeneric(fieldValue.otherGeneric);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean generic;

    boolean list;

    boolean map;

    boolean otherGeneric;
  }

  private class FieldValue {
    Generic<T> generic;

    List<R> list;

    Map<S, T> map;

    Generic<R> otherGeneric;
  }

  public class CollectionList {
    public CollectionList add(final R item) {
      if (GenericParentBuilder.this.fieldValue.list == null) {
        GenericParentBuilder.this.fieldValue.list = new ArrayList<>();
      }
      GenericParentBuilder.this.fieldValue.list.add(item);
      GenericParentBuilder.this.callSetterFor.list = true;
      return this;
    }

    public GenericParentBuilder and() {
      return GenericParentBuilder.this;
    }
  }

  public class MapMap {
    public MapMap put(final S key, final T value) {
      if (GenericParentBuilder.this.fieldValue.map == null) {
        GenericParentBuilder.this.fieldValue.map = new HashMap<>();
      }
      GenericParentBuilder.this.fieldValue.map.put(key, value);
      GenericParentBuilder.this.callSetterFor.map = true;
      return this;
    }

    public GenericParentBuilder and() {
      return GenericParentBuilder.this;
    }
  }
}
