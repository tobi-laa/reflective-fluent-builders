package io.github.tobi.laa.reflective.fluent.builders.service.api;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * <p>
 * Offers methods for dealing with reflection related to types.
 * </p>
 */
public interface TypeService {

    /**
     * <p>
     * Returns all classes that are part of {@code type}.
     * </p>
     * <p>
     * For simple types, the returned set simply contains {@code type} itself.
     * </p>
     * <p>
     * For more complex generics, all classes are returned separately. For example, calling this method for a type
     * representing {@code List<Map<Long, Set<Boolean>>>} will return a set containing {@code List.class},
     * {@code Map.class}, {@code Long.class}, {@code Set.class} and {@code Boolean.class},
     * </p>
     * <p>
     * For wildcard types, the upper bounds are included (may just be {@code Object.class}.
     * </p>
     *
     * @param type The type for which to return all classes that are part of it. Must not be {@code null}.
     * @return All classes that are part of {@code type}. At the very least a set that contains one element
     * ({@code type} itself).
     */
    Set<Class<?>> explodeType(final Type type);
}
