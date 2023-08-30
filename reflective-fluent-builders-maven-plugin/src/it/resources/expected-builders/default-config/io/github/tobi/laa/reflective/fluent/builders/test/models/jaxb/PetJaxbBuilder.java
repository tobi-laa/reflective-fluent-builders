package io.github.tobi.laa.reflective.fluent.builders.test.models.jaxb;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class PetJaxbBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private PetJaxb objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected PetJaxbBuilder(final PetJaxb objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  protected PetJaxbBuilder() {
    // noop
  }

  public static PetJaxbBuilder newInstance() {
    return new PetJaxbBuilder();
  }

  public static PetJaxbBuilder thatModifies(final PetJaxb objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new PetJaxbBuilder(objectToModify);
  }

  public CollectionSiblings siblings() {
    return new CollectionSiblings();
  }

  public PetJaxbBuilder fullName(final String fullName) {
    this.fieldValue.fullName = fullName;
    this.callSetterFor.fullName = true;
    return this;
  }

  public PetJaxbBuilder owner(final PersonJaxb owner) {
    this.fieldValue.owner = owner;
    this.callSetterFor.owner = true;
    return this;
  }

  public PetJaxbBuilder siblings(final List<PetJaxb> siblings) {
    this.fieldValue.siblings = siblings;
    this.callSetterFor.siblings = true;
    return this;
  }

  public PetJaxbBuilder weight(final float weight) {
    this.fieldValue.weight = weight;
    this.callSetterFor.weight = true;
    return this;
  }

  public PetJaxb build() {
    if (this.objectToBuild == null) {
      this.objectToBuild = new PetJaxb();
    }
    if (this.callSetterFor.fullName) {
      this.objectToBuild.setFullName(this.fieldValue.fullName);
    }
    if (this.callSetterFor.owner) {
      this.objectToBuild.setOwner(this.fieldValue.owner);
    }
    if (this.callSetterFor.siblings && this.fieldValue.siblings != null) {
      this.fieldValue.siblings.forEach(objectToBuild.getSiblings()::add);
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
