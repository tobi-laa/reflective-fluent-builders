package io.github.tobi.laa.reflective.fluent.builders.test.models.jaxb;

import java.lang.String;
import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class EntryBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private PersonJaxb.Relations.Entry objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected EntryBuilder(final PersonJaxb.Relations.Entry objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  protected EntryBuilder() {
    // noop
  }

  public static EntryBuilder newInstance() {
    return new EntryBuilder();
  }

  public static EntryBuilder thatModifies(final PersonJaxb.Relations.Entry objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new EntryBuilder(objectToModify);
  }

  public EntryBuilder key(final String key) {
    fieldValue.key = key;
    callSetterFor.key = true;
    return this;
  }

  public EntryBuilder value(final PersonJaxb value) {
    fieldValue.value = value;
    callSetterFor.value = true;
    return this;
  }

  public PersonJaxb.Relations.Entry build() {
    if (objectToBuild == null) {
      objectToBuild = new PersonJaxb.Relations.Entry();
    }
    if (callSetterFor.key) {
      objectToBuild.setKey(fieldValue.key);
    }
    if (callSetterFor.value) {
      objectToBuild.setValue(fieldValue.value);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean key;

    boolean value;
  }

  private class FieldValue {
    String key;

    PersonJaxb value;
  }
}
