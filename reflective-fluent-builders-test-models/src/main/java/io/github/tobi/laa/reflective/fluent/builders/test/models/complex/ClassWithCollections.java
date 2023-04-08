package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import java.util.*;

@lombok.Setter
public class ClassWithCollections<T, U> {

    private Collection<Integer> ints;

    private List list;

    private java.util.Set<List> set;

    private Deque<? extends Object> deque;

    private SortedSet<?> sortedSetWild;

    private float[] floats;

    private Map<String, Object> map;

    private Map<T, U> mapTU;

    private Map<?, Object> mapWildObj;

    private Map mapNoTypeArgs;
}
