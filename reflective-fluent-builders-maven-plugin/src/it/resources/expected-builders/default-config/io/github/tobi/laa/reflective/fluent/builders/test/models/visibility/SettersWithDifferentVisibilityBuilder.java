package io.github.tobi.laa.reflective.fluent.builders.test.models.visibility;

import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class SettersWithDifferentVisibilityBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private SettersWithDifferentVisibility objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected SettersWithDifferentVisibilityBuilder(
      final SettersWithDifferentVisibility objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static SettersWithDifferentVisibilityBuilder newInstance() {
    return new SettersWithDifferentVisibilityBuilder(null);
  }

  public static SettersWithDifferentVisibilityBuilder thatModifies(
      final SettersWithDifferentVisibility objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new SettersWithDifferentVisibilityBuilder(objectToModify);
  }

  public SettersWithDifferentVisibilityBuilder packagePrivateSetter(
      final int packagePrivateSetter) {
    fieldValue.packagePrivateSetter = packagePrivateSetter;
    callSetterFor.packagePrivateSetter = true;
    return this;
  }

  public SettersWithDifferentVisibilityBuilder protectedSetter(final int protectedSetter) {
    fieldValue.protectedSetter = protectedSetter;
    callSetterFor.protectedSetter = true;
    return this;
  }

  public SettersWithDifferentVisibilityBuilder publicSetter(final int publicSetter) {
    fieldValue.publicSetter = publicSetter;
    callSetterFor.publicSetter = true;
    return this;
  }

  public SettersWithDifferentVisibility build() {
    if (objectToBuild == null) {
      objectToBuild = new SettersWithDifferentVisibility();
    }
    if (callSetterFor.packagePrivateSetter) {
      objectToBuild.setPackagePrivateSetter(fieldValue.packagePrivateSetter);
    }
    if (callSetterFor.protectedSetter) {
      objectToBuild.setProtectedSetter(fieldValue.protectedSetter);
    }
    if (callSetterFor.publicSetter) {
      objectToBuild.setPublicSetter(fieldValue.publicSetter);
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
