package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class WithAdders {

    private List<String> hasNoAdders;

    private List<String> hasAdders;

    private Set<List<Object>> alsoHasAdders;

    private List<Map<String, String>> hasInaccessibleAdders;

    public void addHasAdder(final String hasAdder) {
        this.hasAdders.add(hasAdder);
    }

    void addAlsoHasAdder(final List<Object> alsoHasAdder) {
        this.alsoHasAdders.add(alsoHasAdder);
    }

    private void addHasInaccessibleAdder(final Map<String, String> hasInaccessibleAdder) {
        this.hasInaccessibleAdders.add(hasInaccessibleAdder);
    }
}