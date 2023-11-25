package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import java.lang.Float;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
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

  private Supplier<ClassWithGenerics> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected ClassWithGenericsBuilder(final Supplier<ClassWithGenerics> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static ClassWithGenericsBuilder newInstance() {
    return new ClassWithGenericsBuilder(ClassWithGenerics::new);
  }

  public static ClassWithGenericsBuilder withSupplier(final Supplier<ClassWithGenerics> supplier) {
    return new ClassWithGenericsBuilder(supplier);
  }

  public ArrayFloats floats() {
    return new ArrayFloats();
  }

  public CollectionList list() {
    return new CollectionList();
  }

  public ClassWithGenericsBuilder anInt(final int anInt) {
    this.fieldValue.anInt = anInt;
    this.callSetterFor.anInt = true;
    return this;
  }

  public ClassWithGenericsBuilder bar(final ClassWithGenerics.Foo<T> bar) {
    this.fieldValue.bar = bar;
    this.callSetterFor.bar = true;
    return this;
  }

  public ClassWithGenericsBuilder floats(final float[] floats) {
    this.fieldValue.floats = floats;
    this.callSetterFor.floats = true;
    return this;
  }

  public ClassWithGenericsBuilder list(final List<T> list) {
    this.fieldValue.list = list;
    this.callSetterFor.list = true;
    return this;
  }

  public ClassWithGenericsBuilder t(final T t) {
    this.fieldValue.t = t;
    this.callSetterFor.t = true;
    return this;
  }

  public ClassWithGenerics build() {
    final ClassWithGenerics objectToBuild = objectSupplier.get();
    if (this.callSetterFor.anInt) {
      objectToBuild.setAnInt(this.fieldValue.anInt);
    }
    if (this.callSetterFor.bar) {
      objectToBuild.setBar(this.fieldValue.bar);
    }
    if (this.callSetterFor.floats) {
      objectToBuild.setFloats(this.fieldValue.floats);
    }
    if (this.callSetterFor.list) {
      objectToBuild.setList(this.fieldValue.list);
    }
    if (this.callSetterFor.t) {
      objectToBuild.setT(this.fieldValue.t);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean anInt;

    boolean bar;

    boolean floats;

    boolean list;

    boolean t;
  }

  private class FieldValue {
    int anInt;

    ClassWithGenerics.Foo<T> bar;

    float[] floats;

    List<T> list;

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

  public class CollectionList {
    public CollectionList add(final T item) {
      if (ClassWithGenericsBuilder.this.fieldValue.list == null) {
        ClassWithGenericsBuilder.this.fieldValue.list = new ArrayList<>();
      }
      ClassWithGenericsBuilder.this.fieldValue.list.add(item);
      ClassWithGenericsBuilder.this.callSetterFor.list = true;
      return this;
    }

    public ClassWithGenericsBuilder and() {
      return ClassWithGenericsBuilder.this;
    }
  }
}
