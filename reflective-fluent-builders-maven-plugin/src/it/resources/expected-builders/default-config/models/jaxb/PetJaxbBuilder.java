package com.github.tobi.laa.reflective.fluent.builders.test.models.jaxb;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;
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

  public CollectionSiblings siblings() {
    return new CollectionSiblings();
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

  public PetJaxbBuilder siblings(final List<PetJaxb> siblings) {
    fieldValue.siblings = siblings;
    callSetterFor.siblings = true;
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
    if (callSetterFor.siblings && fieldValue.siblings != null) {
      fieldValue.siblings.forEach(objectToBuild.getSiblings()::add);
    }
    if (callSetterFor.weight) {
      objectToBuild.setWeight(fieldValue.weight);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean fullName;

    boolean owner;

    boolean siblings;

    boolean weight;
  }

  private class FieldValue {
    String fullName;

    PersonJaxb owner;

    List<PetJaxb> siblings;

    float weight;
  }

  public class CollectionSiblings {
    public CollectionSiblings add(final PetJaxb item) {
      if (PetJaxbBuilder.this.fieldValue.siblings == null) {
        PetJaxbBuilder.this.fieldValue.siblings = new ArrayList<>();
      }
      PetJaxbBuilder.this.fieldValue.siblings.add(item);
      PetJaxbBuilder.this.callSetterFor.siblings = true;
      return this;
    }

    public PetJaxbBuilder and() {
      return PetJaxbBuilder.this;
    }
  }
}
