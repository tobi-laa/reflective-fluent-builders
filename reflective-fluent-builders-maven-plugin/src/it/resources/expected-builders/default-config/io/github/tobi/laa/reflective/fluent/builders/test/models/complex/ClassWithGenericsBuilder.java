package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import java.lang.Float;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class ClassWithGenericsBuilder<T> {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private ClassWithGenerics objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected ClassWithGenericsBuilder(final ClassWithGenerics objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  protected ClassWithGenericsBuilder() {
    // noop
  }

  public static ClassWithGenericsBuilder newInstance() {
    return new ClassWithGenericsBuilder();
  }

  public static ClassWithGenericsBuilder thatModifies(final ClassWithGenerics objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new ClassWithGenericsBuilder(objectToModify);
  }

  public ArrayFloats floats() {
    return new ArrayFloats();
  }

  public ClassWithGenericsBuilder anInt(final int anInt) {
    this.fieldValue.anInt = anInt;
    this.callSetterFor.anInt = true;
    return this;
  }

  public ClassWithGenericsBuilder floats(final float[] floats) {
    this.fieldValue.floats = floats;
    this.callSetterFor.floats = true;
    return this;
  }

  public ClassWithGenericsBuilder t(final T t) {
    this.fieldValue.t = t;
    this.callSetterFor.t = true;
    return this;
  }

  public ClassWithGenerics build() {
    if (this.objectToBuild == null) {
      this.objectToBuild = new ClassWithGenerics();
    }
    if (this.callSetterFor.anInt) {
      this.objectToBuild.setAnInt(this.fieldValue.anInt);
    }
    if (this.callSetterFor.floats) {
      this.objectToBuild.setFloats(this.fieldValue.floats);
    }
    if (this.callSetterFor.t) {
      this.objectToBuild.setT(this.fieldValue.t);
    }
    return this.objectToBuild;
  }

  private class CallSetterFor {
    boolean anInt;

    boolean floats;

    boolean t;
  }

  private class FieldValue {
    int anInt;

    float[] floats;

    T t;
  }

  public class ArrayFloats {
    private List<Float> list;

    public ArrayFloats add(final float item) {
      if (this.list == null) {
        this.list = new ArrayList<>();
      }
      this.list.add(item);
      ClassWithGenericsBuilder.this.callSetterFor.floats = true;
      return this;
    }

    public ClassWithGenericsBuilder and() {
      if (this.list != null) {
        ClassWithGenericsBuilder.this.fieldValue.floats = new float[this.list.size()];
        for (int i = 0; i < this.list.size(); i++) {
          ClassWithGenericsBuilder.this.fieldValue.floats[i] = this.list.get(i);
        }
      }
      return ClassWithGenericsBuilder.this;
    }
  }
}
