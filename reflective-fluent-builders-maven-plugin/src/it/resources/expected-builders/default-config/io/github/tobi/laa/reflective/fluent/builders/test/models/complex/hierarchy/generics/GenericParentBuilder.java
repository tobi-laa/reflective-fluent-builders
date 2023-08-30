package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.generics;

import java.lang.SuppressWarnings;
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
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private GenericParent objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected GenericParentBuilder(final GenericParent objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  protected GenericParentBuilder() {
    // noop
  }

  public static GenericParentBuilder newInstance() {
    return new GenericParentBuilder();
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
    this.fieldValue.generic = generic;
    this.callSetterFor.generic = true;
    return this;
  }

  public GenericParentBuilder list(final List<R> list) {
    this.fieldValue.list = list;
    this.callSetterFor.list = true;
    return this;
  }

  public GenericParentBuilder map(final Map<S, T> map) {
    this.fieldValue.map = map;
    this.callSetterFor.map = true;
    return this;
  }

  public GenericParentBuilder otherGeneric(final Generic<R> otherGeneric) {
    this.fieldValue.otherGeneric = otherGeneric;
    this.callSetterFor.otherGeneric = true;
    return this;
  }

  public GenericParent build() {
    if (this.objectToBuild == null) {
      this.objectToBuild = new GenericParent();
    }
    if (this.callSetterFor.generic) {
      this.objectToBuild.setGeneric(this.fieldValue.generic);
    }
    if (this.callSetterFor.list) {
      this.objectToBuild.setList(this.fieldValue.list);
    }
    if (this.callSetterFor.map) {
      this.objectToBuild.setMap(this.fieldValue.map);
    }
    if (this.callSetterFor.otherGeneric) {
      this.objectToBuild.setOtherGeneric(this.fieldValue.otherGeneric);
    }
    return this.objectToBuild;
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
