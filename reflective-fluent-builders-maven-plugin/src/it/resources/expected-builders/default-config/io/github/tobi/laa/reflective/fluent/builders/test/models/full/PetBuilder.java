package io.github.tobi.laa.reflective.fluent.builders.test.models.full;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Supplier;
import javax.annotation.Generated;

/**
 * Builder for {@link Pet}.
 */
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

  private final Supplier<Pet> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link Pet} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected PetBuilder(final Supplier<Pet> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link PetBuilder} that will work on a new instance of {@link Pet} once {@link #build()} is called.
   */
  public static PetBuilder newInstance() {
    return new PetBuilder(Pet::new);
  }

  /**
   * Creates an instance of {@link PetBuilder} that will work on an instance of {@link Pet} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static PetBuilder withSupplier(final Supplier<Pet> supplier) {
    return new PetBuilder(supplier);
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
   * To be more precise, this will lead to {@link Pet#setFullName(String)} being called on construction of the object.
   * @param fullName the value to set.
   * @return This builder for chained calls.
   */
  public PetBuilder fullName(final String fullName) {
    this.fieldValue.fullName = fullName;
    this.callSetterFor.fullName = true;
    return this;
  }

  /**
   * Sets the value for the {@code owner} property.
   * To be more precise, this will lead to {@link Pet#setOwner(Person)} being called on construction of the object.
   * @param owner the value to set.
   * @return This builder for chained calls.
   */
  public PetBuilder owner(final Person owner) {
    this.fieldValue.owner = owner;
    this.callSetterFor.owner = true;
    return this;
  }

  /**
   * Sets the value for the {@code siblings} property.
   * To be more precise, this will lead to {@link Pet#setSiblings(SortedSet<Pet>)} being called on construction of the object.
   * @param siblings the value to set.
   * @return This builder for chained calls.
   */
  public PetBuilder siblings(final SortedSet<Pet> siblings) {
    this.fieldValue.siblings = siblings;
    this.callSetterFor.siblings = true;
    return this;
  }

  /**
   * Sets the value for the {@code weight} property.
   * To be more precise, this will lead to {@link Pet#setWeight(float)} being called on construction of the object.
   * @param weight the value to set.
   * @return This builder for chained calls.
   */
  public PetBuilder weight(final float weight) {
    this.fieldValue.weight = weight;
    this.callSetterFor.weight = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link Pet}.
   * @return The constructed instance. Never {@code null}.
   */
  public Pet build() {
    final Pet objectToBuild = this.objectSupplier.get();
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
    /**
     * Adds an item to the collection property {@code siblings}.
     * @param item The item to add to the collection {@code siblings}.
     * @return This builder for chained calls.
     */
    public CollectionSiblings add(final Pet item) {
      if (PetBuilder.this.fieldValue.siblings == null) {
        PetBuilder.this.fieldValue.siblings = new TreeSet<>();
      }
      PetBuilder.this.fieldValue.siblings.add(item);
      PetBuilder.this.callSetterFor.siblings = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public PetBuilder and() {
      return PetBuilder.this;
    }
  }
}
