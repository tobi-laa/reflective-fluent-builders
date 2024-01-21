package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class DirectFieldAccess {

    private int privateFieldNoSetter;

    int packagePrivateFieldNoSetter;

    protected int protectedFieldNoSetter;

    public int publicFieldNoSetter;

    @Setter(AccessLevel.PACKAGE)
    int packagePrivateFieldWithSetter;

    @Setter(AccessLevel.PROTECTED)
    protected int protectedFieldWithSetter;

    @Setter
    public int publicFieldWithSetter;

    @Getter(AccessLevel.PACKAGE)
    List<String> packagePrivateFieldWithGetAndAdd;

    @Getter(AccessLevel.PROTECTED)
    protected List<String> protectedFieldWithGetAndAdd;

    @Getter
    public List<String> publicFieldWithGetAndAdd;

    @Setter(AccessLevel.PRIVATE)
    public int publicFieldWithPrivateSetter;

    public final List<String> publicFinalFieldNoSetter = new ArrayList<>();
}
