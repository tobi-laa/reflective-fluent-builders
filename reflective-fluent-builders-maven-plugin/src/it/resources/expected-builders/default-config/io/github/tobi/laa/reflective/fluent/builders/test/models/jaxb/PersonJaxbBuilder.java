package io.github.tobi.laa.reflective.fluent.builders.test.models.jaxb;

import java.lang.Object;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

/**
 * Builder for {@link PersonJaxb}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class PersonJaxbBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<PersonJaxb> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link PersonJaxb} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected PersonJaxbBuilder(final Supplier<PersonJaxb> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link PersonJaxbBuilder} that will work on a new instance of {@link PersonJaxb} once {@link #build()} is called.
   */
  public static PersonJaxbBuilder newInstance() {
    return new PersonJaxbBuilder(PersonJaxb::new);
  }

  /**
   * Creates an instance of {@link PersonJaxbBuilder} that will work on an instance of {@link PersonJaxb} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static PersonJaxbBuilder withSupplier(final Supplier<PersonJaxb> supplier) {
    return new PersonJaxbBuilder(supplier);
  }

  /**
   * Returns an inner builder for the collection property {@code attributes} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.attributes()
   *        .add(item1)
   *        .add(item2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the collection property {@code attributes}.
   */
  public CollectionAttributes attributes() {
    return new CollectionAttributes();
  }

  /**
   * Returns an inner builder for the collection property {@code names} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.names()
   *        .add(item1)
   *        .add(item2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the collection property {@code names}.
   */
  public CollectionNames names() {
    return new CollectionNames();
  }

  /**
   * Returns an inner builder for the collection property {@code pets} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.pets()
   *        .add(item1)
   *        .add(item2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the collection property {@code pets}.
   */
  public CollectionPets pets() {
    return new CollectionPets();
  }

  /**
   * Sets the value for the {@code age} property.
   * To be more precise, this will lead to {@link PersonJaxb#setAge(int)} being called on construction of the object.
   * @param age the value to set.
   * @return This builder for chained calls.
   */
  public PersonJaxbBuilder age(final int age) {
    this.fieldValue.age = age;
    this.callSetterFor.age = true;
    return this;
  }

  /**
   * Sets the value for the {@code attributes} property.
   * To be more precise, this will lead to {@link PersonJaxb#getAttributes()} being called on construction of the object.
   * @param attributes the value to set.
   * @return This builder for chained calls.
   */
  public PersonJaxbBuilder attributes(final List<Object> attributes) {
    this.fieldValue.attributes = attributes;
    this.callSetterFor.attributes = true;
    return this;
  }

  /**
   * Sets the value for the {@code married} property.
   * To be more precise, this will lead to {@link PersonJaxb#setMarried(boolean)} being called on construction of the object.
   * @param married the value to set.
   * @return This builder for chained calls.
   */
  public PersonJaxbBuilder married(final boolean married) {
    this.fieldValue.married = married;
    this.callSetterFor.married = true;
    return this;
  }

  /**
   * Sets the value for the {@code names} property.
   * To be more precise, this will lead to {@link PersonJaxb#getNames()} being called on construction of the object.
   * @param names the value to set.
   * @return This builder for chained calls.
   */
  public PersonJaxbBuilder names(final List<String> names) {
    this.fieldValue.names = names;
    this.callSetterFor.names = true;
    return this;
  }

  /**
   * Sets the value for the {@code pets} property.
   * To be more precise, this will lead to {@link PersonJaxb#getPets()} being called on construction of the object.
   * @param pets the value to set.
   * @return This builder for chained calls.
   */
  public PersonJaxbBuilder pets(final List<PetJaxb> pets) {
    this.fieldValue.pets = pets;
    this.callSetterFor.pets = true;
    return this;
  }

  /**
   * Sets the value for the {@code relations} property.
   * To be more precise, this will lead to {@link PersonJaxb#setRelations(PersonJaxb.Relations)} being called on construction of the object.
   * @param relations the value to set.
   * @return This builder for chained calls.
   */
  public PersonJaxbBuilder relations(final PersonJaxb.Relations relations) {
    this.fieldValue.relations = relations;
    this.callSetterFor.relations = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link PersonJaxb}.
   * @return The constructed instance. Never {@code null}.
   */
  public PersonJaxb build() {
    final PersonJaxb objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.age) {
      objectToBuild.setAge(this.fieldValue.age);
    }
    if (this.callSetterFor.attributes && this.fieldValue.attributes != null) {
      this.fieldValue.attributes.forEach(objectToBuild.getAttributes()::add);
    }
    if (this.callSetterFor.married) {
      objectToBuild.setMarried(this.fieldValue.married);
    }
    if (this.callSetterFor.names && this.fieldValue.names != null) {
      this.fieldValue.names.forEach(objectToBuild.getNames()::add);
    }
    if (this.callSetterFor.pets && this.fieldValue.pets != null) {
      this.fieldValue.pets.forEach(objectToBuild.getPets()::add);
    }
    if (this.callSetterFor.relations) {
      objectToBuild.setRelations(this.fieldValue.relations);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean age;

    boolean attributes;

    boolean married;

    boolean names;

    boolean pets;

    boolean relations;
  }

  private class FieldValue {
    int age;

    List<Object> attributes;

    boolean married;

    List<String> names;

    List<PetJaxb> pets;

    PersonJaxb.Relations relations;
  }

  public class CollectionAttributes {
    /**
     * Adds an item to the collection property {@code attributes}.
     * @param item The item to add to the collection {@code attributes}.
     * @return This builder for chained calls.
     */
    public CollectionAttributes add(final Object item) {
      if (PersonJaxbBuilder.this.fieldValue.attributes == null) {
        PersonJaxbBuilder.this.fieldValue.attributes = new ArrayList<>();
      }
      PersonJaxbBuilder.this.fieldValue.attributes.add(item);
      PersonJaxbBuilder.this.callSetterFor.attributes = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public PersonJaxbBuilder and() {
      return PersonJaxbBuilder.this;
    }
  }

  public class CollectionNames {
    /**
     * Adds an item to the collection property {@code names}.
     * @param item The item to add to the collection {@code names}.
     * @return This builder for chained calls.
     */
    public CollectionNames add(final String item) {
      if (PersonJaxbBuilder.this.fieldValue.names == null) {
        PersonJaxbBuilder.this.fieldValue.names = new ArrayList<>();
      }
      PersonJaxbBuilder.this.fieldValue.names.add(item);
      PersonJaxbBuilder.this.callSetterFor.names = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public PersonJaxbBuilder and() {
      return PersonJaxbBuilder.this;
    }
  }

  public class CollectionPets {
    /**
     * Adds an item to the collection property {@code pets}.
     * @param item The item to add to the collection {@code pets}.
     * @return This builder for chained calls.
     */
    public CollectionPets add(final PetJaxb item) {
      if (PersonJaxbBuilder.this.fieldValue.pets == null) {
        PersonJaxbBuilder.this.fieldValue.pets = new ArrayList<>();
      }
      PersonJaxbBuilder.this.fieldValue.pets.add(item);
      PersonJaxbBuilder.this.callSetterFor.pets = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public PersonJaxbBuilder and() {
      return PersonJaxbBuilder.this;
    }
  }

  /**
   * Builder for {@link PersonJaxb.Relations}.
   */
  @Generated(
      value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
      date = "3333-03-13T00:00Z[UTC]"
  )
  public static class RelationsBuilder {
    /**
     * This field is solely used to be able to detect generated builders via reflection at a later stage.
     */
    @SuppressWarnings("unused")
    private boolean ______generatedByReflectiveFluentBuildersGenerator;

    private final Supplier<PersonJaxb.Relations> objectSupplier;

    private final CallSetterFor callSetterFor = new CallSetterFor();

    private final FieldValue fieldValue = new FieldValue();

    /**
     * Creates a new instance of {@link PersonJaxb.Relations} using the given {@code objectSupplier}.
     * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
     */
    protected RelationsBuilder(final Supplier<PersonJaxb.Relations> objectSupplier) {
      this.objectSupplier = Objects.requireNonNull(objectSupplier);
    }

    /**
     * Creates an instance of {@link RelationsBuilder} that will work on a new instance of {@link PersonJaxb.Relations} once {@link #build()} is called.
     */
    public static RelationsBuilder newInstance() {
      return new RelationsBuilder(PersonJaxb.Relations::new);
    }

    /**
     * Creates an instance of {@link RelationsBuilder} that will work on an instance of {@link PersonJaxb.Relations} that is created initially by the given {@code supplier} once {@link #build()} is called.
     */
    public static RelationsBuilder withSupplier(final Supplier<PersonJaxb.Relations> supplier) {
      return new RelationsBuilder(supplier);
    }

    /**
     * Returns an inner builder for the collection property {@code entry} for chained calls of adding items to it.
     * Can be used like follows:
     * <pre>
     * builder.entry()
     *        .add(item1)
     *        .add(item2)
     *        .and()
     *        .build()
     * </pre>
     * @return The inner builder for the collection property {@code entry}.
     */
    public CollectionEntry entry() {
      return new CollectionEntry();
    }

    /**
     * Sets the value for the {@code entry} property.
     * To be more precise, this will lead to {@link PersonJaxb.Relations#getEntry()} being called on construction of the object.
     * @param entry the value to set.
     * @return This builder for chained calls.
     */
    public RelationsBuilder entry(final List<PersonJaxb.Relations.Entry> entry) {
      this.fieldValue.entry = entry;
      this.callSetterFor.entry = true;
      return this;
    }

    /**
     * Performs the actual construction of an instance for {@link PersonJaxb.Relations}.
     * @return The constructed instance. Never {@code null}.
     */
    public PersonJaxb.Relations build() {
      final PersonJaxb.Relations objectToBuild = this.objectSupplier.get();
      if (this.callSetterFor.entry && this.fieldValue.entry != null) {
        this.fieldValue.entry.forEach(objectToBuild.getEntry()::add);
      }
      return objectToBuild;
    }

    private class CallSetterFor {
      boolean entry;
    }

    private class FieldValue {
      List<PersonJaxb.Relations.Entry> entry;
    }

    public class CollectionEntry {
      /**
       * Adds an item to the collection property {@code entry}.
       * @param item The item to add to the collection {@code entry}.
       * @return This builder for chained calls.
       */
      public CollectionEntry add(final PersonJaxb.Relations.Entry item) {
        if (RelationsBuilder.this.fieldValue.entry == null) {
          RelationsBuilder.this.fieldValue.entry = new ArrayList<>();
        }
        RelationsBuilder.this.fieldValue.entry.add(item);
        RelationsBuilder.this.callSetterFor.entry = true;
        return this;
      }

      /**
       * Returns the builder for the parent object.
       * @return The builder for the parent object.
       */
      public RelationsBuilder and() {
        return RelationsBuilder.this;
      }
    }

    /**
     * Builder for {@link PersonJaxb.Relations.Entry}.
     */
    @Generated(
        value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
        date = "3333-03-13T00:00Z[UTC]"
    )
    public static class EntryBuilder {
      /**
       * This field is solely used to be able to detect generated builders via reflection at a later stage.
       */
      @SuppressWarnings("unused")
      private boolean ______generatedByReflectiveFluentBuildersGenerator;

      private final Supplier<PersonJaxb.Relations.Entry> objectSupplier;

      private final CallSetterFor callSetterFor = new CallSetterFor();

      private final FieldValue fieldValue = new FieldValue();

      /**
       * Creates a new instance of {@link PersonJaxb.Relations.Entry} using the given {@code objectSupplier}.
       * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
       */
      protected EntryBuilder(final Supplier<PersonJaxb.Relations.Entry> objectSupplier) {
        this.objectSupplier = Objects.requireNonNull(objectSupplier);
      }

      /**
       * Creates an instance of {@link EntryBuilder} that will work on a new instance of {@link PersonJaxb.Relations.Entry} once {@link #build()} is called.
       */
      public static EntryBuilder newInstance() {
        return new EntryBuilder(PersonJaxb.Relations.Entry::new);
      }

      /**
       * Creates an instance of {@link EntryBuilder} that will work on an instance of {@link PersonJaxb.Relations.Entry} that is created initially by the given {@code supplier} once {@link #build()} is called.
       */
      public static EntryBuilder withSupplier(final Supplier<PersonJaxb.Relations.Entry> supplier) {
        return new EntryBuilder(supplier);
      }

      /**
       * Sets the value for the {@code key} property.
       * To be more precise, this will lead to {@link PersonJaxb.Relations.Entry#setKey(String)} being called on construction of the object.
       * @param key the value to set.
       * @return This builder for chained calls.
       */
      public EntryBuilder key(final String key) {
        this.fieldValue.key = key;
        this.callSetterFor.key = true;
        return this;
      }

      /**
       * Sets the value for the {@code value} property.
       * To be more precise, this will lead to {@link PersonJaxb.Relations.Entry#setValue(PersonJaxb)} being called on construction of the object.
       * @param value the value to set.
       * @return This builder for chained calls.
       */
      public EntryBuilder value(final PersonJaxb value) {
        this.fieldValue.value = value;
        this.callSetterFor.value = true;
        return this;
      }

      /**
       * Performs the actual construction of an instance for {@link PersonJaxb.Relations.Entry}.
       * @return The constructed instance. Never {@code null}.
       */
      public PersonJaxb.Relations.Entry build() {
        final PersonJaxb.Relations.Entry objectToBuild = this.objectSupplier.get();
        if (this.callSetterFor.key) {
          objectToBuild.setKey(this.fieldValue.key);
        }
        if (this.callSetterFor.value) {
          objectToBuild.setValue(this.fieldValue.value);
        }
        return objectToBuild;
      }

      private class CallSetterFor {
        boolean key;

        boolean value;
      }

      private class FieldValue {
        String key;

        PersonJaxb value;
      }
    }
  }
}
