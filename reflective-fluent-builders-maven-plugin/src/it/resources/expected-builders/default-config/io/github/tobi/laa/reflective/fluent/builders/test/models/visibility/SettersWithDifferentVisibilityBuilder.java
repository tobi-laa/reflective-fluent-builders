package io.github.tobi.laa.reflective.fluent.builders.test.models.visibility;

import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

/**
 * Builder for {@link SettersWithDifferentVisibility}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class SettersWithDifferentVisibilityBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<SettersWithDifferentVisibility> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link SettersWithDifferentVisibility} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected SettersWithDifferentVisibilityBuilder(
      final Supplier<SettersWithDifferentVisibility> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link SettersWithDifferentVisibilityBuilder} that will work on a new instance of {@link SettersWithDifferentVisibility} once {@link #build()} is called.
   */
  public static SettersWithDifferentVisibilityBuilder newInstance() {
    return new SettersWithDifferentVisibilityBuilder(SettersWithDifferentVisibility::new);
  }

  /**
   * Creates an instance of {@link SettersWithDifferentVisibilityBuilder} that will work on an instance of {@link SettersWithDifferentVisibility} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static SettersWithDifferentVisibilityBuilder withSupplier(
      final Supplier<SettersWithDifferentVisibility> supplier) {
    return new SettersWithDifferentVisibilityBuilder(supplier);
  }

  /**
   * Sets the value for the {@code packagePrivateSetter} property.
   * To be more precise, this will lead to {@link SettersWithDifferentVisibility#setPackagePrivateSetter(int)} being called on construction of the object.
   * @param packagePrivateSetter the value to set.
   * @return This builder for chained calls.
   */
  public SettersWithDifferentVisibilityBuilder packagePrivateSetter(
      final int packagePrivateSetter) {
    this.fieldValue.packagePrivateSetter = packagePrivateSetter;
    this.callSetterFor.packagePrivateSetter = true;
    return this;
  }

  /**
   * Sets the value for the {@code protectedSetter} property.
   * To be more precise, this will lead to {@link SettersWithDifferentVisibility#setProtectedSetter(int)} being called on construction of the object.
   * @param protectedSetter the value to set.
   * @return This builder for chained calls.
   */
  public SettersWithDifferentVisibilityBuilder protectedSetter(final int protectedSetter) {
    this.fieldValue.protectedSetter = protectedSetter;
    this.callSetterFor.protectedSetter = true;
    return this;
  }

  /**
   * Sets the value for the {@code publicSetter} property.
   * To be more precise, this will lead to {@link SettersWithDifferentVisibility#setPublicSetter(int)} being called on construction of the object.
   * @param publicSetter the value to set.
   * @return This builder for chained calls.
   */
  public SettersWithDifferentVisibilityBuilder publicSetter(final int publicSetter) {
    this.fieldValue.publicSetter = publicSetter;
    this.callSetterFor.publicSetter = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link SettersWithDifferentVisibility}.
   * @return The constructed instance. Never {@code null}.
   */
  public SettersWithDifferentVisibility build() {
    final SettersWithDifferentVisibility objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.packagePrivateSetter) {
      objectToBuild.setPackagePrivateSetter(this.fieldValue.packagePrivateSetter);
    }
    if (this.callSetterFor.protectedSetter) {
      objectToBuild.setProtectedSetter(this.fieldValue.protectedSetter);
    }
    if (this.callSetterFor.publicSetter) {
      objectToBuild.setPublicSetter(this.fieldValue.publicSetter);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean packagePrivateSetter;

    boolean protectedSetter;

    boolean publicSetter;
  }

  private class FieldValue {
    int packagePrivateSetter;

    int protectedSetter;

    int publicSetter;
  }
}
