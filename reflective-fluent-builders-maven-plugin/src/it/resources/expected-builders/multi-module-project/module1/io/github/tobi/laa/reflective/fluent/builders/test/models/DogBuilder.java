package io.github.tobi.laa.reflective.fluent.builders.test.models;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class DogBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private Dog objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected DogBuilder(final Dog objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  protected DogBuilder() {
    // noop
  }

  public static DogBuilder newInstance() {
    return new DogBuilder();
  }

  public static DogBuilder thatModifies(final Dog objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new DogBuilder(objectToModify);
  }

  public DogBuilder name(final String name) {
    this.fieldValue.name = name;
    this.callSetterFor.name = true;
    return this;
  }

  public Dog build() {
    if (this.objectToBuild == null) {
      this.objectToBuild = new Dog();
    }
    if (this.callSetterFor.name) {
      this.objectToBuild.setName(this.fieldValue.name);
    }
    return this.objectToBuild;
  }

  private class CallSetterFor {
    boolean name;
  }

  private class FieldValue {
    String name;
  }
}
