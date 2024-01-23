package io.github.tobi.laa.reflective.fluent.builders.model;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * Represents a single adder of a class for which a builder is going to be generated.
 * </p>
 */
@SuperBuilder(toBuilder = true)
@Getter
@ToString(callSuper = true)
public class Adder extends AbstractMethodAccessor {

    /**
     * <p>
     * The name of the parameter of the adder method, for instance {@code item}. This differs from
     * {@link #getPropertyName() propertyName} in that the latter is the name of the property that is modified by the
     * adder method, for instance {@code items}, whereas this is the name of the parameter of the adder method.
     * </p>
     */
    @lombok.NonNull
    private final String paramName;

    /**
     * <p>
     * The type of the parameter of the adder method, for instance {@code String}. This differs from
     * {@link #getPropertyType() propertyType} in that the latter is the type of the property that is modified by the
     * adder method, for instance {@code List<String>}, whereas this is the name of the parameter of the adder method.
     * </p>
     */
    @lombok.NonNull
    private final PropertyType paramType;

    /**
     * <p>
     * Creates a <em>new</em> {@link Adder} with all values kept the same except for {@code propertyName}.
     * </p>
     *
     * @param propertyName The new property name for the newly constructed {@link Adder}.
     * @return A new {@link Adder} with all values kept the same except for {@code propertyName}.
     */
    @Override
    public Adder withPropertyName(final String propertyName) {
        return toBuilder().propertyName(propertyName).build();
    }
}
