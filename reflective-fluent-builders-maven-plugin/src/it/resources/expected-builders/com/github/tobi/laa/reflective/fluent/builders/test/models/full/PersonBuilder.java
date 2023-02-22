package com.github.tobi.laa.reflective.fluent.builders.test.models.full;

import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "com.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class PersonBuilder {
  private Person objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private PersonBuilder(final Person objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static PersonBuilder newInstance() {
    return new PersonBuilder(null);
  }

  public static PersonBuilder thatModifies(final Person objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new PersonBuilder(objectToModify);
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
    fieldValue.age = age;
    callSetterFor.age = true;
    return this;
  }

  public PersonBuilder attributes(final List<Object> attributes) {
    fieldValue.attributes = attributes;
    callSetterFor.attributes = true;
    return this;
  }

  public PersonBuilder married(final boolean married) {
    fieldValue.married = married;
    callSetterFor.married = true;
    return this;
  }

  public PersonBuilder married(final String married0) {
    fieldValue.married0 = married0;
    callSetterFor.married0 = true;
    return this;
  }

  public PersonBuilder names(final String[] names) {
    fieldValue.names = names;
    callSetterFor.names = true;
    return this;
  }

  public PersonBuilder pets(final Set<Pet> pets) {
    fieldValue.pets = pets;
    callSetterFor.pets = true;
    return this;
  }

  public PersonBuilder relations(final Map<String, Person> relations) {
    fieldValue.relations = relations;
    callSetterFor.relations = true;
    return this;
  }

  public Person build() {
    if (objectToBuild == null) {
      objectToBuild = new Person();
    }
    if (callSetterFor.age) {
      objectToBuild.setAge(fieldValue.age);
    }
    if (callSetterFor.attributes) {
      objectToBuild.setAttributes(fieldValue.attributes);
    }
    if (callSetterFor.married) {
      objectToBuild.setMarried(fieldValue.married);
    }
    if (callSetterFor.married0) {
      objectToBuild.setMarried(fieldValue.married0);
    }
    if (callSetterFor.names) {
      objectToBuild.setNames(fieldValue.names);
    }
    if (callSetterFor.pets) {
      objectToBuild.setPets(fieldValue.pets);
    }
    if (callSetterFor.relations) {
      objectToBuild.setRelations(fieldValue.relations);
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

    List<Object> attributes;

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
