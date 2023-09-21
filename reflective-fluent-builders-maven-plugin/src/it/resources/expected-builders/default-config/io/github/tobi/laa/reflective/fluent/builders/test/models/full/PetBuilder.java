package io.github.tobi.laa.reflective.fluent.builders.test.models.full;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

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

  private Supplier<Pet> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected PetBuilder(final Supplier<Pet> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static PetBuilder newInstance() {
    return new PetBuilder(Pet::new);
  }

  public static PetBuilder withSupplier(final Supplier<Pet> supplier) {
    return new PetBuilder(supplier);
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
    final Pet objectToBuild = objectSupplier.get();
    if (this.callSetterFor.fullName) {
      objectToBuild.setFullName(this.fieldValue.fullName);
    }
    if (this.callSetterFor.owner) {
      objectToBuild.setOwner(this.fieldValue.owner);
    }
    if (this.callSetterFor.siblings) {
      objectToBuild.setSiblings(this.fieldValue.siblings);
    }
    if (this.callSetterFor.weight) {
      objectToBuild.setWeight(this.fieldValue.weight);
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
