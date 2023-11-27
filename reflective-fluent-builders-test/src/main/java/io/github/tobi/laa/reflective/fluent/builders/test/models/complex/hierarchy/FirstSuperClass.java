package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy;

import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.second.SecondSuperClassInDifferentPackage;
import lombok.AccessLevel;

@lombok.Setter
public class FirstSuperClass extends SecondSuperClassInDifferentPackage {

    @lombok.Setter(AccessLevel.PACKAGE)
    private int two;
}
