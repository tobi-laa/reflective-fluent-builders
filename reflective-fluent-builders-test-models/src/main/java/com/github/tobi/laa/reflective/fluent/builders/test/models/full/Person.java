package com.github.tobi.laa.reflective.fluent.builders.test.models.full;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"rawtypes", "unused"})
@Data
public class Person {

    private String[] names;

    private int age;

    private boolean married;

    private Set<Pet> pets;

    private List attributes;

    private Map<String, Person> relations;

    public void setMarried(final String married) {
        this.married = Boolean.parseBoolean(married);
    }

    public void setMarried(final boolean married) {
        this.married = married;
    }
}
