package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import java.lang.Object;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

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

  protected WithAddersBuilder(final Supplier<WithAdders> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static WithAddersBuilder newInstance() {
    return new WithAddersBuilder(WithAdders::new);
  }

  public static WithAddersBuilder withSupplier(final Supplier<WithAdders> supplier) {
    return new WithAddersBuilder(supplier);
  }

  public CollectionAlsoHasAdder alsoHasAdder() {
    return new CollectionAlsoHasAdder();
  }

  public CollectionHasAdder hasAdder() {
    return new CollectionHasAdder();
  }

  public CollectionHasInaccessibleAdders hasInaccessibleAdders() {
    return new CollectionHasInaccessibleAdders();
  }

  public CollectionHasNoAdders hasNoAdders() {
    return new CollectionHasNoAdders();
  }

  public WithAddersBuilder alsoHasAdder(final List<List<Object>> alsoHasAdder) {
    this.fieldValue.alsoHasAdder = alsoHasAdder;
    this.callSetterFor.alsoHasAdder = true;
    return this;
  }

  public WithAddersBuilder hasAdder(final List<String> hasAdder) {
    this.fieldValue.hasAdder = hasAdder;
    this.callSetterFor.hasAdder = true;
    return this;
  }

  public WithAddersBuilder hasInaccessibleAdders(
      final List<Map<String, String>> hasInaccessibleAdders) {
    this.fieldValue.hasInaccessibleAdders = hasInaccessibleAdders;
    this.callSetterFor.hasInaccessibleAdders = true;
    return this;
  }

  public WithAddersBuilder hasNoAdders(final List<String> hasNoAdders) {
    this.fieldValue.hasNoAdders = hasNoAdders;
    this.callSetterFor.hasNoAdders = true;
    return this;
  }

  public WithAdders build() {
    final WithAdders objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.alsoHasAdder && this.fieldValue.alsoHasAdder != null) {
      this.fieldValue.alsoHasAdder.forEach(objectToBuild::addAlsoHasAdder);
    }
    if (this.callSetterFor.hasAdder && this.fieldValue.hasAdder != null) {
      this.fieldValue.hasAdder.forEach(objectToBuild::addHasAdder);
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
    boolean alsoHasAdder;

    boolean hasAdder;

    boolean hasInaccessibleAdders;

    boolean hasNoAdders;
  }

  private class FieldValue {
    List<List<Object>> alsoHasAdder;

    List<String> hasAdder;

    List<Map<String, String>> hasInaccessibleAdders;

    List<String> hasNoAdders;
  }

  public class CollectionAlsoHasAdder {
    public CollectionAlsoHasAdder add(final List<Object> item) {
      if (WithAddersBuilder.this.fieldValue.alsoHasAdder == null) {
        WithAddersBuilder.this.fieldValue.alsoHasAdder = new ArrayList<>();
      }
      WithAddersBuilder.this.fieldValue.alsoHasAdder.add(item);
      WithAddersBuilder.this.callSetterFor.alsoHasAdder = true;
      return this;
    }

    public WithAddersBuilder and() {
      return WithAddersBuilder.this;
    }
  }

  public class CollectionHasAdder {
    public CollectionHasAdder add(final String item) {
      if (WithAddersBuilder.this.fieldValue.hasAdder == null) {
        WithAddersBuilder.this.fieldValue.hasAdder = new ArrayList<>();
      }
      WithAddersBuilder.this.fieldValue.hasAdder.add(item);
      WithAddersBuilder.this.callSetterFor.hasAdder = true;
      return this;
    }

    public WithAddersBuilder and() {
      return WithAddersBuilder.this;
    }
  }

  public class CollectionHasInaccessibleAdders {
    public CollectionHasInaccessibleAdders add(final Map<String, String> item) {
      if (WithAddersBuilder.this.fieldValue.hasInaccessibleAdders == null) {
        WithAddersBuilder.this.fieldValue.hasInaccessibleAdders = new ArrayList<>();
      }
      WithAddersBuilder.this.fieldValue.hasInaccessibleAdders.add(item);
      WithAddersBuilder.this.callSetterFor.hasInaccessibleAdders = true;
      return this;
    }

    public WithAddersBuilder and() {
      return WithAddersBuilder.this;
    }
  }

  public class CollectionHasNoAdders {
    public CollectionHasNoAdders add(final String item) {
      if (WithAddersBuilder.this.fieldValue.hasNoAdders == null) {
        WithAddersBuilder.this.fieldValue.hasNoAdders = new ArrayList<>();
      }
      WithAddersBuilder.this.fieldValue.hasNoAdders.add(item);
      WithAddersBuilder.this.callSetterFor.hasNoAdders = true;
      return this;
    }

    public WithAddersBuilder and() {
      return WithAddersBuilder.this;
    }
  }
}
