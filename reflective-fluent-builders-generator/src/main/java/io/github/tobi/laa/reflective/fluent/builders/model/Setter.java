package io.github.tobi.laa.reflective.fluent.builders.model;

/**
 * <p>
 * Represents a single setter of a class for which a builder is going to be generated.
 * </p>
 */
public interface Setter extends WriteAccessor {

    /**
     * <p>
     * The name of the setter method, for instance {@code setAge}.
     * </p>
     *
     * @return The name of the method.
     */
    String getMethodName();

    /**
     * <p>
     * Creates a <em>new</em> {@link Setter} with all values kept the same except for {@code propertyName}.
     * </p>
     *
     * @param propertyName The new property name for the newly constructed {@link Setter}.
     * @return A new {@link Setter} with all values kept the same except for {@code paramName}.
     */
    @Override
    Setter withPropertyName(final String propertyName);
}
