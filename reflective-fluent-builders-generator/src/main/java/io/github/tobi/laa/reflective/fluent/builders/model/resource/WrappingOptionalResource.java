package io.github.tobi.laa.reflective.fluent.builders.model.resource;

import lombok.Builder;

import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;

/**
 * <p>
 * Implementation of {@link OptionalResource} that wraps a {@link java.io.Closeable}.
 * </p>
 *
 * @param <T> The type of the content contained in this resource.
 */
@Builder
public class WrappingOptionalResource<T> implements OptionalResource<T> {

    private final T content;

    @lombok.NonNull
    private final Closeable closeable;

    @Override
    public Optional<T> content() {
        return Optional.ofNullable(content);
    }

    @Override
    public void close() throws IOException {
        closeable.close();
    }
}
