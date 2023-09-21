package io.github.tobi.laa.reflective.fluent.builders.test.models.jaxb;

import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class RelationsBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private Supplier<PersonJaxb.Relations> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected RelationsBuilder(final Supplier<PersonJaxb.Relations> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static RelationsBuilder newInstance() {
    return new RelationsBuilder(PersonJaxb.Relations::new);
  }

  public static RelationsBuilder withSupplier(final Supplier<PersonJaxb.Relations> supplier) {
    return new RelationsBuilder(supplier);
  }

  public CollectionEntry entry() {
    return new CollectionEntry();
  }

  public RelationsBuilder entry(final List<PersonJaxb.Relations.Entry> entry) {
    this.fieldValue.entry = entry;
    this.callSetterFor.entry = true;
    return this;
  }

  public PersonJaxb.Relations build() {
    final PersonJaxb.Relations objectToBuild = objectSupplier.get();
    if (this.callSetterFor.entry && this.fieldValue.entry != null) {
      this.fieldValue.entry.forEach(objectToBuild.getEntry()::add);
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
