package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import lombok.Data;

import java.util.List;

@Data
public class ClassWithGenerics<T> {

    private int anInt;

    private float[] floats;

    private T t;

    private List<T> list;

    private Foo<T> bar;

    static class Foo<T> {
        // no content
    }
}
