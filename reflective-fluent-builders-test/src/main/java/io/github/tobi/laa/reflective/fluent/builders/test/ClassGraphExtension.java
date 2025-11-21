package io.github.tobi.laa.reflective.fluent.builders.test;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import io.github.tobi.laa.reflective.fluent.builders.Marker;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

import java.util.Optional;

/**
 * <p>
 * JUnit 5 extension that scans the classpath with {@link ClassGraph} and offers access to all
 * {@link ClassInfoList classes} found.
 * </p>
 */
public class ClassGraphExtension implements BeforeAllCallback {

    private static final String NAMESPACE = ClassGraphExtension.class.getName();

    private static final String ALL_CLASSES_ID = "allClasses";

    private ClassInfoList allClasses;

    @Override
    public void beforeAll(final ExtensionContext extensionContext) {
        allClasses = extensionContext.getStore(Namespace.create(NAMESPACE))
                .computeIfAbsent(ALL_CLASSES_ID, key -> loadAllClasses(), ClassInfoList.class);
    }

    private ClassInfoList loadAllClasses() {
        try (final ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(Marker.class.getPackageName())
                .scan()) {
            //
            final ClassInfoList classInfos = scanResult.getAllClasses();
            classInfos.forEach(this::loadEagerly);
            return classInfos;
        }
    }

    private void loadEagerly(final ClassInfo classInfo) {
        classInfo.loadClass();
        classInfo.getSuperclasses().loadClasses();
        classInfo.getInterfaces().loadClasses();
        classInfo.getInnerClasses().loadClasses();
    }

    /**
     * <p>
     * Get the {@link ClassInfo} for {@code clazz}.
     * </p>
     *
     * @param clazz The class for which to get the {@link ClassInfo}. Must not be {@code null}.
     * @return The {@link ClassInfo} for the class. Never {@code null}.
     * @throws IllegalArgumentException If no {@link ClassInfo} for the class has been found.
     */
    public ClassInfo get(final Class<?> clazz) {
        return get(clazz.getName());
    }

    /**
     * <p>
     * Get the {@link ClassInfo} for the class with the given {@code name}.
     * </p>
     *
     * @param name The name of the class for which to get the {@link ClassInfo}. Must not be {@code null}.
     * @return The {@link ClassInfo} for the class with the given {@code name}. Never {@code null}.
     * @throws IllegalArgumentException If no {@link ClassInfo} for the class with the given {@code name} has been found.
     */
    public ClassInfo get(final String name) {
        return Optional
                .ofNullable(allClasses.get(name))
                .orElseThrow(() -> new IllegalArgumentException("No ClassInfo for " + name + " has been found!"));
    }
}
