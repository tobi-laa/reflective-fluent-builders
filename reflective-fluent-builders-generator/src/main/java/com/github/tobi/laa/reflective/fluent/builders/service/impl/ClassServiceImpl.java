package com.github.tobi.laa.reflective.fluent.builders.service.impl;

import com.github.tobi.laa.reflective.fluent.builders.exception.ReflectionException;
import com.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import com.github.tobi.laa.reflective.fluent.builders.service.api.ClassService;
import com.google.common.reflect.ClassPath;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

/**
 * <p>
 * Standard implementation of {@link ClassService}.
 * </p>
 * <p>
 * Classes to be excluded from the {@link #collectFullClassHierarchy(Class) hierarchy collection} can be provided via
 * the constructor.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
class ClassServiceImpl implements ClassService {

    @lombok.NonNull
    private final BuildersProperties properties;

    @Override
    public Set<Class<?>> collectFullClassHierarchy(final Class<?> clazz) {
        Objects.requireNonNull(clazz);
        final Set<Class<?>> classHierarchy = new HashSet<>();
        for (var i = clazz; i != null; i = i.getSuperclass()) {
            if (properties.getHierarchyCollection().getClassesToExclude().contains(i)) {
                break;
            }
            classHierarchy.add(i);
            Arrays.stream(i.getInterfaces()) //
                    .filter(not(properties.getHierarchyCollection().getClassesToExclude()::contains)) //
                    .forEach(classHierarchy::add);
        }
        return classHierarchy;
    }

    @Override
    public Set<Class<?>> collectClassesRecursively(final String packageName) {
        Objects.requireNonNull(packageName);
        try {
            return ClassPath.from(getClass().getClassLoader()) //
                    .getTopLevelClassesRecursive(packageName) //
                    .stream() //
                    .map(ClassPath.ClassInfo::load) //
                    .flatMap(clazz -> Stream.concat(
                            Stream.of(clazz),
                            collectStaticInnerClassesRecursively(clazz).stream()))
                    .collect(Collectors.toUnmodifiableSet());
        } catch (final IOException e) {
            throw new ReflectionException("Error while attempting to collect classes recursively.", e);
        }
    }

    private Set<Class<?>> collectStaticInnerClassesRecursively(final Class<?> clazz) {
        final Set<Class<?>> innerStaticClasses = new HashSet<>();
        for (final Class<?> innerClass : clazz.getDeclaredClasses()) {
            innerStaticClasses.add(innerClass);
            innerStaticClasses.addAll(collectStaticInnerClassesRecursively(innerClass));
        }
        return innerStaticClasses;
    }
}
