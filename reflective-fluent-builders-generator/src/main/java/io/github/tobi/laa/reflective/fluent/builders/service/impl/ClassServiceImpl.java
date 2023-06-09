package io.github.tobi.laa.reflective.fluent.builders.service.impl;

import com.google.common.reflect.ClassPath;
import io.github.tobi.laa.reflective.fluent.builders.exception.ReflectionException;
import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import io.github.tobi.laa.reflective.fluent.builders.service.api.ClassService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.*;
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
            if (excludeFromHierarchyCollection(i)) {
                break;
            }
            classHierarchy.add(i);
            Arrays.stream(i.getInterfaces()) //
                    .filter(not(this::excludeFromHierarchyCollection)) //
                    .forEach(classHierarchy::add);
        }
        return classHierarchy;
    }

    private boolean excludeFromHierarchyCollection(final Class<?> clazz) {
        return properties.getHierarchyCollection().getExcludes().stream().anyMatch(p -> p.test(clazz));
    }

    @Override
    public Set<Class<?>> collectClassesRecursively(final String packageName) {
        Objects.requireNonNull(packageName);
        try {
            return ClassPath.from(Thread.currentThread().getContextClassLoader()) //
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

    @Override
    public Optional<Path> determineClassLocation(final Class<?> clazz) {
        Objects.requireNonNull(clazz);
        return Optional.ofNullable(getCodeSource(clazz)) //
                .map(this::getLocationAsPath) //
                .map(path -> resolveClassFileIfNecessary(path, clazz));
    }

    private CodeSource getCodeSource(final Class<?> clazz) {
        return clazz.getProtectionDomain().getCodeSource();
    }

    @SneakyThrows(URISyntaxException.class)
        // should never occur
    Path getLocationAsPath(final CodeSource codeSource) {
        return Paths.get(codeSource.getLocation().toURI());
    }

    private Path resolveClassFileIfNecessary(final Path path, final Class<?> clazz) {
        if (Files.isDirectory(path)) {
            Path classFile = path;
            for (final String subdir : clazz.getPackageName().split("\\.")) {
                classFile = classFile.resolve(subdir);
            }
            classFile = classFile.resolve(clazz.getSimpleName() + ".class");
            return classFile;
        } else {
            return path;
        }
    }
}
