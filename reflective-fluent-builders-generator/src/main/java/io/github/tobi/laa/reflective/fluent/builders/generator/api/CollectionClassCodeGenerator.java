package io.github.tobi.laa.reflective.fluent.builders.generator.api;

import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.generator.model.CollectionClassSpec;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.model.Setter;

/**
 * <p>
 * Generates an {@link CollectionClassSpec inner class which provides convenience methods for fluently adding elements
 * to a collection, a map or an array} to be placed within the source code of a {@link BuilderMetadata builder}.
 * </p>
 */
public interface CollectionClassCodeGenerator {

    /**
     * <p>
     * Returns {@code true} if this generator is applicable for {@code setter} (meaning it can be used for generating an
     * inner class) and {@code false} otherwise.
     * </p>
     *
     * @param setter The setter for which to check whether this generator is applicable. Must not be
     *               {@code null}.
     * @return {@code true} if this generator is applicable for {@code setter} and {@code false} otherwise.
     */
    boolean isApplicable(final Setter setter);

    /**
     * <p>
     * Generates an {@link CollectionClassSpec inner class which provides convenience methods for fluently adding
     * elements to a collection, a map or an array} to be placed within the source code of a
     * {@link BuilderMetadata builder}.
     * </p>
     * <p>
     * Calling this method for the setter of a {@link java.util.List List} should for instance produce an inner class
     * with said convenience methods for adding elements to it via chained methods.
     * </p>
     *
     * @param builderMetadata The metadata of the builder for which to generate said inner class. Must not be
     *                        {@code null}.
     * @param setter          The setter for which to generate said inner class. Must not be {@code null}.
     * @return An inner class which provides convenience methods for fluently adding elements to a collection, a map or
     * an array
     * @throws CodeGenerationException If called for a setter for which this generator is not
     *                                 {@link #isApplicable(Setter)}.
     */
    CollectionClassSpec generate(final BuilderMetadata builderMetadata, final Setter setter);
}
