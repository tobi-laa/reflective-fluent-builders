package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

public class MapWithThreeParams<A, B, C> extends HashMap<A, Map<B, C>> {
    @Serial
    private static final long serialVersionUID = 6511712322776760125L;
}