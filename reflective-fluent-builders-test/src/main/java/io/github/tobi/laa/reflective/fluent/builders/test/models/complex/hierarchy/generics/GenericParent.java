package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.generics;

import lombok.Setter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Setter
@SuppressWarnings("unused")
public class GenericParent<R, S, T> {

    private List<R> list;

    private Map<S, T> map;

    private Generic<T> generic;

    private Generic<R> otherGeneric;

    @SuppressWarnings("java:S1452")
    public List<? extends Serializable> getGenericList() {
        return Collections.emptyList();
    }
}
