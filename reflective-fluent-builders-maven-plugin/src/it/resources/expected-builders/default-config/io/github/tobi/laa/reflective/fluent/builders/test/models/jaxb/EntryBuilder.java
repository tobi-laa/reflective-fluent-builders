package io.github.tobi.laa.reflective.fluent.builders.test.models.jaxb;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
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

  private Supplier<PersonJaxb.Relations.Entry> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected EntryBuilder(final Supplier<PersonJaxb.Relations.Entry> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static EntryBuilder newInstance() {
    return new EntryBuilder(PersonJaxb.Relations.Entry::new);
  }

  public static EntryBuilder withSupplier(final Supplier<PersonJaxb.Relations.Entry> supplier) {
    return new EntryBuilder(supplier);
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
    final PersonJaxb.Relations.Entry objectToBuild = objectSupplier.get();
    if (this.callSetterFor.key) {
      objectToBuild.setKey(this.fieldValue.key);
    }
    if (this.callSetterFor.value) {
      objectToBuild.setValue(this.fieldValue.value);
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
