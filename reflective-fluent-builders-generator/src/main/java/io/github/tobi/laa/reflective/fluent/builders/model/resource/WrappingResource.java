package io.github.tobi.laa.reflective.fluent.builders.model.resource;

import lombok.Builder;

import java.io.Closeable;
import java.io.IOException;

/**
 * <p>
 * Implementation of {@link Resource} that wraps a {@link java.io.Closeable}.
 * </p>
 *
 * @param <T> The type of the content contained in this resource. Should never be {@code null}.
 */
@Builder
public class WrappingResource<T> implements Resource<T> {

    @lombok.NonNull
    private final T content;

    @lombok.NonNull
    private final Closeable closeable;

    @Override
    public T content() {
        return content;
    }

    @Override
    public void close() throws IOException {
        closeable.close();
    }
}
