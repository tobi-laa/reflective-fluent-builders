package com.github.tobi.laa.reflective.fluent.builders.generator.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.lang.reflect.Type;

/**
 * <p>
 * The setter of a {@link java.util.Map map}.
 * </p>
 */
@SuperBuilder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MapSetter extends AbstractSetter {

    /**
     * <p>
     * The type of the key of the elements within the map being set by the setter method's single parameter, for
     * instance {@code Integer.class} for {@code Map<Integer, String>}.
     * </p>
     */
    @lombok.NonNull
    private final Type keyType;


    /**
     * <p>
     * The type of the value of the elements within the map being set by the setter method's single parameter, for
     * instance {@code String.class} for {@code Map<Integer, String>}.
     * </p>
     */
    @lombok.NonNull
    private final Type valueType;

    @Override
    public MapSetter withParamName(final String paramName) {
        return toBuilder().paramName(paramName).build();
    }
}
