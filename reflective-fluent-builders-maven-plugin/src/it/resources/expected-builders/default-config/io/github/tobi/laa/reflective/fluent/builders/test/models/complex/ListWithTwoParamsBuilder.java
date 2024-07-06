package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

/**
 * Builder for {@link ListWithTwoParams}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ListWithTwoParamsBuilder<A, B> {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("all")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<ListWithTwoParams> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link ListWithTwoParams} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected ListWithTwoParamsBuilder(final Supplier<ListWithTwoParams> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link ListWithTwoParamsBuilder} that will work on a new instance of {@link ListWithTwoParams} once {@link #build()} is called.
   */
  public static ListWithTwoParamsBuilder newInstance() {
    return new ListWithTwoParamsBuilder(ListWithTwoParams::new);
  }

  /**
   * Creates an instance of {@link ListWithTwoParamsBuilder} that will work on an instance of {@link ListWithTwoParams} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static ListWithTwoParamsBuilder withSupplier(final Supplier<ListWithTwoParams> supplier) {
    return new ListWithTwoParamsBuilder(supplier);
  }

  /**
   * Adds a value to the {@code alls} property.
   * To be more precise, this will lead to {@link ArrayList#addAll(List<Collection<? extends Map<A, B>>>)} being called on construction of the object.
   * @param all the value to add to {@code alls}.
   * @return This builder for chained calls.
   */
  public ListWithTwoParamsBuilder all(final Collection<Map<A, B>> all) {
    if (this.fieldValue.alls == null) {
      this.fieldValue.alls = new ArrayList<>();
    }
    this.fieldValue.alls.add(all);
    this.callSetterFor.alls = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link ListWithTwoParams}.
   * @return The constructed instance. Never {@code null}.
   */
  public ListWithTwoParams build() {
    final ListWithTwoParams objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.alls && this.fieldValue.alls != null) {
      this.fieldValue.alls.forEach(objectToBuild::addAll);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean alls;
  }

  private class FieldValue {
    List<Collection<? extends Map<A, B>>> alls;
  }
}
