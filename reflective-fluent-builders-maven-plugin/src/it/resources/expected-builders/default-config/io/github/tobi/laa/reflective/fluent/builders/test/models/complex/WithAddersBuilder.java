package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import java.lang.Object;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

/**
 * Builder for {@link WithAdders}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class WithAddersBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<WithAdders> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link WithAdders} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected WithAddersBuilder(final Supplier<WithAdders> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link WithAddersBuilder} that will work on a new instance of {@link WithAdders} once {@link #build()} is called.
   */
  public static WithAddersBuilder newInstance() {
    return new WithAddersBuilder(WithAdders::new);
  }

  /**
   * Creates an instance of {@link WithAddersBuilder} that will work on an instance of {@link WithAdders} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static WithAddersBuilder withSupplier(final Supplier<WithAdders> supplier) {
    return new WithAddersBuilder(supplier);
  }

  /**
   * Returns an inner builder for the collection property {@code hasInaccessibleAdders} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.hasInaccessibleAdders()
   *        .add(item1)
   *        .add(item2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the collection property {@code hasInaccessibleAdders}.
   */
  public CollectionHasInaccessibleAdders hasInaccessibleAdders() {
    return new CollectionHasInaccessibleAdders();
  }

  /**
   * Returns an inner builder for the collection property {@code hasNoAdders} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.hasNoAdders()
   *        .add(item1)
   *        .add(item2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the collection property {@code hasNoAdders}.
   */
  public CollectionHasNoAdders hasNoAdders() {
    return new CollectionHasNoAdders();
  }

  /**
   * Adds a value to the {@code alsoHasAdders} property.
   * To be more precise, this will lead to {@link WithAdders#addAlsoHasAdder(List<List<Object>>)} being called on construction of the object.
   * @param alsoHasAdder the value to add to {@code alsoHasAdders}.
   * @return This builder for chained calls.
   */
  public WithAddersBuilder alsoHasAdder(final List<Object> alsoHasAdder) {
    if (this.fieldValue.alsoHasAdders == null) {
      this.fieldValue.alsoHasAdders = new ArrayList<>();
    }
    this.fieldValue.alsoHasAdders.add(alsoHasAdder);
    this.callSetterFor.alsoHasAdders = true;
    return this;
  }

  /**
   * Adds a value to the {@code hasAdders} property.
   * To be more precise, this will lead to {@link WithAdders#addHasAdder(List<String>)} being called on construction of the object.
   * @param hasAdder the value to add to {@code hasAdders}.
   * @return This builder for chained calls.
   */
  public WithAddersBuilder hasAdder(final String hasAdder) {
    if (this.fieldValue.hasAdders == null) {
      this.fieldValue.hasAdders = new ArrayList<>();
    }
    this.fieldValue.hasAdders.add(hasAdder);
    this.callSetterFor.hasAdders = true;
    return this;
  }

  /**
   * Sets the value for the {@code hasInaccessibleAdders} property.
   * To be more precise, this will lead to {@link WithAdders#setHasInaccessibleAdders(List<Map<String, String>>)} being called on construction of the object.
   * @param hasInaccessibleAdders the value to set.
   * @return This builder for chained calls.
   */
  public WithAddersBuilder hasInaccessibleAdders(
      final List<Map<String, String>> hasInaccessibleAdders) {
    this.fieldValue.hasInaccessibleAdders = hasInaccessibleAdders;
    this.callSetterFor.hasInaccessibleAdders = true;
    return this;
  }

  /**
   * Sets the value for the {@code hasNoAdders} property.
   * To be more precise, this will lead to {@link WithAdders#setHasNoAdders(List<String>)} being called on construction of the object.
   * @param hasNoAdders the value to set.
   * @return This builder for chained calls.
   */
  public WithAddersBuilder hasNoAdders(final List<String> hasNoAdders) {
    this.fieldValue.hasNoAdders = hasNoAdders;
    this.callSetterFor.hasNoAdders = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link WithAdders}.
   * @return The constructed instance. Never {@code null}.
   */
  public WithAdders build() {
    final WithAdders objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.alsoHasAdders && this.fieldValue.alsoHasAdders != null) {
      this.fieldValue.alsoHasAdders.forEach(objectToBuild::addAlsoHasAdder);
    }
    if (this.callSetterFor.hasAdders && this.fieldValue.hasAdders != null) {
      this.fieldValue.hasAdders.forEach(objectToBuild::addHasAdder);
    }
    if (this.callSetterFor.hasInaccessibleAdders) {
      objectToBuild.setHasInaccessibleAdders(this.fieldValue.hasInaccessibleAdders);
    }
    if (this.callSetterFor.hasNoAdders) {
      objectToBuild.setHasNoAdders(this.fieldValue.hasNoAdders);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean alsoHasAdders;

    boolean hasAdders;

    boolean hasInaccessibleAdders;

    boolean hasNoAdders;
  }

  private class FieldValue {
    List<List<Object>> alsoHasAdders;

    List<String> hasAdders;

    List<Map<String, String>> hasInaccessibleAdders;

    List<String> hasNoAdders;
  }

  public class CollectionHasInaccessibleAdders {
    /**
     * Adds an item to the collection property {@code hasInaccessibleAdders}.
     * @param item The item to add to the collection {@code hasInaccessibleAdders}.
     * @return This builder for chained calls.
     */
    public CollectionHasInaccessibleAdders add(final Map<String, String> item) {
      if (WithAddersBuilder.this.fieldValue.hasInaccessibleAdders == null) {
        WithAddersBuilder.this.fieldValue.hasInaccessibleAdders = new ArrayList<>();
      }
      WithAddersBuilder.this.fieldValue.hasInaccessibleAdders.add(item);
      WithAddersBuilder.this.callSetterFor.hasInaccessibleAdders = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public WithAddersBuilder and() {
      return WithAddersBuilder.this;
    }
  }

  public class CollectionHasNoAdders {
    /**
     * Adds an item to the collection property {@code hasNoAdders}.
     * @param item The item to add to the collection {@code hasNoAdders}.
     * @return This builder for chained calls.
     */
    public CollectionHasNoAdders add(final String item) {
      if (WithAddersBuilder.this.fieldValue.hasNoAdders == null) {
        WithAddersBuilder.this.fieldValue.hasNoAdders = new ArrayList<>();
      }
      WithAddersBuilder.this.fieldValue.hasNoAdders.add(item);
      WithAddersBuilder.this.callSetterFor.hasNoAdders = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public WithAddersBuilder and() {
      return WithAddersBuilder.this;
    }
  }
}
