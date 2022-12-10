package com.github.tobi.laa.reflective.fluent.builders.generator.service.impl;

import com.github.tobi.laa.reflective.fluent.builders.generator.exception.ReflectionException;
import com.github.tobi.laa.reflective.fluent.builders.generator.service.api.ClassService;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
public class ClassServiceImpl implements ClassService {

    private final Set<Class<?>> classesToExclude;

    /**
     * <p>
     * Constructs a new instance of this service.
     * </p>
     *
     * @param classesToExclude Classes to be excluded from the
     *                         {@link #collectFullClassHierarchy(Class) hierarchy collection}. They will not be added to
     *                         the result. Furthermore, if a class from {@code excludes} is encountered during ancestor
     *                         traversal of {@code clazz} it is immediately stopped.
     */
    public ClassServiceImpl(final Class<?>... classesToExclude) {
        this.classesToExclude = Set.of(classesToExclude);
    }

    @Override
    public Set<Class<?>> collectFullClassHierarchy(final Class<?> clazz) {
        Objects.requireNonNull(clazz);
        final Set<Class<?>> classHierarchy = new HashSet<>();
        for (var i = clazz; i != null; i = i.getSuperclass()) {
            if (classesToExclude.contains(i)) {
                break;
            }
            classHierarchy.add(i);
            Arrays.stream(i.getInterfaces()) //
                    .filter(not(classesToExclude::contains)) //
                    .forEach(classHierarchy::add);
        }
        return classHierarchy;
    }

    @Override
    public Set<Class<?>> collectClassesRecursively(final String packageName) {
        Objects.requireNonNull(packageName);
        try {
            return ClassPath.from(ClassLoader.getSystemClassLoader()) //
                    .getTopLevelClassesRecursive(packageName) //
                    .stream() //
                    .map(ClassPath.ClassInfo::load) //
                    .collect(Collectors.toUnmodifiableSet());
        } catch (IOException e) {
            throw new ReflectionException("Error while attempting to collect classes recursively.", e);
        }
    }
}
