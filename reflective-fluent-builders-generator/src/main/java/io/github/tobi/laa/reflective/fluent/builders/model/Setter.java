package io.github.tobi.laa.reflective.fluent.builders.model;

import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * Represents a single setter of a class for which a builder is going to be generated.
 * </p>
 */
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
public class Setter extends AbstractMethodAccessor {

    /**
     * <p>
     * Creates a <em>new</em> {@link Setter} with all values kept the same except for {@code propertyName}.
     * </p>
     *
     * @param propertyName The new property name for the newly constructed {@link Setter}.
     * @return A new {@link Setter} with all values kept the same except for {@code propertyName}.
     */
    @Override
    public Setter withPropertyName(final String propertyName) {
        return toBuilder().propertyName(propertyName).build();
    }
}
