package com.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Map;

@lombok.Setter
public class ClassWithCollections<T> {

    private Collection<Integer> ints;

    private List list;

    private java.util.Set<List> set;

    private Deque<?> deque;

    private float[] floats;

    private Map<String, Object> map;

    private Map<?, T> mapWildT;

    private Map mapNoTypeArgs;
}
