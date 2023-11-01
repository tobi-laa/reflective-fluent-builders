package io.github.tobi.laa.reflective.fluent.builders.model.resource;

import java.util.Optional;

/**
 * <p>
 * Represents an optional result that is based upon a {@link java.io.Closeable closeable} resource. The consumer of a
 * resource is responsible for closing this resource.
 * </p>
 *
 * @param <T> The type of the content contained in this resource.
 */
public interface OptionalResource<T> extends Resource<Optional<T>> {
    // no content
}
