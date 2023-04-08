package io.github.tobi.laa.reflective.fluent.builders.test.models.nested;

import java.util.Objects;
import javax.annotation.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class TopLevelClassBuilder {
  private TopLevelClass objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private TopLevelClassBuilder(final TopLevelClass objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static TopLevelClassBuilder newInstance() {
    return new TopLevelClassBuilder(null);
  }

  public static TopLevelClassBuilder thatModifies(final TopLevelClass objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new TopLevelClassBuilder(objectToModify);
  }

  public TopLevelClassBuilder nestedNonStatic(final TopLevelClass.NestedNonStatic nestedNonStatic) {
    fieldValue.nestedNonStatic = nestedNonStatic;
    callSetterFor.nestedNonStatic = true;
    return this;
  }

  public TopLevelClassBuilder nestedPackagePrivate(
      final TopLevelClass.NestedPackagePrivateLevelOne nestedPackagePrivate) {
    fieldValue.nestedPackagePrivate = nestedPackagePrivate;
    callSetterFor.nestedPackagePrivate = true;
    return this;
  }

  public TopLevelClassBuilder nestedPublic(final TopLevelClass.NestedPublicLevelOne nestedPublic) {
    fieldValue.nestedPublic = nestedPublic;
    callSetterFor.nestedPublic = true;
    return this;
  }

  public TopLevelClass build() {
    if (objectToBuild == null) {
      objectToBuild = new TopLevelClass();
    }
    if (callSetterFor.nestedNonStatic) {
      objectToBuild.setNestedNonStatic(fieldValue.nestedNonStatic);
    }
    if (callSetterFor.nestedPackagePrivate) {
      objectToBuild.setNestedPackagePrivate(fieldValue.nestedPackagePrivate);
    }
    if (callSetterFor.nestedPublic) {
      objectToBuild.setNestedPublic(fieldValue.nestedPublic);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean nestedNonStatic;

    boolean nestedPackagePrivate;

    boolean nestedPublic;
  }

  private class FieldValue {
    TopLevelClass.NestedNonStatic nestedNonStatic;

    TopLevelClass.NestedPackagePrivateLevelOne nestedPackagePrivate;

    TopLevelClass.NestedPublicLevelOne nestedPublic;
  }
}
