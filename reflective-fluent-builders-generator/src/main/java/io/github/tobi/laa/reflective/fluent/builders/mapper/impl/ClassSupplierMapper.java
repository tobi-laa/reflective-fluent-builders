package io.github.tobi.laa.reflective.fluent.builders.mapper.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.function.Supplier;

/**
 * <p>
 * Helper for creating {@link java.util.function.Supplier suppliers} for loading Java classes lazily.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
class ClassSupplierMapper {

    @lombok.NonNull
    private final Provider<ClassLoader> classLoaderProvider;

    Supplier<Class<?>> createSupplier(final String name) {
        return () -> loadClass(name);
    }

    @SneakyThrows
    private Class<?> loadClass(final String name) {
        return classLoaderProvider.get().loadClass(name);
    }
}
