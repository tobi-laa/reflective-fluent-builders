package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.bridgemethod;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract class BridgeMethodAbstract<T> implements BridgeMethodInterface<T> {

    private T something;
}
