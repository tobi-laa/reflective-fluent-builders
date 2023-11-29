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

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class GenericGrandChildBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<GenericGrandChild> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected GenericGrandChildBuilder(final Supplier<GenericGrandChild> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static GenericGrandChildBuilder newInstance() {
    return new GenericGrandChildBuilder(GenericGrandChild::new);
  }

  public static GenericGrandChildBuilder withSupplier(final Supplier<GenericGrandChild> supplier) {
    return new GenericGrandChildBuilder(supplier);
  }

  public CollectionList list() {
    return new CollectionList();
  }

  public MapMap map() {
    return new MapMap();
  }

  public GenericGrandChildBuilder generic(final Generic<Boolean> generic) {
    this.fieldValue.generic = generic;
    this.callSetterFor.generic = true;
    return this;
  }

  public GenericGrandChildBuilder list(final List<String> list) {
    this.fieldValue.list = list;
    this.callSetterFor.list = true;
    return this;
  }

  public GenericGrandChildBuilder map(final Map<Long, Boolean> map) {
    this.fieldValue.map = map;
    this.callSetterFor.map = true;
    return this;
  }

  public GenericGrandChildBuilder otherGeneric(final Generic<String> otherGeneric) {
    this.fieldValue.otherGeneric = otherGeneric;
    this.callSetterFor.otherGeneric = true;
    return this;
  }

  public GenericGrandChild build() {
    final GenericGrandChild objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.generic) {
      objectToBuild.setGeneric(this.fieldValue.generic);
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
