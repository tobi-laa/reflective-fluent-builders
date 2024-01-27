package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.generics;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GenericChild<S extends Number, T> extends GenericParent<String, S, T> {

    @Override
    public void setList(List<String> list) {
        super.setList(list);
    }

    @Override
    public void setMap(Map<S, T> map) {
        super.setMap(map);
    }

    @Override
    public void setGeneric(Generic<T> generic) {
        super.setGeneric(generic);
    }

    @Override
    public List<? extends Number> getGenericList() {
        return Collections.emptyList();
    }
}
