package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.second;

import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.TopLevelSuperClass;
import lombok.AccessLevel;

public class SecondSuperClassInDifferentPackage extends TopLevelSuperClass {

    @lombok.Setter
    private int four;

    @lombok.Setter(AccessLevel.PACKAGE)
    private int five;
}
