package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class BuiltClassAsMemberBuilder0 {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private Supplier<BuiltClassAsMemberBuilder.BuiltClassAsMember> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected BuiltClassAsMemberBuilder0(
      final Supplier<BuiltClassAsMemberBuilder.BuiltClassAsMember> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static BuiltClassAsMemberBuilder0 newInstance() {
    return new BuiltClassAsMemberBuilder0(BuiltClassAsMemberBuilder.BuiltClassAsMember::new);
  }

  public static BuiltClassAsMemberBuilder0 withSupplier(
      final Supplier<BuiltClassAsMemberBuilder.BuiltClassAsMember> supplier) {
    return new BuiltClassAsMemberBuilder0(supplier);
  }

  public BuiltClassAsMemberBuilder0 aField(final int aField) {
    this.fieldValue.aField = aField;
    this.callSetterFor.aField = true;
    return this;
  }

  public BuiltClassAsMemberBuilder.BuiltClassAsMember build() {
    final BuiltClassAsMemberBuilder.BuiltClassAsMember objectToBuild = objectSupplier.get();
    if (this.callSetterFor.aField) {
      objectToBuild.setAField(this.fieldValue.aField);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean aField;
  }

  private class FieldValue {
    int aField;
  }
}
