package com.github.tobi.laa.fluent.builder.generator.service.impl;

import com.github.tobi.laa.fluent.builder.generator.model.Builder;
import com.github.tobi.laa.fluent.builder.generator.service.api.BuilderService;
import com.github.tobi.laa.fluent.builder.generator.service.api.VisibilityService;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.tobi.laa.fluent.builder.generator.constants.BuilderConstants.PACKAGE_PLACEHOLDER;
import static com.github.tobi.laa.fluent.builder.generator.model.Visibility.PACKAGE_PRIVATE;
import static com.github.tobi.laa.fluent.builder.generator.model.Visibility.PUBLIC;
import static java.util.function.Predicate.not;

/**
 * <p>
 * Standard implementation of {@link BuilderService}.
 * </p>
 */
@RequiredArgsConstructor
public class BuilderServiceImpl implements BuilderService {

    @lombok.NonNull
    private final VisibilityService visibilityService;

    @lombok.NonNull
    private final String builderPackage;

    @lombok.NonNull
    private final String builderSuffix;

    @Override
    public Builder collectBuilderMetadata(final Class<?> clazz) {
        Objects.requireNonNull(clazz);
        return Builder.builder() //
                .packageName(resolveBuilderPackage(clazz)) //
                .name(clazz.getName() + builderSuffix) //
                .builtType(Builder.BuiltType.builder() //
                        .type(clazz) //
                        .accessibleNonArgsConstructor(hasAccessibleNonArgsConstructor(clazz)) //
                        .build()) //
                .build();
    }

    private String resolveBuilderPackage(final Class<?> clazz) {
        return builderPackage.replace(PACKAGE_PLACEHOLDER, clazz.getPackageName());
    }

    private boolean hasAccessibleNonArgsConstructor(final Class<?> clazz) {
        return Arrays //
                .stream(clazz.getConstructors()) //
                .filter(this::isAccessible) //
                .filter(constructor -> constructor.getParameterCount() == 0) //
                .count() >= 1;
    }

    private boolean isAccessible(final Constructor<?> constructor) {
        return isAccessible(constructor.getDeclaringClass(), constructor.getModifiers());
    }

    @Override
    public Set<Class<?>> filterOutNonBuildableClasses(final Set<Class<?>> classes) {
        Objects.requireNonNull(classes);
        return classes //
                .stream() //
                .filter(not(Class::isInterface)) //
                .filter(not(this::isAbstract)) //
                .filter(not(Class::isAnonymousClass)) //
                .filter(not(Class::isEnum)) //
                .filter(not(Class::isPrimitive)) //
                .filter(not(Class::isMemberClass)) //
                .filter(this::isAccessible) //
                .collect(Collectors.toSet());
    }

    private boolean isAbstract(final Class<?> clazz) {
        return Modifier.isAbstract(clazz.getModifiers());
    }

    private boolean isAccessible(final Class<?> clazz) {
        return isAccessible(clazz, clazz.getModifiers());
    }

    private boolean isAccessible(final Class<?> clazz, final int modifiers) {
        final var visibility = visibilityService.toVisibility(modifiers);
        return visibility == PUBLIC || visibility == PACKAGE_PRIVATE && placeBuildersInSamePackage(clazz);
    }

    private boolean placeBuildersInSamePackage(final Class<?> clazz) {
        return PACKAGE_PLACEHOLDER.equals(builderPackage) || builderPackage.equals(clazz.getPackageName());
    }
}
