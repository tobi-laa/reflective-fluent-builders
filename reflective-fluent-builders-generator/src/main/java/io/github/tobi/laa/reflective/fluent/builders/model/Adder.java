package io.github.tobi.laa.reflective.fluent.builders.model;

import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * Represents a single adder of a class for which a builder is going to be generated.
 * </p>
 */
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
public class Adder extends AbstractMethodAccessor {

    /**
     * <p>
     * Creates a <em>new</em> {@link Adder} with all values kept the same except for {@code propertyName}.
     * </p>
     *
     * @param propertyName The new property name for the newly constructed {@link Adder}.
     * @return A new {@link Adder} with all values kept the same except for {@code paramName}.
     */
    @Override
    public Adder withPropertyName(final String propertyName) {
        return toBuilder().propertyName(propertyName).build();
    }
}
