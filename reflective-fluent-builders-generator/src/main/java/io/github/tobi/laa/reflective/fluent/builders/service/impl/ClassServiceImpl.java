package io.github.tobi.laa.reflective.fluent.builders.service.impl;


import com.google.common.collect.ImmutableList;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassGraphException;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import io.github.tobi.laa.reflective.fluent.builders.exception.ReflectionException;
import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import io.github.tobi.laa.reflective.fluent.builders.service.api.ClassService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.base.Predicates.not;

/**
 * <p>
 * Standard implementation of {@link ClassService}.
 * </p>
 * <p>
 * Classes to be excluded from the {@link #collectFullClassHierarchy(ClassInfo) hierarchy collection} can be provided via
 * the constructor.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
class ClassServiceImpl implements ClassService {

    @lombok.NonNull
    private final BuildersProperties properties;

    @lombok.NonNull
    private final Provider<ClassLoader> classLoaderProvider;

    @Override
    public List<ClassInfo> collectFullClassHierarchy(final ClassInfo clazz) {
        Objects.requireNonNull(clazz);
        final List<ClassInfo> classHierarchy = new ArrayList<>();
        for (ClassInfo c = clazz; c != null; c = c.getSuperclass()) {
            final ClassInfo currentClass = c;
            if (excludeFromHierarchyCollection(currentClass)) {
                break;
            }
            classHierarchy.add(currentClass);
            currentClass.getInterfaces().stream() //
                    .filter(i -> isDirectInterface(i, currentClass)) //
                    .filter(not(this::excludeFromHierarchyCollection)) //
                    .forEach(classHierarchy::add);
        }
        return classHierarchy.stream().distinct().collect(ImmutableList.toImmutableList());
    }

    private boolean isDirectInterface(final ClassInfo anInterface, final ClassInfo clazz) {
        return Arrays.asList(clazz.loadClass().getInterfaces()).contains(anInterface.loadClass());
    }

    private boolean excludeFromHierarchyCollection(final ClassInfo clazz) {
        return properties.getHierarchyCollection().getExcludes().stream().anyMatch(p -> p.test(clazz.loadClass()));
    }

    @Override
    public Set<ClassInfo> collectClassesRecursively(final String packageName) {
        Objects.requireNonNull(packageName);
        try (final ScanResult scanResult = new ClassGraph()
                .overrideClassLoaders(classLoaderProvider.get())
                .enableAllInfo()
                .acceptPackages(packageName)
                .scan()) {
            //
            return scanResult.getAllClasses()
                    .stream()
                    .map(this::loadEagerly)
                    .filter(not(ClassInfo::isInnerClass))
                    .collect(Collectors.toUnmodifiableSet());
        } catch (final ClassGraphException e) {
            throw new ReflectionException("Error while attempting to collect classes recursively.", e);
        }
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

    // Exception should never occur
    @SneakyThrows(URISyntaxException.class)
    Path getLocationAsPath(final CodeSource codeSource) {
        return Paths.get(codeSource.getLocation().toURI());
    }

    private Path resolveClassFileIfNecessary(final Path path, final Class<?> clazz) {
        if (Files.isDirectory(path)) {
            Path classFile = path;
            for (final String subdir : clazz.getPackage().getName().split("\\.")) {
                classFile = classFile.resolve(subdir);
            }
            classFile = classFile.resolve(clazz.getSimpleName() + ".class");
            return classFile;
        } else {
            return path;
        }
    }

    @Override
    public Optional<ClassInfo> loadClass(final String className) {
        Objects.requireNonNull(className);
        try (final ScanResult scanResult = new ClassGraph()
                .overrideClassLoaders(classLoaderProvider.get())
                .enableAllInfo()
                .acceptClasses(className)
                .scan()) {
            //
            return scanResult.getAllClasses().stream().findFirst().map(this::loadEagerly);
        } catch (final ClassGraphException e) {
            throw new ReflectionException("Error while attempting to load class " + className + '.', e);
        }
    }

    private ClassInfo loadEagerly(final ClassInfo classInfo) {
        classInfo.loadClass();
        classInfo.getSuperclasses().loadClasses();
        classInfo.getInterfaces().loadClasses();
        classInfo.getInnerClasses().loadClasses();
        return classInfo;
    }

    @Override
    public boolean isAbstract(final Class<?> clazz) {
        Objects.requireNonNull(clazz);
        return Modifier.isAbstract(clazz.getModifiers());
    }
}
