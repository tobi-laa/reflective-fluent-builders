package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.generics;

import java.lang.Number;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class GenericChildBuilder<S extends Number, T> {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private Supplier<GenericChild> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected GenericChildBuilder(final Supplier<GenericChild> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static GenericChildBuilder newInstance() {
    return new GenericChildBuilder(GenericChild::new);
  }

  public static GenericChildBuilder withSupplier(final Supplier<GenericChild> supplier) {
    return new GenericChildBuilder(supplier);
  }

  public CollectionList list() {
    return new CollectionList();
  }

  public MapMap map() {
    return new MapMap();
  }

  public GenericChildBuilder generic(final Generic<T> generic) {
    this.fieldValue.generic = generic;
    this.callSetterFor.generic = true;
    return this;
  }

  public GenericChildBuilder list(final List<String> list) {
    this.fieldValue.list = list;
    this.callSetterFor.list = true;
    return this;
  }

  public GenericChildBuilder map(final Map<S, T> map) {
    this.fieldValue.map = map;
    this.callSetterFor.map = true;
    return this;
  }

  public GenericChildBuilder otherGeneric(final Generic<String> otherGeneric) {
    this.fieldValue.otherGeneric = otherGeneric;
    this.callSetterFor.otherGeneric = true;
    return this;
  }

  public GenericChild build() {
    final GenericChild objectToBuild = objectSupplier.get();
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
