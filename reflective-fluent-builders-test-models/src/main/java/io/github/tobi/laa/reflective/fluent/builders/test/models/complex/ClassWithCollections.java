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

    private ListWithTwoParams<String, Integer> listWithTwoParams;

    private MapWithThreeParams<String, Integer, Boolean> mapWithThreeParams;

    static class ListWithTwoParams<A, B> extends ArrayList<Map<A, B>> {
        private static final long serialVersionUID = 155076095692605279L;
    }

    static class MapWithThreeParams<A, B, C> extends HashMap<A, Map<B, C>> {
        private static final long serialVersionUID = 6511712322776760125L;
    }
}
