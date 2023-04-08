package io.github.tobi.laa.reflective.fluent.builders.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.lang.reflect.Type;

/**
 * <p>
 * A simple setter, meaning the field being set by it is no array, {@link java.util.Collection collection} or
 * {@link java.util.Map map}.
 * </p>
 */
@SuperBuilder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SimpleSetter extends AbstractSetter {
    
    @lombok.NonNull
    @EqualsAndHashCode.Include
    private final Type paramType;

    @Override
    public SimpleSetter withParamName(final String paramName) {
        return toBuilder().paramName(paramName).build();
    }
}