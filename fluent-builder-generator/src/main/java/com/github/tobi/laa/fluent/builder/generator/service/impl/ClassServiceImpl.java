package com.github.tobi.laa.fluent.builder.generator.service.impl;

import com.github.tobi.laa.fluent.builder.generator.exception.ReflectionException;
import com.github.tobi.laa.fluent.builder.generator.service.api.ClassService;
import com.google.common.reflect.ClassPath;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
 */
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {

    @Override
    public Set<Class<?>> collectFullClassHierarchy(final Class<?> clazz, final Class<?>... excludes) {
        Objects.requireNonNull(clazz);
        final Set<Class<?>> classesToExclude = Set.of(excludes);
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
            return ClassPath.from(ClassLoader.getSystemClassLoader())
                    .getTopLevelClassesRecursive(packageName)
                    .stream()
                    .map(ClassPath.ClassInfo::load)
                    .collect(Collectors.toUnmodifiableSet());
        } catch (IOException e) {
            throw new ReflectionException("Error while attempting to collect classes recursively.", e);
        }
    }

    public long thisIsTest(final int a, final int b) {
        int c = a + b;
        final var bar = Integer.toString(c).substring(1) + "foobar";
        final var by = bar.getBytes(StandardCharsets.UTF_8);
        return by[4];
    }
}
