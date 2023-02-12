package com.github.tobi.laa.reflective.fluent.builders.generator.api;

import com.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import com.squareup.javapoet.JavaFile;

/**
 * Generates JavaPoet {@link JavaFile} objects containing the spec for a builder class from
 * {@link BuilderMetadata builder metadata}.
 *
 * @see <a href="https://github.com/square/javapoet">JavaPoet</a>
 */
public interface JavaFileGenerator {

    /**
     * <p>
     * Generates a {@link JavaFile} containing the actual builder class corresponding to {@code builderMetadata}.
     * </p>
     *
     * @param builderMetadata The metadata of the builder for which to generate a {@link JavaFile}. Must not be
     *                        {@code null}.
     * @return A {@link JavaFile} containing the actual builder class corresponding to {@code builderMetadata}.
     * @throws com.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException If an error occurs
     *                                                                                          during code generation.
     */
    JavaFile generateJavaFile(final BuilderMetadata builderMetadata);
}
