package io.github.tobi.laa.reflective.fluent.builders.generator.api;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.model.WriteAccessor;

/**
 * <p>
 * Generates the {@link CodeBlock} for a single {@link WriteAccessor} to be used within the
 * {@code build()}-{@link MethodSpec method} of a {@link BuilderMetadata builder}.
 * </p>
 *
 * @see BuildMethodCodeGenerator
 */
public interface BuildMethodStepCodeGenerator {

    /**
     * <p>
     * Returns {@code true} if this generator is applicable for {@code writeAccessor} (meaning it can be used for
     * generating a {@link CodeBlock}) and {@code false} otherwise.
     * </p>
     *
     * @param writeAccessor The write accessor for which to check whether this generator is applicable. Must not be
     *                      {@code null}.
     * @return {@code true} if this generator is applicable for {@code writeAccessor} (meaning it can be used for
     * generating a {@link CodeBlock}) and {@code false} otherwise.
     */
    boolean isApplicable(final WriteAccessor writeAccessor);

    /**
     * <p>
     * Generates a {@link CodeBlock} to be integrated within the builder's {@code build()}-{@link MethodSpec method} for
     * the given {@code writeAccessor}.
     * </p>
     *
     * @param writeAccessor The write accessor for which to generate the code block. Must not be {@code null}.
     * @return A code block to be integrated within the builder's {@code build()}-{@link MethodSpec method} for the
     * given {@code writeAccessor}.
     * @throws CodeGenerationException If called for a write accessor for which this generator is not
     *                                 {@link #isApplicable(WriteAccessor) applicable}.
     */
    CodeBlock generate(final WriteAccessor writeAccessor);
}
