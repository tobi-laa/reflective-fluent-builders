package io.github.tobi.laa.reflective.fluent.builders.service.impl;

import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import io.github.tobi.laa.reflective.fluent.builders.service.api.BuilderPackageService;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Objects;

import static io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.PACKAGE_PLACEHOLDER;

/**
 * <p>
 * Standard implementation of {@link BuilderPackageService}.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
class BuilderPackageServiceImpl implements BuilderPackageService {

    @lombok.NonNull
    private final BuildersProperties properties;

    @Override
    public String resolveBuilderPackage(final Class<?> clazz) {
        Objects.requireNonNull(clazz);
        return properties.getBuilderPackage().replace(PACKAGE_PLACEHOLDER, clazz.getPackage().getName());
    }
}
