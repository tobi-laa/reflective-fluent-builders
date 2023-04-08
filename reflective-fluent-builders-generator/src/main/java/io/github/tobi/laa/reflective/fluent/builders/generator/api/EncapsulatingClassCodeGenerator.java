package io.github.tobi.laa.reflective.fluent.builders.generator.api;

import io.github.tobi.laa.reflective.fluent.builders.generator.model.EncapsulatingClassSpec;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;

/**
 * <p>
 * Generates an {@link EncapsulatingClassSpec encapsulating inner class} to be placed within the source code of a
 * {@link BuilderMetadata builder}. This could e.g. be an inner class that encapsulates all field values to avoid name
 * collisions.
 * </p>
 */
public interface EncapsulatingClassCodeGenerator {

    /**
     * <p>
     * Generates an {@link EncapsulatingClassSpec encapsulating inner class} to be placed within the source code of a
     * {@link BuilderMetadata builder}. This could e.g. be an inner class that encapsulates all field values to avoid
     * name collisions.
     * </p>
     *
     * @param builderMetadata The metadata of the builder for which to generate an encapsulating inner class. Must not
     *                        be {@code null}.
     * @return The encapsulating inner class.
     */
    EncapsulatingClassSpec generate(final BuilderMetadata builderMetadata);
}
