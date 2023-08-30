package io.github.tobi.laa.reflective.fluent.builders.test.models;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Objects;
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

  private Cat objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected CatBuilder(final Cat objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  protected CatBuilder() {
    // noop
  }

  public static CatBuilder newInstance() {
    return new CatBuilder();
  }

  public static CatBuilder thatModifies(final Cat objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new CatBuilder(objectToModify);
  }

  public CatBuilder fur(final String fur) {
    this.fieldValue.fur = fur;
    this.callSetterFor.fur = true;
    return this;
  }

  public Cat build() {
    if (this.objectToBuild == null) {
      this.objectToBuild = new Cat();
    }
    if (this.callSetterFor.fur) {
      this.objectToBuild.setFur(this.fieldValue.fur);
    }
    return this.objectToBuild;
  }

  private class CallSetterFor {
    boolean fur;
  }

  private class FieldValue {
    String fur;
  }
}
