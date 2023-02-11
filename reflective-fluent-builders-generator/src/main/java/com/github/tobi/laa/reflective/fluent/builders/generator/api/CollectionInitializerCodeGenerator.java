package com.github.tobi.laa.reflective.fluent.builders.generator.api;

import com.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import com.github.tobi.laa.reflective.fluent.builders.model.CollectionSetter;
import com.squareup.javapoet.CodeBlock;

/**
 * <p>
 * Generates a {@link CodeBlock} for initializing the collection being set by a given {@link CollectionSetter}.
 * </p>
 * <p>
 * Calling {@link #generateCollectionInitializer(CollectionSetter)} for the setter of a {@link java.util.List List}
 * should for instance produce a CodeBlock holding the statement {@code "new ArrayList<>()"}.
 * </p>
 */
public interface CollectionInitializerCodeGenerator {

    /**
     * <p>
     * Returns {@code true} if this generator is applicable for {@code collectionSetter} (meaning it can be used for
     * generating an initializer) and {@code false} otherwise.
     * </p>
     *
     * @param collectionSetter The setter for which to check whether this generator is applicable. Must not be
     *                         {@code null}.
     * @return {@code true} if this generator is applicable for {@code collectionSetter} (meaning it can be used for
     * generating an initializer) and {@code false} otherwise.
     */
    boolean isApplicable(final CollectionSetter collectionSetter);

    /**
     * <p>
     * Generates a {@link CodeBlock} for initializing the collection being set by a given {@link CollectionSetter}.
     * </p>
     * <p>
     * Calling this method for the setter of a {@link java.util.List List} should for instance produce a CodeBlock
     * holding the statement {@code "new ArrayList<>()"}.
     * </p>
     *
     * @param collectionSetter The setter for which to generate the code block for initializing the collection being set by it. Must not be {@code null}.
     * @return A code block for initializing the collection being set {@code collectionSetter}.
     * @throws CodeGenerationException If called for a setter for which this generator is not {@link #isApplicable(CollectionSetter)}.
     */
    CodeBlock generateCollectionInitializer(final CollectionSetter collectionSetter);
}
