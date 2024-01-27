package io.github.tobi.laa.reflective.fluent.builders.test.models.jaxb;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

/**
 * Builder for {@link PetJaxb}.
 */
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

  /**
   * Creates a new instance of {@link PetJaxb} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected PetJaxbBuilder(final Supplier<PetJaxb> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link PetJaxbBuilder} that will work on a new instance of {@link PetJaxb} once {@link #build()} is called.
   */
  public static PetJaxbBuilder newInstance() {
    return new PetJaxbBuilder(PetJaxb::new);
  }

  /**
   * Creates an instance of {@link PetJaxbBuilder} that will work on an instance of {@link PetJaxb} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static PetJaxbBuilder withSupplier(final Supplier<PetJaxb> supplier) {
    return new PetJaxbBuilder(supplier);
  }

  /**
   * Returns an inner builder for the collection property {@code siblings} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.siblings()
   *        .add(item1)
   *        .add(item2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the collection property {@code siblings}.
   */
  public CollectionSiblings siblings() {
    return new CollectionSiblings();
  }

  /**
   * Sets the value for the {@code fullName} property.
   * To be more precise, this will lead to {@link PetJaxb#setFullName(String)} being called on construction of the object.
   * @param fullName the value to set.
   * @return This builder for chained calls.
   */
  public PetJaxbBuilder fullName(final String fullName) {
    this.fieldValue.fullName = fullName;
    this.callSetterFor.fullName = true;
    return this;
  }

  /**
   * Sets the value for the {@code owner} property.
   * To be more precise, this will lead to {@link PetJaxb#setOwner(PersonJaxb)} being called on construction of the object.
   * @param owner the value to set.
   * @return This builder for chained calls.
   */
  public PetJaxbBuilder owner(final PersonJaxb owner) {
    this.fieldValue.owner = owner;
    this.callSetterFor.owner = true;
    return this;
  }

  /**
   * Sets the value for the {@code siblings} property.
   * To be more precise, this will lead to {@link PetJaxb#getSiblings()} being called on construction of the object.
   * @param siblings the value to set.
   * @return This builder for chained calls.
   */
  public PetJaxbBuilder siblings(final List<PetJaxb> siblings) {
    this.fieldValue.siblings = siblings;
    this.callSetterFor.siblings = true;
    return this;
  }

  /**
   * Sets the value for the {@code weight} property.
   * To be more precise, this will lead to {@link PetJaxb#setWeight(float)} being called on construction of the object.
   * @param weight the value to set.
   * @return This builder for chained calls.
   */
  public PetJaxbBuilder weight(final float weight) {
    this.fieldValue.weight = weight;
    this.callSetterFor.weight = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link PetJaxb}.
   * @return The constructed instance. Never {@code null}.
   */
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
    /**
     * Adds an item to the collection property {@code siblings}.
     * @param item The item to add to the collection {@code siblings}.
     * @return This builder for chained calls.
     */
    public CollectionSiblings add(final PetJaxb item) {
      if (PetJaxbBuilder.this.fieldValue.siblings == null) {
        PetJaxbBuilder.this.fieldValue.siblings = new ArrayList<>();
      }
      PetJaxbBuilder.this.fieldValue.siblings.add(item);
      PetJaxbBuilder.this.callSetterFor.siblings = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public PetJaxbBuilder and() {
      return PetJaxbBuilder.this;
    }
  }
}
