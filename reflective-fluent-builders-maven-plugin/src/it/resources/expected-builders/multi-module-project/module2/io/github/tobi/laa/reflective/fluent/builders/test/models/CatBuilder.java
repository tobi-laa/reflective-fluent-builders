package io.github.tobi.laa.reflective.fluent.builders.test.models;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class CatBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private Supplier<Cat> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected CatBuilder(final Supplier<Cat> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static CatBuilder newInstance() {
    return new CatBuilder(Cat::new);
  }

  public static CatBuilder withSupplier(final Supplier<Cat> supplier) {
    return new CatBuilder(supplier);
  }

  public CatBuilder fur(final String fur) {
    this.fieldValue.fur = fur;
    this.callSetterFor.fur = true;
    return this;
  }

  public Cat build() {
    final Cat objectToBuild = objectSupplier.get();
    if (this.callSetterFor.fur) {
      objectToBuild.setFur(this.fieldValue.fur);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean fur;
  }

  private class FieldValue {
    String fur;
  }
}
