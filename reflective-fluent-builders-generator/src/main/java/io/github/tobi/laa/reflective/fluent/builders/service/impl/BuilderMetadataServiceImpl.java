package io.github.tobi.laa.reflective.fluent.builders.service.impl;

import com.google.common.base.Predicate;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.FieldInfo;
import io.github.classgraph.FieldInfoList;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.model.Setter;
import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import io.github.tobi.laa.reflective.fluent.builders.service.api.*;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Predicates.not;
import static io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.GENERATED_BUILDER_MARKER_FIELD_NAME;
import static java.lang.reflect.Modifier.isStatic;

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
    private final SetterService setterService;

    @lombok.NonNull
    private final ClassService classService;

    @lombok.NonNull
    private final BuilderPackageService builderPackageService;

    @lombok.NonNull
    private final BuildersProperties properties;

    @Override
    public BuilderMetadata collectBuilderMetadata(final ClassInfo classInfo) {
        Objects.requireNonNull(classInfo);
        final Class<?> clazz = classInfo.loadClass();
        final String builderPackage = builderPackageService.resolveBuilderPackage(clazz);
        return BuilderMetadata.builder() //
                .packageName(builderPackage) //
                .name(builderClassName(clazz, builderPackage)) //
                .builtType(BuilderMetadata.BuiltType.builder() //
                        .type(classInfo) //
                        .location(classService.determineClassLocation(clazz).orElse(null)) //
                        .accessibleNonArgsConstructor(hasAccessibleNonArgsConstructor(clazz, builderPackage)) //
                        .setters(gatherSettersAndAvoidNameCollisions(classInfo))
                        .build()) //
                .build();
    }

    private String builderClassName(final Class<?> clazz, final String builderPackage) {
        String name = clazz.getSimpleName() + properties.getBuilderSuffix();
        int count = 0;
        while (builderAlreadyExists(builderPackage + '.' + name)) {
            name = clazz.getSimpleName() + properties.getBuilderSuffix() + count;
            count++;
        }
        return name;
    }

    private boolean builderAlreadyExists(final String builderClassName) {
        final Optional<ClassInfo> builderClass = classService.loadClass(builderClassName);
        if (!builderClass.isPresent()) {
            return false;
        } else {
            return builderClass
                    .map(ClassInfo::getDeclaredFieldInfo) //
                    .map(FieldInfoList::stream) //
                    .orElseGet(Stream::empty)
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

    private SortedSet<Setter> gatherSettersAndAvoidNameCollisions(final ClassInfo clazz) {
        final SortedSet<Setter> setters = setterService.gatherAllSetters(clazz);
        return avoidNameCollisions(setters);
    }

    private SortedSet<Setter> avoidNameCollisions(final Set<Setter> setters) {
        final SortedSet<Setter> noNameCollisions = new TreeSet<>();
        for (final Setter setter : setters) {
            if (noNameCollisions.stream().map(Setter::getParamName).noneMatch(setter.getParamName()::equals)) {
                noNameCollisions.add(setter);
            } else {
                for (int i = 0; true; i++) {
                    final String paramName = setter.getParamName() + i;
                    if (noNameCollisions.stream().map(Setter::getParamName).noneMatch(paramName::equals)) {
                        noNameCollisions.add(setter.withParamName(paramName));
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
                .filter(metadata -> !metadata.getBuiltType().getSetters().isEmpty()) //
                .collect(Collectors.toSet());
    }
}
