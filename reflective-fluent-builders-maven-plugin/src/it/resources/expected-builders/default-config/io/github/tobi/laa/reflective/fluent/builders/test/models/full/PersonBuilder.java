package io.github.tobi.laa.reflective.fluent.builders.test.models.full;

import java.lang.Object;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class PersonBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<Person> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected PersonBuilder(final Supplier<Person> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static PersonBuilder newInstance() {
    return new PersonBuilder(Person::new);
  }

  public static PersonBuilder withSupplier(final Supplier<Person> supplier) {
    return new PersonBuilder(supplier);
  }

  public ArrayNames names() {
    return new ArrayNames();
  }

  public CollectionAttributes attributes() {
    return new CollectionAttributes();
  }

  public CollectionPets pets() {
    return new CollectionPets();
  }

  public MapRelations relations() {
    return new MapRelations();
  }

  public PersonBuilder age(final int age) {
    this.fieldValue.age = age;
    this.callSetterFor.age = true;
    return this;
  }

  public PersonBuilder attributes(final List attributes) {
    this.fieldValue.attributes = attributes;
    this.callSetterFor.attributes = true;
    return this;
  }

  public PersonBuilder married(final boolean married) {
    this.fieldValue.married = married;
    this.callSetterFor.married = true;
    return this;
  }

  public PersonBuilder married(final String married0) {
    this.fieldValue.married0 = married0;
    this.callSetterFor.married0 = true;
    return this;
  }

  public PersonBuilder names(final String[] names) {
    this.fieldValue.names = names;
    this.callSetterFor.names = true;
    return this;
  }

  public PersonBuilder pets(final Set<Pet> pets) {
    this.fieldValue.pets = pets;
    this.callSetterFor.pets = true;
    return this;
  }

  public PersonBuilder relations(final Map<String, Person> relations) {
    this.fieldValue.relations = relations;
    this.callSetterFor.relations = true;
    return this;
  }

  public Person build() {
    final Person objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.age) {
      objectToBuild.setAge(this.fieldValue.age);
    }
    if (this.callSetterFor.attributes) {
      objectToBuild.setAttributes(this.fieldValue.attributes);
    }
    if (this.callSetterFor.married) {
      objectToBuild.setMarried(this.fieldValue.married);
    }
    if (this.callSetterFor.married0) {
      objectToBuild.setMarried(this.fieldValue.married0);
    }
    if (this.callSetterFor.names) {
      objectToBuild.setNames(this.fieldValue.names);
    }
    if (this.callSetterFor.pets) {
      objectToBuild.setPets(this.fieldValue.pets);
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

    boolean married0;

    boolean names;

    boolean pets;

    boolean relations;
  }

  private class FieldValue {
    int age;

    List attributes;

    boolean married;

    String married0;

    String[] names;

    Set<Pet> pets;

    Map<String, Person> relations;
  }

  public class ArrayNames {
    private List<String> list;

    public ArrayNames add(final String item) {
      if (this.list == null) {
        this.list = new ArrayList<>();
      }
      this.list.add(item);
      PersonBuilder.this.callSetterFor.names = true;
      return this;
    }

    public PersonBuilder and() {
      if (this.list != null) {
        PersonBuilder.this.fieldValue.names = new String[this.list.size()];
        for (int i = 0; i < this.list.size(); i++) {
          PersonBuilder.this.fieldValue.names[i] = this.list.get(i);
        }
      }
      return PersonBuilder.this;
    }
  }

  public class CollectionAttributes {
    public CollectionAttributes add(final Object item) {
      if (PersonBuilder.this.fieldValue.attributes == null) {
        PersonBuilder.this.fieldValue.attributes = new ArrayList<>();
      }
      PersonBuilder.this.fieldValue.attributes.add(item);
      PersonBuilder.this.callSetterFor.attributes = true;
      return this;
    }

    public PersonBuilder and() {
      return PersonBuilder.this;
    }
  }

  public class CollectionPets {
    public CollectionPets add(final Pet item) {
      if (PersonBuilder.this.fieldValue.pets == null) {
        PersonBuilder.this.fieldValue.pets = new HashSet<>();
      }
      PersonBuilder.this.fieldValue.pets.add(item);
      PersonBuilder.this.callSetterFor.pets = true;
      return this;
    }

    public PersonBuilder and() {
      return PersonBuilder.this;
    }
  }

  public class MapRelations {
    public MapRelations put(final String key, final Person value) {
      if (PersonBuilder.this.fieldValue.relations == null) {
        PersonBuilder.this.fieldValue.relations = new HashMap<>();
      }
      PersonBuilder.this.fieldValue.relations.put(key, value);
      PersonBuilder.this.callSetterFor.relations = true;
      return this;
    }

    public PersonBuilder and() {
      return PersonBuilder.this;
    }
  }
}
