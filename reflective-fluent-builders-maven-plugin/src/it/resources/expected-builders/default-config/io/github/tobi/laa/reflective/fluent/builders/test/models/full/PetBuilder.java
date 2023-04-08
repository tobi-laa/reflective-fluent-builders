package io.github.tobi.laa.reflective.fluent.builders.test.models.full;

import java.lang.String;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class PetBuilder {
  private Pet objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private PetBuilder(final Pet objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static PetBuilder newInstance() {
    return new PetBuilder(null);
  }

  public static PetBuilder thatModifies(final Pet objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new PetBuilder(objectToModify);
  }

  public CollectionSiblings siblings() {
    return new CollectionSiblings();
  }

  public PetBuilder fullName(final String fullName) {
    fieldValue.fullName = fullName;
    callSetterFor.fullName = true;
    return this;
  }

  public PetBuilder owner(final Person owner) {
    fieldValue.owner = owner;
    callSetterFor.owner = true;
    return this;
  }

  public PetBuilder siblings(final SortedSet<Pet> siblings) {
    fieldValue.siblings = siblings;
    callSetterFor.siblings = true;
    return this;
  }

  public PetBuilder weight(final float weight) {
    fieldValue.weight = weight;
    callSetterFor.weight = true;
    return this;
  }

  public Pet build() {
    if (objectToBuild == null) {
      objectToBuild = new Pet();
    }
    if (callSetterFor.fullName) {
      objectToBuild.setFullName(fieldValue.fullName);
    }
    if (callSetterFor.owner) {
      objectToBuild.setOwner(fieldValue.owner);
    }
    if (callSetterFor.siblings) {
      objectToBuild.setSiblings(fieldValue.siblings);
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

    Person owner;

    SortedSet<Pet> siblings;

    float weight;
  }

  public class CollectionSiblings {
    public CollectionSiblings add(final Pet item) {
      if (PetBuilder.this.fieldValue.siblings == null) {
        PetBuilder.this.fieldValue.siblings = new TreeSet<>();
      }
      PetBuilder.this.fieldValue.siblings.add(item);
      PetBuilder.this.callSetterFor.siblings = true;
      return this;
    }

    public PetBuilder and() {
      return PetBuilder.this;
    }
  }
}
