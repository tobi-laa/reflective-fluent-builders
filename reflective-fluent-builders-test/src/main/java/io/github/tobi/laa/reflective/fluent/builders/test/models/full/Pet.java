package io.github.tobi.laa.reflective.fluent.builders.test.models.full;

import lombok.Data;

import java.util.SortedSet;

@Data
public class Pet {

    private String fullName;

    private float weight;

    private SortedSet<Pet> siblings;

    private Person owner;
}
