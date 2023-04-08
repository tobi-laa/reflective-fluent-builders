package io.github.tobi.laa.reflective.fluent.builders.generator.api;

import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import com.squareup.javapoet.JavaFile;
import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;

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
     * @throws CodeGenerationException If an error occurs
     *                                                                                          during code generation.
     */
    JavaFile generateJavaFile(final BuilderMetadata builderMetadata);
}
