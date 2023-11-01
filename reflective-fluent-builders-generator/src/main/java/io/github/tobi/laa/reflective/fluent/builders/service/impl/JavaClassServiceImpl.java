package io.github.tobi.laa.reflective.fluent.builders.service.impl;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassGraphException;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import io.github.tobi.laa.reflective.fluent.builders.exception.ReflectionException;
import io.github.tobi.laa.reflective.fluent.builders.model.JavaClass;
import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import io.github.tobi.laa.reflective.fluent.builders.service.api.JavaClassService;
import io.github.tobi.laa.reflective.fluent.builders.service.api.VisibilityService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toSet;

/**
 * <p>
 * Standard implementation of {@link JavaClassService}.
 * </p>
 * <p>
 * Classes to be excluded from the {@link #collectFullClassHierarchy(JavaClass) hierarchy collection} can be provided
 * via the constructor.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
class JavaClassServiceImpl implements JavaClassService {

    @lombok.NonNull
    private final VisibilityService visibilityService;

    @lombok.NonNull
    private final BuildersProperties properties;

    @lombok.NonNull
    private final Provider<ClassLoader> classLoaderProvider;

    @Override
    public List<JavaClass> collectFullClassHierarchy(final JavaClass clazz) {
        Objects.requireNonNull(clazz);
        final List<JavaClass> classHierarchy = new ArrayList<>();
        for (var i = clazz; i != null; i = i.getSuperclass().orElse(null)) {
            if (excludeFromHierarchyCollection(i)) {
                break;
            }
            classHierarchy.add(i);
            i.getInterfaces()
                    .stream()
                    .filter(not(this::excludeFromHierarchyCollection)) //
                    .forEach(classHierarchy::add);
        }
        return classHierarchy.stream().distinct().collect(Collectors.toUnmodifiableList());
    }

    private boolean excludeFromHierarchyCollection(final JavaClass clazz) {
        return properties.getHierarchyCollection().getExcludes().stream().anyMatch(p -> p.test(clazz));
    }

    @Override
    public Set<JavaClass> collectClassesRecursively(final String packageName) {
        Objects.requireNonNull(packageName);
        try (final ScanResult scanResult = classGraph().acceptPackages(packageName).scan()) {
            //
            return scanResult.getAllClasses()
                    .stream()
                    .flatMap(clazz -> Stream.concat(
                            Stream.of(clazz),
                            collectStaticInnerClassesRecursively(clazz).stream()))
                    .map(this::toJavaClass)
                    .collect(Collectors.toUnmodifiableSet());
        } catch (final ClassGraphException e) {
            throw new ReflectionException("Error while attempting to collect classes recursively.", e);
        }
    }

    private ClassGraph classGraph() {
        return new ClassGraph()
                .overrideClassLoaders(classLoaderProvider.get())
                .enableAllInfo();
    }

    private JavaClass toJavaClass(final ClassInfo clazz) {
        return JavaClass.builder()
                .clazz(clazz.loadClass())
                .visibility(visibilityService.toVisibility(clazz.getModifiers()))
                .isAbstract(clazz.isAbstract())
                .isStatic(clazz.isStatic())
                .classLocation(classLocation(clazz))
                .sourceLocation(sourceLocation(clazz))
                .superclass(superclass(clazz))
                .interfaces(clazz.getInterfaces().stream().map(this::toJavaClass).collect(toSet()))
                .build();
    }

    private Path classLocation(final ClassInfo clazz) {
        return Optional.ofNullable(clazz)
                .map(ClassInfo::getClasspathElementFile)
                .map(File::toPath)
                .map(path -> resolveClassFileIfNecessary(path, clazz.loadClass()))
                .orElse(null);
    }

    private Path sourceLocation(final ClassInfo clazz) {
        return Optional.ofNullable(clazz).map(ClassInfo::getSourceFile).map(Paths::get).orElse(null);
    }

    private JavaClass superclass(final ClassInfo clazz) {
        return Optional.ofNullable(clazz.getSuperclass()).map(this::toJavaClass).orElse(null);
    }

    private Set<ClassInfo> collectStaticInnerClassesRecursively(final ClassInfo clazz) {
        final Set<ClassInfo> innerStaticClasses = new HashSet<>();
        clazz.getInnerClasses().stream().filter(ClassInfo::isStatic).forEach(innerClass -> {
            innerStaticClasses.add(innerClass);
            innerStaticClasses.addAll(collectStaticInnerClassesRecursively(innerClass));
        });
        return innerStaticClasses;
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
            for (final String subdir : clazz.getPackageName().split("\\.")) {
                classFile = classFile.resolve(subdir);
            }
            classFile = classFile.resolve(clazz.getSimpleName() + ".class");
            return classFile;
        } else {
            return path;
        }
    }

    @Override
    public Optional<JavaClass> loadClass(final String className) {
        try (final ScanResult scanResult = classGraph().acceptClasses(className).scan()) {
            return scanResult.getAllClasses().stream().map(this::toJavaClass).findFirst();
        } catch (final ClassGraphException e) {
            throw new ReflectionException("Error while attempting to load class " + className + '.', e);
        }
    }
}
