package io.github.tobi.laa.reflective.fluent.builders.service.impl;

import io.github.classgraph.ClassInfo;
import io.github.classgraph.FieldInfo;
import io.github.classgraph.FieldInfoList;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.model.MethodAccessor;
import io.github.tobi.laa.reflective.fluent.builders.model.WriteAccessor;
import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import io.github.tobi.laa.reflective.fluent.builders.service.api.*;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.GENERATED_BUILDER_MARKER_FIELD_NAME;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.function.Predicate.not;

/**
 * <p>
 * Standard implementation of {@link BuilderMetadataService}.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
class BuilderMetadataServiceImpl implements BuilderMetadataService {

    @lombok.NonNull
    private final AccessibilityService accessibilityService;

    @lombok.NonNull
    private final WriteAccessorService writeAccessorService;

    @lombok.NonNull
    private final ClassService classService;

    @lombok.NonNull
    private final BuilderPackageService builderPackageService;

    @lombok.NonNull
    private final BuildersProperties properties;

    @Override
    public BuilderMetadata collectBuilderMetadata(final ClassInfo classInfo) {
        Objects.requireNonNull(classInfo);
        final var clazz = classInfo.loadClass();
        final String builderPackage = builderPackageService.resolveBuilderPackage(clazz);
        final var writeAccessors = gatherWriteAccessorsAndAvoidNameCollisions(classInfo);
        return BuilderMetadata.builder() //
                .packageName(builderPackage) //
                .name(builderClassName(clazz, builderPackage)) //
                .exceptionTypes(condenseExceptions(writeAccessors)) //
                .nestedBuilders(nestedBuilders(classInfo)) //
                .builtType(BuilderMetadata.BuiltType.builder() //
                        .type(classInfo) //
                        .location(classService.determineClassLocation(clazz).orElse(null)) //
                        .accessibleNonArgsConstructor(hasAccessibleNonArgsConstructor(clazz, builderPackage)) //
                        .writeAccessors(writeAccessors)
                        .build()) //
                .build();
    }

    private Set<Class<? extends Throwable>> condenseExceptions(final Set<WriteAccessor> writeAccessors) {
        final Set<Class<? extends Throwable>> condensed = new HashSet<>();
        writeAccessors //
                .stream() //
                .filter(MethodAccessor.class::isInstance) //
                .map(MethodAccessor.class::cast) //
                .map(MethodAccessor::getExceptionTypes) //
                .flatMap(Set::stream)
                .forEach(exception -> {
                    removeExceptionsThatAreSubclasses(condensed, exception);
                    addExceptionIfSuperclassIsNotPresent(condensed, exception);
                });
        return Collections.unmodifiableSet(condensed);
    }

    private Set<BuilderMetadata> nestedBuilders(final ClassInfo classInfo) {
        return filterOutNonBuildableClasses(new HashSet<>(classInfo.getInnerClasses().getStandardClasses()))
                .stream()
                .filter(ci -> ci.loadClass().getEnclosingClass() == classInfo.loadClass())
                .map(this::collectBuilderMetadata)
                .collect(Collectors.toSet());
    }

    private void addExceptionIfSuperclassIsNotPresent(final Set<Class<? extends Throwable>> exceptions, final Class<? extends Throwable> exception) {
        if (exceptions.stream().noneMatch(candidate -> candidate.isAssignableFrom(exception))) {
            exceptions.add(exception);
        }
    }

    private void removeExceptionsThatAreSubclasses(final Set<Class<? extends Throwable>> exceptions, final Class<? extends Throwable> exception) {
        exceptions.removeIf(exception::isAssignableFrom);
    }

    private String builderClassName(final Class<?> clazz, final String builderPackage) {
        var name = clazz.getSimpleName() + properties.getBuilderSuffix();
        int count = 0;
        while (builderAlreadyExists(builderPackage + '.' + name)) {
            name = clazz.getSimpleName() + properties.getBuilderSuffix() + count;
            count++;
        }
        return name;
    }

    private boolean builderAlreadyExists(final String builderClassName) {
        final Optional<ClassInfo> builderClass = classService.loadClass(builderClassName);
        if (builderClass.isEmpty()) {
            return false;
        } else {
            return builderClass
                    .stream() //
                    .map(ClassInfo::getDeclaredFieldInfo) //
                    .flatMap(FieldInfoList::stream) //
                    .map(FieldInfo::getName) //
                    .noneMatch(GENERATED_BUILDER_MARKER_FIELD_NAME::equals);
        }
    }

    private boolean hasAccessibleNonArgsConstructor(final Class<?> clazz, final String builderPackage) {
        return Arrays //
                .stream(clazz.getDeclaredConstructors()) //
                .filter(constructor -> accessibilityService.isAccessibleFrom(constructor, builderPackage)) //
                .mapToInt(Constructor::getParameterCount) //
                .anyMatch(count -> count == 0);
    }

    private SortedSet<WriteAccessor> gatherWriteAccessorsAndAvoidNameCollisions(final ClassInfo clazz) {
        final var setters = writeAccessorService.gatherAllWriteAccessors(clazz);
        return avoidNameCollisions(setters);
    }

    private SortedSet<WriteAccessor> avoidNameCollisions(final Set<WriteAccessor> writeAccessors) {
        final SortedSet<WriteAccessor> noNameCollisions = new TreeSet<>();
        for (final var writeAccessor : writeAccessors) {
            if (noNameCollisions.stream().map(WriteAccessor::getPropertyName).noneMatch(writeAccessor.getPropertyName()::equals)) {
                noNameCollisions.add(writeAccessor);
            } else {
                for (int i = 0; true; i++) {
                    final var propertyName = writeAccessor.getPropertyName() + i;
                    if (noNameCollisions.stream().map(WriteAccessor::getPropertyName).noneMatch(propertyName::equals)) {
                        noNameCollisions.add(writeAccessor.withPropertyName(propertyName));
                        break;
                    }
                }
            }
        }
        return noNameCollisions;
    }

    @Override
    public Set<ClassInfo> filterOutNonBuildableClasses(final Set<ClassInfo> classes) {
        Objects.requireNonNull(classes);
        return classes //
                .stream() //
                .filter(not(ClassInfo::isInterface)) //
                .filter(not(clazz(Class::isAnonymousClass)))
                .filter(not(ClassInfo::isEnum)) //
                .filter(not(clazz(Class::isPrimitive)))
                .filter(not(ClassInfo::isAbstract)) //
                .filter(not(clazz(c -> c.isMemberClass() && !isStatic(c.getModifiers())))) //
                .filter(clazz(c -> accessibilityService.isAccessibleFrom(c, builderPackageService.resolveBuilderPackage(c)))) //
                .collect(Collectors.toSet());
    }

    private Predicate<ClassInfo> clazz(final Predicate<Class<?>> wrapped) {
        return classInfo -> wrapped.test(classInfo.loadClass());
    }

    @Override
    public Set<ClassInfo> filterOutConfiguredExcludes(final Set<ClassInfo> classes) {
        Objects.requireNonNull(classes);
        return classes.stream().filter(not(clazz(this::exclude))).collect(Collectors.toSet());
    }

    private boolean exclude(final Class<?> clazz) {
        return properties.getExcludes().stream().anyMatch(p -> p.test(clazz));
    }

    @Override
    public Set<BuilderMetadata> filterOutEmptyBuilders(final Collection<BuilderMetadata> builderMetadata) {
        Objects.requireNonNull(builderMetadata);
        return builderMetadata.stream() //
                .filter(metadata -> !metadata.getBuiltType().getWriteAccessors().isEmpty()) //
                .collect(Collectors.toSet());
    }
}
