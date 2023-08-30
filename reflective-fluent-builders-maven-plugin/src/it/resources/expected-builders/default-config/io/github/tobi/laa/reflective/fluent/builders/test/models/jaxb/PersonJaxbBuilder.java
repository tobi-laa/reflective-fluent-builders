package io.github.tobi.laa.reflective.fluent.builders.test.models.jaxb;

import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class PersonJaxbBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private PersonJaxb objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected PersonJaxbBuilder(final PersonJaxb objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  protected PersonJaxbBuilder() {
    // noop
  }

  public static PersonJaxbBuilder newInstance() {
    return new PersonJaxbBuilder();
  }

  public static PersonJaxbBuilder thatModifies(final PersonJaxb objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new PersonJaxbBuilder(objectToModify);
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
    fieldValue.age = age;
    callSetterFor.age = true;
    return this;
  }

  public PersonJaxbBuilder attributes(final List<Object> attributes) {
    fieldValue.attributes = attributes;
    callSetterFor.attributes = true;
    return this;
  }

  public PersonJaxbBuilder married(final boolean married) {
    fieldValue.married = married;
    callSetterFor.married = true;
    return this;
  }

  public PersonJaxbBuilder names(final List<String> names) {
    fieldValue.names = names;
    callSetterFor.names = true;
    return this;
  }

  public PersonJaxbBuilder pets(final List<PetJaxb> pets) {
    fieldValue.pets = pets;
    callSetterFor.pets = true;
    return this;
  }

  public PersonJaxbBuilder relations(final PersonJaxb.Relations relations) {
    fieldValue.relations = relations;
    callSetterFor.relations = true;
    return this;
  }

  public PersonJaxb build() {
    if (objectToBuild == null) {
      objectToBuild = new PersonJaxb();
    }
    if (callSetterFor.age) {
      objectToBuild.setAge(fieldValue.age);
    }
    if (callSetterFor.attributes && fieldValue.attributes != null) {
      fieldValue.attributes.forEach(objectToBuild.getAttributes()::add);
    }
    if (callSetterFor.married) {
      objectToBuild.setMarried(fieldValue.married);
    }
    if (callSetterFor.names && fieldValue.names != null) {
      fieldValue.names.forEach(objectToBuild.getNames()::add);
    }
    if (callSetterFor.pets && fieldValue.pets != null) {
      fieldValue.pets.forEach(objectToBuild.getPets()::add);
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
}
