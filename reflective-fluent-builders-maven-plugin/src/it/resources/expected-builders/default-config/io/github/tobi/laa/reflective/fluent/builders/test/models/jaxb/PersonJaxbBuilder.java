package io.github.tobi.laa.reflective.fluent.builders.test.models.jaxb;

import java.lang.Object;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class PersonJaxbBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<PersonJaxb> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected PersonJaxbBuilder(final Supplier<PersonJaxb> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static PersonJaxbBuilder newInstance() {
    return new PersonJaxbBuilder(PersonJaxb::new);
  }

  public static PersonJaxbBuilder withSupplier(final Supplier<PersonJaxb> supplier) {
    return new PersonJaxbBuilder(supplier);
  }

  public CollectionAttributes attributes() {
    return new CollectionAttributes();
  }

  public CollectionNames names() {
    return new CollectionNames();
  }

  public CollectionPets pets() {
    return new CollectionPets();
  }

  public PersonJaxbBuilder age(final int age) {
    this.fieldValue.age = age;
    this.callSetterFor.age = true;
    return this;
  }

  public PersonJaxbBuilder attributes(final List<Object> attributes) {
    this.fieldValue.attributes = attributes;
    this.callSetterFor.attributes = true;
    return this;
  }

  public PersonJaxbBuilder married(final boolean married) {
    this.fieldValue.married = married;
    this.callSetterFor.married = true;
    return this;
  }

  public PersonJaxbBuilder names(final List<String> names) {
    this.fieldValue.names = names;
    this.callSetterFor.names = true;
    return this;
  }

  public PersonJaxbBuilder pets(final List<PetJaxb> pets) {
    this.fieldValue.pets = pets;
    this.callSetterFor.pets = true;
    return this;
  }

  public PersonJaxbBuilder relations(final PersonJaxb.Relations relations) {
    this.fieldValue.relations = relations;
    this.callSetterFor.relations = true;
    return this;
  }

  public PersonJaxb build() {
    final PersonJaxb objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.age) {
      objectToBuild.setAge(this.fieldValue.age);
    }
    if (this.callSetterFor.attributes && this.fieldValue.attributes != null) {
      this.fieldValue.attributes.forEach(objectToBuild.getAttributes()::add);
    }
    if (this.callSetterFor.married) {
      objectToBuild.setMarried(this.fieldValue.married);
    }
    if (this.callSetterFor.names && this.fieldValue.names != null) {
      this.fieldValue.names.forEach(objectToBuild.getNames()::add);
    }
    if (this.callSetterFor.pets && this.fieldValue.pets != null) {
      this.fieldValue.pets.forEach(objectToBuild.getPets()::add);
    }
    if (this.callSetterFor.relations) {
      objectToBuild.setRelations(this.fieldValue.relations);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean age;

    boolean attributes;

    boolean married;

    boolean names;

    boolean pets;

    boolean relations;
  }

  private class FieldValue {
    int age;

    List<Object> attributes;

    boolean married;

    List<String> names;

    List<PetJaxb> pets;

    PersonJaxb.Relations relations;
  }

  public class CollectionAttributes {
    public CollectionAttributes add(final Object item) {
      if (PersonJaxbBuilder.this.fieldValue.attributes == null) {
        PersonJaxbBuilder.this.fieldValue.attributes = new ArrayList<>();
      }
      PersonJaxbBuilder.this.fieldValue.attributes.add(item);
      PersonJaxbBuilder.this.callSetterFor.attributes = true;
      return this;
    }

    public PersonJaxbBuilder and() {
      return PersonJaxbBuilder.this;
    }
  }

  public class CollectionNames {
    public CollectionNames add(final String item) {
      if (PersonJaxbBuilder.this.fieldValue.names == null) {
        PersonJaxbBuilder.this.fieldValue.names = new ArrayList<>();
      }
      PersonJaxbBuilder.this.fieldValue.names.add(item);
      PersonJaxbBuilder.this.callSetterFor.names = true;
      return this;
    }

    public PersonJaxbBuilder and() {
      return PersonJaxbBuilder.this;
    }
  }

  public class CollectionPets {
    public CollectionPets add(final PetJaxb item) {
      if (PersonJaxbBuilder.this.fieldValue.pets == null) {
        PersonJaxbBuilder.this.fieldValue.pets = new ArrayList<>();
      }
      PersonJaxbBuilder.this.fieldValue.pets.add(item);
      PersonJaxbBuilder.this.callSetterFor.pets = true;
      return this;
    }

    public PersonJaxbBuilder and() {
      return PersonJaxbBuilder.this;
    }
  }

  @Generated(
      value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
      date = "3333-03-13T00:00Z[UTC]"
  )
  public static class RelationsBuilder {
    /**
     * This field is solely used to be able to detect generated builders via reflection at a later stage.
     */
    @SuppressWarnings("unused")
    private boolean ______generatedByReflectiveFluentBuildersGenerator;

    private final Supplier<PersonJaxb.Relations> objectSupplier;

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
      final PersonJaxb.Relations objectToBuild = this.objectSupplier.get();
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

    @Generated(
        value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
        date = "3333-03-13T00:00Z[UTC]"
    )
    public static class EntryBuilder {
      /**
       * This field is solely used to be able to detect generated builders via reflection at a later stage.
       */
      @SuppressWarnings("unused")
      private boolean ______generatedByReflectiveFluentBuildersGenerator;

      private final Supplier<PersonJaxb.Relations.Entry> objectSupplier;

      private final CallSetterFor callSetterFor = new CallSetterFor();

      private final FieldValue fieldValue = new FieldValue();

      protected EntryBuilder(final Supplier<PersonJaxb.Relations.Entry> objectSupplier) {
        this.objectSupplier = Objects.requireNonNull(objectSupplier);
      }

      public static EntryBuilder newInstance() {
        return new EntryBuilder(PersonJaxb.Relations.Entry::new);
      }

      public static EntryBuilder withSupplier(final Supplier<PersonJaxb.Relations.Entry> supplier) {
        return new EntryBuilder(supplier);
      }

      public EntryBuilder key(final String key) {
        this.fieldValue.key = key;
        this.callSetterFor.key = true;
        return this;
      }

      public EntryBuilder value(final PersonJaxb value) {
        this.fieldValue.value = value;
        this.callSetterFor.value = true;
        return this;
      }

      public PersonJaxb.Relations.Entry build() {
        final PersonJaxb.Relations.Entry objectToBuild = this.objectSupplier.get();
        if (this.callSetterFor.key) {
          objectToBuild.setKey(this.fieldValue.key);
        }
        if (this.callSetterFor.value) {
          objectToBuild.setValue(this.fieldValue.value);
        }
        return objectToBuild;
      }

      private class CallSetterFor {
        boolean key;

        boolean value;
      }

      private class FieldValue {
        String key;

        PersonJaxb value;
      }
    }
  }
}
