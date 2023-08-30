package io.github.tobi.laa.reflective.fluent.builders.test.models.visibility;

import java.lang.SuppressWarnings;
import java.util.Objects;
import javax.annotation.Generated;

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

  private SettersWithDifferentVisibility objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected SettersWithDifferentVisibilityBuilder(
      final SettersWithDifferentVisibility objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  protected SettersWithDifferentVisibilityBuilder() {
    // noop
  }

  public static SettersWithDifferentVisibilityBuilder newInstance() {
    return new SettersWithDifferentVisibilityBuilder();
  }

  public static SettersWithDifferentVisibilityBuilder thatModifies(
      final SettersWithDifferentVisibility objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new SettersWithDifferentVisibilityBuilder(objectToModify);
  }

  public SettersWithDifferentVisibilityBuilder packagePrivateSetter(
      final int packagePrivateSetter) {
    this.fieldValue.packagePrivateSetter = packagePrivateSetter;
    this.callSetterFor.packagePrivateSetter = true;
    return this;
  }

  public SettersWithDifferentVisibilityBuilder protectedSetter(final int protectedSetter) {
    this.fieldValue.protectedSetter = protectedSetter;
    this.callSetterFor.protectedSetter = true;
    return this;
  }

  public SettersWithDifferentVisibilityBuilder publicSetter(final int publicSetter) {
    this.fieldValue.publicSetter = publicSetter;
    this.callSetterFor.publicSetter = true;
    return this;
  }

  public SettersWithDifferentVisibility build() {
    if (this.objectToBuild == null) {
      this.objectToBuild = new SettersWithDifferentVisibility();
    }
    if (this.callSetterFor.packagePrivateSetter) {
      this.objectToBuild.setPackagePrivateSetter(this.fieldValue.packagePrivateSetter);
    }
    if (this.callSetterFor.protectedSetter) {
      this.objectToBuild.setProtectedSetter(this.fieldValue.protectedSetter);
    }
    if (this.callSetterFor.publicSetter) {
      this.objectToBuild.setPublicSetter(this.fieldValue.publicSetter);
    }
    return this.objectToBuild;
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
