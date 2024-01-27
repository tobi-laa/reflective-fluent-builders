package io.github.tobi.laa.reflective.fluent.builders.service.impl;

import io.github.tobi.laa.reflective.fluent.builders.model.Visibility;
import io.github.tobi.laa.reflective.fluent.builders.service.api.AccessibilityService;
import io.github.tobi.laa.reflective.fluent.builders.service.api.ClassService;
import io.github.tobi.laa.reflective.fluent.builders.service.api.TypeService;
import io.github.tobi.laa.reflective.fluent.builders.service.api.VisibilityService;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Objects;

import static io.github.tobi.laa.reflective.fluent.builders.model.Visibility.*;
import static java.util.Arrays.stream;

@Named
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
class AccessibilityServiceImpl implements AccessibilityService {

    @lombok.NonNull
    private final VisibilityService visibilityService;

    @lombok.NonNull
    private final TypeService typeService;

    @lombok.NonNull
    private final ClassService classService;

    @Override
    public boolean isAccessibleFrom(final Class<?> clazz, final String packageName) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(packageName);
        return isAccessible(clazz, clazz.getModifiers(), packageName);
    }

    @Override
    public boolean isAccessibleFrom(final Type type, final String packageName) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(packageName);
        return typeService.explodeType(type).stream().allMatch(clazz -> isAccessibleFrom(clazz, packageName));
    }

    @Override
    public boolean isAccessibleFrom(final Method method, final String packageName) {
        Objects.requireNonNull(method);
        Objects.requireNonNull(packageName);
        final Visibility visibility = visibilityService.toVisibility(method.getModifiers());
        return isAccessible(method.getDeclaringClass(), visibility, packageName) && //
                isAccessibleFrom(method.getGenericReturnType(), packageName) && //
                stream(method.getGenericParameterTypes()) //
                        .allMatch(type -> isAccessibleFrom(type, packageName));
    }

    @Override
    public boolean isAccessibleFrom(final Constructor<?> constructor, final String packageName) {
        Objects.requireNonNull(constructor);
        Objects.requireNonNull(packageName);
        return isAccessible(constructor.getDeclaringClass(), constructor.getModifiers(), packageName);
    }

    @Override
    public boolean isAccessibleFrom(final Field field, final String packageName) {
        Objects.requireNonNull(field);
        Objects.requireNonNull(packageName);
        final var visibility = visibilityService.toVisibility(field.getModifiers());
        return isAccessible(field.getDeclaringClass(), visibility, packageName) && //
                isAccessibleFrom(field.getGenericType(), packageName);
    }

    private boolean isAccessible(final Class<?> clazz, final int modifiers, final String builderPackage) {
        final Visibility visibility = visibilityService.toVisibility(modifiers);
        return isAccessible(clazz, visibility, builderPackage);
    }

    private boolean isAccessible(final Class<?> clazz, final Visibility visibility, final String builderPackage) {
        return visibility == PUBLIC || //
                visibility == PACKAGE_PRIVATE && !classService.isAbstract(clazz) && builderPackage.equals(clazz.getPackage().getName()) || //
                visibility == PROTECTED && builderPackage.equals(clazz.getPackage().getName());
    }
}
