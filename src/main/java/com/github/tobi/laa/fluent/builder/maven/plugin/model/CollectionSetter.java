package com.github.tobi.laa.fluent.builder.maven.plugin.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 *     The setter of a {@link java.util.Collection collection}, so for instance a list or a set.
 * </p>
 */
@SuperBuilder
@Data
public class CollectionSetter extends AbstractSetter {

    /**
     * <p>
     *     The type of the elements within the collection being set by the setter method's single parameter, for
     *     instance {@code Integer.class}.
     * </p>
     */
    @lombok.NonNull
    private final Class<?> paramTypeArg;
}
