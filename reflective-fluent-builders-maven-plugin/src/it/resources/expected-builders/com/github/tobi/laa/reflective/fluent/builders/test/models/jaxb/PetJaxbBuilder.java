package com.github.tobi.laa.reflective.fluent.builders.test.models.jaxb;

import java.lang.String;
import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "com.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class PetJaxbBuilder {
  private PetJaxb objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private PetJaxbBuilder(final PetJaxb objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static PetJaxbBuilder newInstance() {
    return new PetJaxbBuilder(null);
  }

  public static PetJaxbBuilder thatModifies(final PetJaxb objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new PetJaxbBuilder(objectToModify);
  }

  public PetJaxbBuilder fullName(final String fullName) {
    fieldValue.fullName = fullName;
    callSetterFor.fullName = true;
    return this;
  }

  public PetJaxbBuilder owner(final PersonJaxb owner) {
    fieldValue.owner = owner;
    callSetterFor.owner = true;
    return this;
  }

  public PetJaxbBuilder weight(final float weight) {
    fieldValue.weight = weight;
    callSetterFor.weight = true;
    return this;
  }

  public PetJaxb build() {
    if (objectToBuild == null) {
      objectToBuild = new PetJaxb();
    }
    if (callSetterFor.fullName) {
      objectToBuild.setFullName(fieldValue.fullName);
    }
    if (callSetterFor.owner) {
      objectToBuild.setOwner(fieldValue.owner);
    }
    if (callSetterFor.weight) {
      objectToBuild.setWeight(fieldValue.weight);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean fullName;

    boolean owner;

    boolean weight;
  }

  private class FieldValue {
    String fullName;

    PersonJaxb owner;

    float weight;
  }
}
