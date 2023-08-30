package io.github.tobi.laa.reflective.fluent.builders.test.models.jaxb;

import java.lang.String;
import java.lang.SuppressWarnings;
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
  @SuppressWarnings("unused")
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
    this.fieldValue.key = key;
    this.callSetterFor.key = true;
    return this;
  }

  public EntryBuilder value(final PersonJaxb value) {
    this.fieldValue.value = value;
    this.callSetterFor.value = true;
    return this;
  }

  public PersonJaxb.Relations.Entry build() {
    if (this.objectToBuild == null) {
      this.objectToBuild = new PersonJaxb.Relations.Entry();
    }
    if (this.callSetterFor.key) {
      this.objectToBuild.setKey(this.fieldValue.key);
    }
    if (this.callSetterFor.value) {
      this.objectToBuild.setValue(this.fieldValue.value);
    }
    return this.objectToBuild;
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
