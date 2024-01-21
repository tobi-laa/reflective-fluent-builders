package io.github.tobi.laa.reflective.fluent.builders.model;

import lombok.Data;

import java.lang.reflect.Type;

/**
 * <p>
 * A simple property type, meaning it is no array, {@link java.util.Collection collection} or {@link java.util.Map map}.
 * </p>
 */
@Data
public class SimpleType implements PropertyType {

    @lombok.NonNull
    private final Type type;
}
