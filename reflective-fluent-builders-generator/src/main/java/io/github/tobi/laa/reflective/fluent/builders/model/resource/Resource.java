package io.github.tobi.laa.reflective.fluent.builders.model.resource;

/**
 * <p>
 * Represents a result that is based upon a {@link java.io.Closeable closeable} resource. The consumer of a resource is
 * responsible for closing this resource.
 * </p>
 *
 * @param <T> The type of the content contained in this resource.
 */
public interface Resource<T> extends AutoCloseable {

    /**
     * <p>
     * Return the content of this resource.
     * </p>
     *
     * @return The content of this resource. Should never be {@code null}.
     */
    T content();
}
