package com.github.tobi.laa.reflective.fluent.builders.mojo;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.Set;

@Data
public class HierarchyCollection {

    @Valid
    private Set<Exclude> excludes;
}
