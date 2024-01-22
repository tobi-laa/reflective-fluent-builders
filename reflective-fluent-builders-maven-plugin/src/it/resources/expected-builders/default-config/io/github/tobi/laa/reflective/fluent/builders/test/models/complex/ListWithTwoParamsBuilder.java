package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ListWithTwoParamsBuilder<A, B> {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<ListWithTwoParams> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected ListWithTwoParamsBuilder(final Supplier<ListWithTwoParams> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static ListWithTwoParamsBuilder newInstance() {
    return new ListWithTwoParamsBuilder(ListWithTwoParams::new);
  }

  public static ListWithTwoParamsBuilder withSupplier(final Supplier<ListWithTwoParams> supplier) {
    return new ListWithTwoParamsBuilder(supplier);
  }

  public CollectionAll all() {
    return new CollectionAll();
  }

  public ListWithTwoParamsBuilder all(final List<Collection<? extends Map<A, B>>> all) {
    this.fieldValue.all = all;
    this.callSetterFor.all = true;
    return this;
  }

  public ListWithTwoParams build() {
    final ListWithTwoParams objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.all && this.fieldValue.all != null) {
      this.fieldValue.all.forEach(objectToBuild::addAll);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean all;
  }

  private class FieldValue {
    List<Collection<? extends Map<A, B>>> all;
  }

  public class CollectionAll {
    public CollectionAll add(final Collection<? extends Map<A, B>> item) {
      if (ListWithTwoParamsBuilder.this.fieldValue.all == null) {
        ListWithTwoParamsBuilder.this.fieldValue.all = new ArrayList<>();
      }
      ListWithTwoParamsBuilder.this.fieldValue.all.add(item);
      ListWithTwoParamsBuilder.this.callSetterFor.all = true;
      return this;
    }

    public ListWithTwoParamsBuilder and() {
      return ListWithTwoParamsBuilder.this;
    }
  }
}
