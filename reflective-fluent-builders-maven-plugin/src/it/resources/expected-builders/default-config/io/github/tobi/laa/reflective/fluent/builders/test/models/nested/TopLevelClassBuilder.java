package io.github.tobi.laa.reflective.fluent.builders.test.models.nested;

import java.lang.SuppressWarnings;
import java.util.Objects;
import javax.annotation.Generated;

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

  private TopLevelClass objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected TopLevelClassBuilder(final TopLevelClass objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  protected TopLevelClassBuilder() {
    // noop
  }

  public static TopLevelClassBuilder newInstance() {
    return new TopLevelClassBuilder();
  }

  public static TopLevelClassBuilder thatModifies(final TopLevelClass objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new TopLevelClassBuilder(objectToModify);
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
    if (this.objectToBuild == null) {
      this.objectToBuild = new TopLevelClass();
    }
    if (this.callSetterFor.nestedNonStatic) {
      this.objectToBuild.setNestedNonStatic(this.fieldValue.nestedNonStatic);
    }
    if (this.callSetterFor.nestedPackagePrivate) {
      this.objectToBuild.setNestedPackagePrivate(this.fieldValue.nestedPackagePrivate);
    }
    if (this.callSetterFor.nestedProtected) {
      this.objectToBuild.setNestedProtected(this.fieldValue.nestedProtected);
    }
    if (this.callSetterFor.nestedPublic) {
      this.objectToBuild.setNestedPublic(this.fieldValue.nestedPublic);
    }
    return this.objectToBuild;
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
