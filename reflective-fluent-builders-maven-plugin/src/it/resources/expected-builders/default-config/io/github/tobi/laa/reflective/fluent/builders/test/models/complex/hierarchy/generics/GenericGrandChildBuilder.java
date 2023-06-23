package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.generics;

import java.lang.Boolean;
import java.lang.Long;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class GenericGrandChildBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private GenericGrandChild objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private GenericGrandChildBuilder(final GenericGrandChild objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static GenericGrandChildBuilder newInstance() {
    return new GenericGrandChildBuilder(null);
  }

  public static GenericGrandChildBuilder thatModifies(final GenericGrandChild objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new GenericGrandChildBuilder(objectToModify);
  }

  public CollectionList list() {
    return new CollectionList();
  }

  public MapMap map() {
    return new MapMap();
  }

  public GenericGrandChildBuilder generic(final Generic<Boolean> generic) {
    fieldValue.generic = generic;
    callSetterFor.generic = true;
    return this;
  }

  public GenericGrandChildBuilder list(final List<String> list) {
    fieldValue.list = list;
    callSetterFor.list = true;
    return this;
  }

  public GenericGrandChildBuilder map(final Map<Long, Boolean> map) {
    fieldValue.map = map;
    callSetterFor.map = true;
    return this;
  }

  public GenericGrandChildBuilder otherGeneric(final Generic<String> otherGeneric) {
    fieldValue.otherGeneric = otherGeneric;
    callSetterFor.otherGeneric = true;
    return this;
  }

  public GenericGrandChild build() {
    if (objectToBuild == null) {
      objectToBuild = new GenericGrandChild();
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
    Generic<Boolean> generic;

    List<String> list;

    Map<Long, Boolean> map;

    Generic<String> otherGeneric;
  }

  public class CollectionList {
    public CollectionList add(final String item) {
      if (GenericGrandChildBuilder.this.fieldValue.list == null) {
        GenericGrandChildBuilder.this.fieldValue.list = new ArrayList<>();
      }
      GenericGrandChildBuilder.this.fieldValue.list.add(item);
      GenericGrandChildBuilder.this.callSetterFor.list = true;
      return this;
    }

    public GenericGrandChildBuilder and() {
      return GenericGrandChildBuilder.this;
    }
  }

  public class MapMap {
    public MapMap put(final Long key, final Boolean value) {
      if (GenericGrandChildBuilder.this.fieldValue.map == null) {
        GenericGrandChildBuilder.this.fieldValue.map = new HashMap<>();
      }
      GenericGrandChildBuilder.this.fieldValue.map.put(key, value);
      GenericGrandChildBuilder.this.callSetterFor.map = true;
      return this;
    }

    public GenericGrandChildBuilder and() {
      return GenericGrandChildBuilder.this;
    }
  }
}
