package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy;

import lombok.AccessLevel;

public abstract class TopLevelSuperClass implements AnotherInterface {

    @lombok.Setter(AccessLevel.PACKAGE)
    private int six;

    @lombok.Setter(AccessLevel.PROTECTED)
    private int seven;
}
