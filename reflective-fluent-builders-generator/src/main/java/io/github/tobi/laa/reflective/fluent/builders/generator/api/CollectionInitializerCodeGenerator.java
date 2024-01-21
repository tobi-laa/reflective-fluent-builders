package io.github.tobi.laa.reflective.fluent.builders.generator.api;

import com.squareup.javapoet.CodeBlock;
import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.model.CollectionType;

/**
 * <p>
 * Generates a {@link CodeBlock} for initializing the collection represented by a given {@link CollectionType}.
 * </p>
 * <p>
 * Calling {@link #generateCollectionInitializer(CollectionType)} for a {@link java.util.List List} should for instance
 * produce a CodeBlock holding the statement {@code "new ArrayList<>()"}.
 * </p>
 */
public interface CollectionInitializerCodeGenerator {

    /**
     * <p>
     * Returns {@code true} if this generator is applicable for {@code collectionType} (meaning it can be used for
     * generating an initializer) and {@code false} otherwise.
     * </p>
     *
     * @param collectionType The type for which to check whether this generator is applicable. Must not be {@code null}.
     * @return {@code true} if this generator is applicable for {@code collectionType} (meaning it can be used for
     * generating an initializer) and {@code false} otherwise.
     */
    boolean isApplicable(final CollectionType collectionType);

    /**
     * <p>
     * Generates a {@link CodeBlock} for initializing the collection represented by a given {@link CollectionType}.
     * </p>
     * <p>
     * Calling this method for a {@link java.util.List List} should for instance produce a CodeBlock holding the
     * statement {@code "new ArrayList<>()"}.
     * </p>
     *
     * @param collectionType The type for which to generate the code block for initializing the collection represented
     *                       by it. Must not be {@code null}.
     * @return A code block for initializing the collection represented by {@code collectionType}.
     * @throws CodeGenerationException If called for a type for which this generator is not
     *                                 {@link #isApplicable(CollectionType) applicable}.
     */
    CodeBlock generateCollectionInitializer(final CollectionType collectionType);
}
