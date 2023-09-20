package io.github.tobi.laa.reflective.fluent.builders.service.api;

import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;

/**
 * <p>
 * Resolves the builder package for a given {@link Class}, that is the package which is configured to contain the
 * builder for said class.
 * </p>
 *
 * @see BuildersProperties#getBuilderPackage()
 */
public interface BuilderPackageService {

    /**
     * <p>
     * Resolves the builder package for {@code clazz}, that is the package which is configured to contain its builder.
     * </p>
     *
     * @param clazz The class for which the resolve the builder package. Must not be {@code null}.
     * @return The package which is configured to contain the builder for {@code clazz}.
     */
    String resolveBuilderPackage(final Class<?> clazz);
}
