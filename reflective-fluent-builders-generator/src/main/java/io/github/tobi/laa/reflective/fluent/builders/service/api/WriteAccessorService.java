package io.github.tobi.laa.reflective.fluent.builders.service.api;

import io.github.classgraph.ClassInfo;
import io.github.tobi.laa.reflective.fluent.builders.model.CollectionType;
import io.github.tobi.laa.reflective.fluent.builders.model.Getter;
import io.github.tobi.laa.reflective.fluent.builders.model.Setter;
import io.github.tobi.laa.reflective.fluent.builders.model.WriteAccessor;

import java.util.SortedSet;

/**
 * <p>
 * Gathers all write accessors found on a class.
 * </p>
 */
public interface WriteAccessorService {

    /**
     * <p>
     * Gathers all write accessors found on {@code clazz}.
     * </p>
     *
     * @param clazz The class for which to gather all write accessors. Must not be {@code null}.
     * @return All write accessors of {@code clazz}.
     */
    SortedSet<WriteAccessor> gatherAllWriteAccessors(final ClassInfo clazz);

    /**
     * <p>
     * Checks whether {@code writeAccessor} is a {@link Setter}.
     * </p>
     *
     * @param writeAccessor The {@link WriteAccessor} to check. Must not be {@code null}.
     * @return {@code true} if {@code writeAccessor} is a {@link Setter}, {@code false} otherwise.
     */
    boolean isSetter(final WriteAccessor writeAccessor);

    /**
     * <p>
     * Checks whether {@code writeAccessor} is the {@link Getter} of a {@link CollectionType collection}.
     * </p>
     *
     * @param writeAccessor The {@link WriteAccessor} to check. Must not be {@code null}.
     * @return {@code true} if {@code writeAccessor} is a collection getter, {@code false} otherwise.
     */
    boolean isCollectionGetter(final WriteAccessor writeAccessor);

    /**
     * <p>
     * Drop the configured setter prefix (for instance {@code set}) from {@code name}.
     * </p>
     *
     * @param name The (method) name from which to drop the configured setter prefix. Must not be {@code null}.
     * @return {@code name} with the configured setter prefix stripped from it. If {@code name} does not start with said
     * prefix or solely consists of it and would thus be empty after stripping it, {@code name} will be returned
     * unchanged.
     */
    String dropSetterPrefix(final String name);

    /**
     * <p>
     * Drop the configured getter prefix (for instance {@code get}) from {@code name}.
     * </p>
     *
     * @param name The (method) name from which to drop the configured getter prefix. Must not be {@code null}.
     * @return {@code name} with the configured getter prefix stripped from it. If {@code name} does not start with said
     * prefix or solely consists of it and would thus be empty after stripping it, {@code name} will be returned
     * unchanged.
     */
    String dropGetterPrefix(final String name);
}
