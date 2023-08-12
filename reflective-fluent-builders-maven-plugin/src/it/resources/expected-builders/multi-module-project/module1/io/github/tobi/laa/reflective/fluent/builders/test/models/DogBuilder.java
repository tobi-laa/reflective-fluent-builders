package io.github.tobi.laa.reflective.fluent.builders.test.models;

import java.lang.String;
import java.util.Objects;
import javax.annotation.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class DogBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private Dog objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private DogBuilder(final Dog objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static DogBuilder newInstance() {
    return new DogBuilder(null);
  }

  public static DogBuilder thatModifies(final Dog objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new DogBuilder(objectToModify);
  }

  public DogBuilder name(final String name) {
    fieldValue.name = name;
    callSetterFor.name = true;
    return this;
  }

  public Dog build() {
    if (objectToBuild == null) {
      objectToBuild = new Dog();
    }
    if (callSetterFor.name) {
      objectToBuild.setName(fieldValue.name);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean name;
  }

  private class FieldValue {
    String name;
  }
}