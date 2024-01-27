package io.github.tobi.laa.reflective.fluent.builders.test.models.full;

import java.lang.Object;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.Generated;

/**
 * Builder for {@link Person}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class PersonBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<Person> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link Person} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected PersonBuilder(final Supplier<Person> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link PersonBuilder} that will work on a new instance of {@link Person} once {@link #build()} is called.
   */
  public static PersonBuilder newInstance() {
    return new PersonBuilder(Person::new);
  }

  /**
   * Creates an instance of {@link PersonBuilder} that will work on an instance of {@link Person} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static PersonBuilder withSupplier(final Supplier<Person> supplier) {
    return new PersonBuilder(supplier);
  }

  /**
   * Returns an inner builder for the array property {@code names} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.names()
   *        .add(item1)
   *        .add(item2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the array property {@code names}.
   */
  public ArrayNames names() {
    return new ArrayNames();
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
   * Returns an inner builder for the map property {@code relations} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.relations()
   *        .put(key1, value1)
   *        .put(key2, value2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the map property {@code relations}.
   */
  public MapRelations relations() {
    return new MapRelations();
  }

  /**
   * Sets the value for the {@code age} property.
   * To be more precise, this will lead to {@link Person#setAge(int)} being called on construction of the object.
   * @param age the value to set.
   * @return This builder for chained calls.
   */
  public PersonBuilder age(final int age) {
    this.fieldValue.age = age;
    this.callSetterFor.age = true;
    return this;
  }

  /**
   * Sets the value for the {@code attributes} property.
   * To be more precise, this will lead to {@link Person#setAttributes(List)} being called on construction of the object.
   * @param attributes the value to set.
   * @return This builder for chained calls.
   */
  public PersonBuilder attributes(final List attributes) {
    this.fieldValue.attributes = attributes;
    this.callSetterFor.attributes = true;
    return this;
  }

  /**
   * Sets the value for the {@code married} property.
   * To be more precise, this will lead to {@link Person#setMarried(boolean)} being called on construction of the object.
   * @param married the value to set.
   * @return This builder for chained calls.
   */
  public PersonBuilder married(final boolean married) {
    this.fieldValue.married = married;
    this.callSetterFor.married = true;
    return this;
  }

  /**
   * Sets the value for the {@code married0} property.
   * To be more precise, this will lead to {@link Person#setMarried(String)} being called on construction of the object.
   * @param married the value to set.
   * @return This builder for chained calls.
   */
  public PersonBuilder married(final String married) {
    this.fieldValue.married0 = married;
    this.callSetterFor.married0 = true;
    return this;
  }

  /**
   * Sets the value for the {@code names} property.
   * To be more precise, this will lead to {@link Person#setNames(String[])} being called on construction of the object.
   * @param names the value to set.
   * @return This builder for chained calls.
   */
  public PersonBuilder names(final String[] names) {
    this.fieldValue.names = names;
    this.callSetterFor.names = true;
    return this;
  }

  /**
   * Sets the value for the {@code pets} property.
   * To be more precise, this will lead to {@link Person#setPets(Set<Pet>)} being called on construction of the object.
   * @param pets the value to set.
   * @return This builder for chained calls.
   */
  public PersonBuilder pets(final Set<Pet> pets) {
    this.fieldValue.pets = pets;
    this.callSetterFor.pets = true;
    return this;
  }

  /**
   * Sets the value for the {@code relations} property.
   * To be more precise, this will lead to {@link Person#setRelations(Map<String, Person>)} being called on construction of the object.
   * @param relations the value to set.
   * @return This builder for chained calls.
   */
  public PersonBuilder relations(final Map<String, Person> relations) {
    this.fieldValue.relations = relations;
    this.callSetterFor.relations = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link Person}.
   * @return The constructed instance. Never {@code null}.
   */
  public Person build() {
    final Person objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.age) {
      objectToBuild.setAge(this.fieldValue.age);
    }
    if (this.callSetterFor.attributes) {
      objectToBuild.setAttributes(this.fieldValue.attributes);
    }
    if (this.callSetterFor.married) {
      objectToBuild.setMarried(this.fieldValue.married);
    }
    if (this.callSetterFor.married0) {
      objectToBuild.setMarried(this.fieldValue.married0);
    }
    if (this.callSetterFor.names) {
      objectToBuild.setNames(this.fieldValue.names);
    }
    if (this.callSetterFor.pets) {
      objectToBuild.setPets(this.fieldValue.pets);
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

    boolean married0;

    boolean names;

    boolean pets;

    boolean relations;
  }

  private class FieldValue {
    int age;

    List attributes;

    boolean married;

    String married0;

    String[] names;

    Set<Pet> pets;

    Map<String, Person> relations;
  }

  public class ArrayNames {
    private List<String> list;

    /**
     * Adds an item to the array property {@code names}.
     * @param item The item to add to the array {@code names}.
     * @return This builder for chained calls.
     */
    public ArrayNames add(final String item) {
      if (this.list == null) {
        this.list = new ArrayList<>();
      }
      this.list.add(item);
      PersonBuilder.this.callSetterFor.names = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public PersonBuilder and() {
      if (this.list != null) {
        PersonBuilder.this.fieldValue.names = new String[this.list.size()];
        for (int i = 0; i < this.list.size(); i++) {
          PersonBuilder.this.fieldValue.names[i] = this.list.get(i);
        }
      }
      return PersonBuilder.this;
    }
  }

  public class CollectionAttributes {
    /**
     * Adds an item to the collection property {@code attributes}.
     * @param item The item to add to the collection {@code attributes}.
     * @return This builder for chained calls.
     */
    public CollectionAttributes add(final Object item) {
      if (PersonBuilder.this.fieldValue.attributes == null) {
        PersonBuilder.this.fieldValue.attributes = new ArrayList<>();
      }
      PersonBuilder.this.fieldValue.attributes.add(item);
      PersonBuilder.this.callSetterFor.attributes = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public PersonBuilder and() {
      return PersonBuilder.this;
    }
  }

  public class CollectionPets {
    /**
     * Adds an item to the collection property {@code pets}.
     * @param item The item to add to the collection {@code pets}.
     * @return This builder for chained calls.
     */
    public CollectionPets add(final Pet item) {
      if (PersonBuilder.this.fieldValue.pets == null) {
        PersonBuilder.this.fieldValue.pets = new HashSet<>();
      }
      PersonBuilder.this.fieldValue.pets.add(item);
      PersonBuilder.this.callSetterFor.pets = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public PersonBuilder and() {
      return PersonBuilder.this;
    }
  }

  public class MapRelations {
    /**
     * Adds an entry to the map property {@code relations}.
     * @param key The key of the entry to add to the map {@code relations}.
     * @param value The value of the entry to add to the map {@code relations}.
     * @return This builder for chained calls.
     */
    public MapRelations put(final String key, final Person value) {
      if (PersonBuilder.this.fieldValue.relations == null) {
        PersonBuilder.this.fieldValue.relations = new HashMap<>();
      }
      PersonBuilder.this.fieldValue.relations.put(key, value);
      PersonBuilder.this.callSetterFor.relations = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public PersonBuilder and() {
      return PersonBuilder.this;
    }
  }
}
