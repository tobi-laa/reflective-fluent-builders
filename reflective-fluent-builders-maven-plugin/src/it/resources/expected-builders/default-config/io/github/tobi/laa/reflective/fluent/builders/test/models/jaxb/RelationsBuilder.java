package io.github.tobi.laa.reflective.fluent.builders.test.models.jaxb;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class RelationsBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private PersonJaxb.Relations objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected RelationsBuilder(final PersonJaxb.Relations objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  protected RelationsBuilder() {
    // noop
  }

  public static RelationsBuilder newInstance() {
    return new RelationsBuilder();
  }

  public static RelationsBuilder thatModifies(final PersonJaxb.Relations objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new RelationsBuilder(objectToModify);
  }

  public CollectionEntry entry() {
    return new CollectionEntry();
  }

  public RelationsBuilder entry(final List<PersonJaxb.Relations.Entry> entry) {
    fieldValue.entry = entry;
    callSetterFor.entry = true;
    return this;
  }

  public PersonJaxb.Relations build() {
    if (objectToBuild == null) {
      objectToBuild = new PersonJaxb.Relations();
    }
    if (callSetterFor.entry && fieldValue.entry != null) {
      fieldValue.entry.forEach(objectToBuild.getEntry()::add);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean entry;
  }

  private class FieldValue {
    List<PersonJaxb.Relations.Entry> entry;
  }

  public class CollectionEntry {
    public CollectionEntry add(final PersonJaxb.Relations.Entry item) {
      if (RelationsBuilder.this.fieldValue.entry == null) {
        RelationsBuilder.this.fieldValue.entry = new ArrayList<>();
      }
      RelationsBuilder.this.fieldValue.entry.add(item);
      RelationsBuilder.this.callSetterFor.entry = true;
      return this;
    }

    public RelationsBuilder and() {
      return RelationsBuilder.this;
    }
  }
}
