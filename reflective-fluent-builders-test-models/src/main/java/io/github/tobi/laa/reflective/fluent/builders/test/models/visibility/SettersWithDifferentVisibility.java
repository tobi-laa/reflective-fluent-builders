package io.github.tobi.laa.reflective.fluent.builders.test.models.visibility;

import lombok.AccessLevel;
import lombok.Setter;

public class SettersWithDifferentVisibility {

    @Setter(AccessLevel.PRIVATE)
    private int privateSetter;

    @Setter(AccessLevel.PROTECTED)
    private int protectedSetter;

    @Setter(AccessLevel.PACKAGE)
    private int packagePrivateSetter;

    @Setter(AccessLevel.PUBLIC)
    private int publicSetter;
}
