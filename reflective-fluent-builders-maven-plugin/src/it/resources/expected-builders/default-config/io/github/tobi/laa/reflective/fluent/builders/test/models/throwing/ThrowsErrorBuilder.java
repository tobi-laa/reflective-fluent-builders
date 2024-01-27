package io.github.tobi.laa.reflective.fluent.builders.test.models.throwing;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ThrowsErrorBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<ThrowsError> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected ThrowsErrorBuilder(final Supplier<ThrowsError> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static ThrowsErrorBuilder newInstance() {
    return new ThrowsErrorBuilder(ThrowsError::new);
  }

  public static ThrowsErrorBuilder withSupplier(final Supplier<ThrowsError> supplier) {
    return new ThrowsErrorBuilder(supplier);
  }

  public ThrowsErrorBuilder aString(final String aString) {
    this.fieldValue.aString = aString;
    this.callSetterFor.aString = true;
    return this;
  }

  public ThrowsError build() {
    final ThrowsError objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.aString) {
      objectToBuild.setAString(this.fieldValue.aString);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean aString;
  }

  private class FieldValue {
    String aString;
  }
}
