package com.github.tobi.laa.reflective.fluent.builders.test.models.jaxb;

import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "com.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class PersonJaxbBuilder {
  private PersonJaxb objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private PersonJaxbBuilder(final PersonJaxb objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static PersonJaxbBuilder newInstance() {
    return new PersonJaxbBuilder(null);
  }

  public static PersonJaxbBuilder thatModifies(final PersonJaxb objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new PersonJaxbBuilder(objectToModify);
  }

  public PersonJaxbBuilder age(final int age) {
    fieldValue.age = age;
    callSetterFor.age = true;
    return this;
  }

  public PersonJaxbBuilder married(final boolean married) {
    fieldValue.married = married;
    callSetterFor.married = true;
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
    if (callSetterFor.married) {
      objectToBuild.setMarried(fieldValue.married);
    }
    if (callSetterFor.relations) {
      objectToBuild.setRelations(fieldValue.relations);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean age;

    boolean married;

    boolean relations;
  }

  private class FieldValue {
    int age;

    boolean married;

    PersonJaxb.Relations relations;
  }
}
