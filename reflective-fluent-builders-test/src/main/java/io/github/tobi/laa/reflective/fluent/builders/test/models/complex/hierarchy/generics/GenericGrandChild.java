package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.generics;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GenericGrandChild extends GenericChild<Long, Boolean> {

    @Override
    public void setMap(Map<Long, Boolean> map) {
        super.setMap(map);
    }

    @Override
    public void setGeneric(Generic<Boolean> generic) {
        super.setGeneric(generic);
    }

    @Override
    public List<Long> getGenericList() {
        return Collections.emptyList();
    }
}
