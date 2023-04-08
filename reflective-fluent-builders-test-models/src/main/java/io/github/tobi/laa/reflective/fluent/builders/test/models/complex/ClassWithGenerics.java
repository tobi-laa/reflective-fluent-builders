package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import lombok.Data;

@Data
public class ClassWithGenerics<T> {

    private int anInt;

    private float[] floats;

    private T t;
}
