package io.github.tobi.laa.reflective.fluent.builders.test.models.jaxb;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
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

  private final Supplier<PetJaxb> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected PetJaxbBuilder(final Supplier<PetJaxb> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static PetJaxbBuilder newInstance() {
    return new PetJaxbBuilder(PetJaxb::new);
  }

  public static PetJaxbBuilder withSupplier(final Supplier<PetJaxb> supplier) {
    return new PetJaxbBuilder(supplier);
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
    final PetJaxb objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.fullName) {
      objectToBuild.setFullName(this.fieldValue.fullName);
    }
    if (this.callSetterFor.owner) {
      objectToBuild.setOwner(this.fieldValue.owner);
    }
    if (this.callSetterFor.siblings && this.fieldValue.siblings != null) {
      this.fieldValue.siblings.forEach(objectToBuild.getSiblings()::add);
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
