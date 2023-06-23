package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.generics;

import java.lang.Number;
import java.lang.String;
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
public class GenericChildBuilder<S extends Number, T> {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private GenericChild objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private GenericChildBuilder(final GenericChild objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static GenericChildBuilder newInstance() {
    return new GenericChildBuilder(null);
  }

  public static GenericChildBuilder thatModifies(final GenericChild objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new GenericChildBuilder(objectToModify);
  }

  public CollectionList list() {
    return new CollectionList();
  }

  public MapMap map() {
    return new MapMap();
  }

  public GenericChildBuilder generic(final Generic<T> generic) {
    fieldValue.generic = generic;
    callSetterFor.generic = true;
    return this;
  }

  public GenericChildBuilder list(final List<String> list) {
    fieldValue.list = list;
    callSetterFor.list = true;
    return this;
  }

  public GenericChildBuilder map(final Map<S, T> map) {
    fieldValue.map = map;
    callSetterFor.map = true;
    return this;
  }

  public GenericChildBuilder otherGeneric(final Generic<String> otherGeneric) {
    fieldValue.otherGeneric = otherGeneric;
    callSetterFor.otherGeneric = true;
    return this;
  }

  public GenericChild build() {
    if (objectToBuild == null) {
      objectToBuild = new GenericChild();
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

    List<String> list;

    Map<S, T> map;

    Generic<String> otherGeneric;
  }

  public class CollectionList {
    public CollectionList add(final String item) {
      if (GenericChildBuilder.this.fieldValue.list == null) {
        GenericChildBuilder.this.fieldValue.list = new ArrayList<>();
      }
      GenericChildBuilder.this.fieldValue.list.add(item);
      GenericChildBuilder.this.callSetterFor.list = true;
      return this;
    }

    public GenericChildBuilder and() {
      return GenericChildBuilder.this;
    }
  }

  public class MapMap {
    public MapMap put(final S key, final T value) {
      if (GenericChildBuilder.this.fieldValue.map == null) {
        GenericChildBuilder.this.fieldValue.map = new HashMap<>();
      }
      GenericChildBuilder.this.fieldValue.map.put(key, value);
      GenericChildBuilder.this.callSetterFor.map = true;
      return this;
    }

    public GenericChildBuilder and() {
      return GenericChildBuilder.this;
    }
  }
}
