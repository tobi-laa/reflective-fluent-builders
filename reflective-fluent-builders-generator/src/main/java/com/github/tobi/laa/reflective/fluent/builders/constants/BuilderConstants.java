package com.github.tobi.laa.reflective.fluent.builders.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Holds constants used by multiples services, configuration or mojos.
 * </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BuilderConstants {

    public static final String PACKAGE_PLACEHOLDER = "<PACKAGE_NAME>";

    private static final Set<Class<?>> SUPPORTED_MAPS = Set.of( //
            Map.class, HashMap.class
    );
}
