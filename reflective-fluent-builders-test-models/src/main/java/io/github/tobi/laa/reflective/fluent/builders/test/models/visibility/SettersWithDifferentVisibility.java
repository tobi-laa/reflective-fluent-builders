package io.github.tobi.laa.reflective.fluent.builders.test.models.visibility;

import lombok.AccessLevel;
import lombok.Setter;

public class SettersWithDifferentVisibility {

    @Setter(AccessLevel.PRIVATE)
    private int privateSetter;

    @Setter(AccessLevel.PACKAGE)
    private int protectedSetter;

    @Setter(AccessLevel.PACKAGE)
    private int packagePrivateSetter;

    @Setter(AccessLevel.PROTECTED)
    private int publicSetter;
}
