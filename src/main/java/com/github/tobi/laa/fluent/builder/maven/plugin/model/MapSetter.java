package com.github.tobi.laa.fluent.builder.maven.plugin.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 *     The setter of a {@link java.util.Map map}.
 * </p>
 */
@SuperBuilder
@Data
public class MapSetter extends AbstractSetter {

    /**
     * <p>
     *     The type of the key of the elements within the map being set by the setter method's single parameter, for
     *     instance {@code Integer.class} for {@code Map<Integer, String>}.
     * </p>
     */
    @lombok.NonNull
    private final Class<?> keyType;


    /**
     * <p>
     *     The type of the value of the elements within the map being set by the setter method's single parameter, for
     *     instance {@code String.class} for {@code Map<Integer, String>}.
     * </p>
     */
    @lombok.NonNull
    private final Class<?> valueType;
}
