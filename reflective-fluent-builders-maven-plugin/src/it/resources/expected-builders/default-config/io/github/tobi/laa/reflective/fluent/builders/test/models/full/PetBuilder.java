package io.github.tobi.laa.reflective.fluent.builders.test.models.full;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class PetBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private Pet objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected PetBuilder(final Pet objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  protected PetBuilder() {
    // noop
  }

  public static PetBuilder newInstance() {
    return new PetBuilder();
  }

  public static PetBuilder thatModifies(final Pet objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new PetBuilder(objectToModify);
  }

  public CollectionSiblings siblings() {
    return new CollectionSiblings();
  }

  public PetBuilder fullName(final String fullName) {
    this.fieldValue.fullName = fullName;
    this.callSetterFor.fullName = true;
    return this;
  }

  public PetBuilder owner(final Person owner) {
    this.fieldValue.owner = owner;
    this.callSetterFor.owner = true;
    return this;
  }

  public PetBuilder siblings(final SortedSet<Pet> siblings) {
    this.fieldValue.siblings = siblings;
    this.callSetterFor.siblings = true;
    return this;
  }

  public PetBuilder weight(final float weight) {
    this.fieldValue.weight = weight;
    this.callSetterFor.weight = true;
    return this;
  }

  public Pet build() {
    if (this.objectToBuild == null) {
      this.objectToBuild = new Pet();
    }
    if (this.callSetterFor.fullName) {
      this.objectToBuild.setFullName(this.fieldValue.fullName);
    }
    if (this.callSetterFor.owner) {
      this.objectToBuild.setOwner(this.fieldValue.owner);
    }
    if (this.callSetterFor.siblings) {
      this.objectToBuild.setSiblings(this.fieldValue.siblings);
    }
    if (this.callSetterFor.weight) {
      this.objectToBuild.setWeight(this.fieldValue.weight);
    }
    return this.objectToBuild;
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
