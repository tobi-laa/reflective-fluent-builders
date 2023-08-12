package io.github.tobi.laa.reflective.fluent.builders.test.models;

import java.lang.String;
import java.util.Objects;
import javax.annotation.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class CatBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private Cat objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private CatBuilder(final Cat objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static CatBuilder newInstance() {
    return new CatBuilder(null);
  }

  public static CatBuilder thatModifies(final Cat objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new CatBuilder(objectToModify);
  }

  public CatBuilder fur(final String fur) {
    fieldValue.fur = fur;
    callSetterFor.fur = true;
    return this;
  }

  public Cat build() {
    if (objectToBuild == null) {
      objectToBuild = new Cat();
    }
    if (callSetterFor.fur) {
      objectToBuild.setFur(fieldValue.fur);
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
