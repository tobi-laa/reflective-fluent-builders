package com.github.tobi.laa.reflective.fluent.builders.generator.service.impl;

import com.github.tobi.laa.reflective.fluent.builders.generator.model.BuilderMetadata;
import com.github.tobi.laa.reflective.fluent.builders.generator.model.Setter;
import com.github.tobi.laa.reflective.fluent.builders.generator.model.Visibility;
import com.github.tobi.laa.reflective.fluent.builders.generator.service.api.BuilderMetadataService;
import com.github.tobi.laa.reflective.fluent.builders.generator.service.api.SetterService;
import com.github.tobi.laa.reflective.fluent.builders.generator.service.api.VisibilityService;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.tobi.laa.reflective.fluent.builders.generator.constants.BuilderConstants.PACKAGE_PLACEHOLDER;
import static com.github.tobi.laa.reflective.fluent.builders.generator.model.Visibility.PACKAGE_PRIVATE;
import static com.github.tobi.laa.reflective.fluent.builders.generator.model.Visibility.PUBLIC;
import static java.util.function.Predicate.not;

/**
 * <p>
 * Standard implementation of {@link BuilderMetadataService}.
 * </p>
 */
@RequiredArgsConstructor
public class BuilderMetadataServiceImpl implements BuilderMetadataService {

    @lombok.NonNull
    private final VisibilityService visibilityService;

    @lombok.NonNull
    private final SetterService setterService;

    @lombok.NonNull
    private final String builderPackage;

    @lombok.NonNull
    private final String builderSuffix;

    @Override
    public BuilderMetadata collectBuilderMetadata(final Class<?> clazz) {
        Objects.requireNonNull(clazz);
        return BuilderMetadata.builder() //
                .packageName(resolveBuilderPackage(clazz)) //
                .name(clazz.getSimpleName() + builderSuffix) //
                .builtType(BuilderMetadata.BuiltType.builder() //
                        .type(clazz) //
                        .accessibleNonArgsConstructor(hasAccessibleNonArgsConstructor(clazz)) //
                        .setters(gatherAndFilterAccessibleSetters(clazz))
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
                .mapToInt(Constructor::getParameterCount) //
                .anyMatch(count -> count == 0);
    }

    private boolean isAccessible(final Constructor<?> constructor) {
        return isAccessible(constructor.getDeclaringClass(), constructor.getModifiers());
    }

    private Set<Setter> gatherAndFilterAccessibleSetters(final Class<?> clazz) {
        return setterService.gatherAllSetters(clazz) //
                .stream() //
                .filter(setter -> isAccessible(clazz, setter.getVisibility()))
                .collect(Collectors.toUnmodifiableSet());
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
        return isAccessible(clazz, visibility);
    }

    private boolean isAccessible(final Class<?> clazz, final Visibility visibility) {
        return visibility == PUBLIC || visibility == PACKAGE_PRIVATE && placeBuildersInSamePackage(clazz);
    }
    private boolean placeBuildersInSamePackage(final Class<?> clazz) {
        return PACKAGE_PLACEHOLDER.equals(builderPackage) || builderPackage.equals(clazz.getPackageName());
    }
}
