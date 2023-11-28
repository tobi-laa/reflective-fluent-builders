package io.github.tobi.laa.reflective.fluent.builders.test.models.nested;

import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class TopLevelClassBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<TopLevelClass> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected TopLevelClassBuilder(final Supplier<TopLevelClass> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static TopLevelClassBuilder newInstance() {
    return new TopLevelClassBuilder(TopLevelClass::new);
  }

  public static TopLevelClassBuilder withSupplier(final Supplier<TopLevelClass> supplier) {
    return new TopLevelClassBuilder(supplier);
  }

  public TopLevelClassBuilder nestedNonStatic(final TopLevelClass.NestedNonStatic nestedNonStatic) {
    this.fieldValue.nestedNonStatic = nestedNonStatic;
    this.callSetterFor.nestedNonStatic = true;
    return this;
  }

  public TopLevelClassBuilder nestedPackagePrivate(
      final TopLevelClass.NestedPackagePrivateLevelOne nestedPackagePrivate) {
    this.fieldValue.nestedPackagePrivate = nestedPackagePrivate;
    this.callSetterFor.nestedPackagePrivate = true;
    return this;
  }

  public TopLevelClassBuilder nestedProtected(
      final TopLevelClass.NestedProtectedLevelOne nestedProtected) {
    this.fieldValue.nestedProtected = nestedProtected;
    this.callSetterFor.nestedProtected = true;
    return this;
  }

  public TopLevelClassBuilder nestedPublic(final TopLevelClass.NestedPublicLevelOne nestedPublic) {
    this.fieldValue.nestedPublic = nestedPublic;
    this.callSetterFor.nestedPublic = true;
    return this;
  }

  public TopLevelClass build() {
    final TopLevelClass objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.nestedNonStatic) {
      objectToBuild.setNestedNonStatic(this.fieldValue.nestedNonStatic);
    }
    if (this.callSetterFor.nestedPackagePrivate) {
      objectToBuild.setNestedPackagePrivate(this.fieldValue.nestedPackagePrivate);
    }
    if (this.callSetterFor.nestedProtected) {
      objectToBuild.setNestedProtected(this.fieldValue.nestedProtected);
    }
    if (this.callSetterFor.nestedPublic) {
      objectToBuild.setNestedPublic(this.fieldValue.nestedPublic);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean nestedNonStatic;

    boolean nestedPackagePrivate;

    boolean nestedProtected;

    boolean nestedPublic;
  }

  private class FieldValue {
    TopLevelClass.NestedNonStatic nestedNonStatic;

    TopLevelClass.NestedPackagePrivateLevelOne nestedPackagePrivate;

    TopLevelClass.NestedProtectedLevelOne nestedProtected;

    TopLevelClass.NestedPublicLevelOne nestedPublic;
  }
}
