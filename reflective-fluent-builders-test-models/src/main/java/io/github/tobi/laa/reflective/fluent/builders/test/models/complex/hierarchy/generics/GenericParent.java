package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.generics;

import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
public class GenericParent<R, S, T> {

    private List<R> list;

    private Map<S, T> map;

    private Generic<T> generic;

    private Generic<R> otherGeneric;
}
