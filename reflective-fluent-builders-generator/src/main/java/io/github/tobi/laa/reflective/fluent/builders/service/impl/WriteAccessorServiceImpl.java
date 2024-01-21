package io.github.tobi.laa.reflective.fluent.builders.service.impl;

import com.google.common.collect.ImmutableSortedSet;
import com.google.common.reflect.TypeToken;
import io.github.classgraph.ClassInfo;
import io.github.tobi.laa.reflective.fluent.builders.model.*;
import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import io.github.tobi.laa.reflective.fluent.builders.service.api.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.reflect.Modifier.isStatic;
import static java.util.function.Predicate.not;

/**
 * <p>
 * Standard implementation of {@link WriteAccessorService}.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
class WriteAccessorServiceImpl implements WriteAccessorService {

    @lombok.NonNull
    private final VisibilityService visibilityService;

    @lombok.NonNull
    private final ClassService classService;

    @lombok.NonNull
    private final AccessibilityService accessibilityService;

    @lombok.NonNull
    private final BuilderPackageService builderPackageService;

    @lombok.NonNull
    private final BuildersProperties properties;

    @Override
    public SortedSet<WriteAccessor> gatherAllWriteAccessors(final ClassInfo classInfo) {
        Objects.requireNonNull(classInfo);
        final var clazz = classInfo.loadClass();
        final var builderPackage = builderPackageService.resolveBuilderPackage(clazz);
        final var classHierarchy = classService.collectFullClassHierarchy(classInfo);
        final var methods = gatherAllNonStaticNonBridgeAccessibleMethods(classHierarchy, builderPackage);
        final SortedSet<WriteAccessor> writeAccessors = new TreeSet<>();
        writeAccessors.addAll(gatherAllSetters(methods, classInfo));
        if (properties.isGetAndAddEnabled()) {
            final var collectionGetters = gatherAllCollectionGetters(methods, classInfo);
            addAllThatAreNotYetCovered(writeAccessors, collectionGetters);
        }
        if (properties.isDirectFieldAccessEnabled()) {
            final var fields = gatherAllNonStaticAccessibleFields(classHierarchy, builderPackage);
            final var fieldAccessors = gatherAllFieldAccessors(fields, classInfo);
            addAllThatAreNotYetCovered(writeAccessors, fieldAccessors);
        }
        return ImmutableSortedSet.copyOf(writeAccessors);
    }

    private void addAllThatAreNotYetCovered(final SortedSet<WriteAccessor> target, final SortedSet<? extends WriteAccessor> candidates) {
        candidates
                .stream()
                .filter(candidate -> notYetCoveredByAnotherWriteAccessor(candidate, target))
                .forEach(target::add);
    }

    private List<Method> gatherAllNonStaticNonBridgeAccessibleMethods(final List<ClassInfo> classHierarchy, final String builderPackage) {
        return classHierarchy //
                .stream() //
                .map(ClassInfo::loadClass) //
                .map(Class::getDeclaredMethods) //
                .flatMap(Arrays::stream) //
                .filter(not(Method::isBridge)) //
                .filter(not(method -> isStatic(method.getModifiers()))) //
                .filter(method -> accessibilityService.isAccessibleFrom(method, builderPackage)) //
                .collect(Collectors.toList());
    }

    private SortedSet<Setter> gatherAllSetters(final List<Method> methods, final ClassInfo classInfo) {
        return methods.stream() //
                .filter(this::isSetter) //
                .map(method -> toSetter(classInfo.loadClass(), method)) //
                .collect(ImmutableSortedSet.toImmutableSortedSet(Comparator.naturalOrder()));
    }

    private boolean isSetter(final Method method) {
        return method.getParameterCount() == 1 && method.getName().startsWith(properties.getSetterPrefix());
    }

    private SortedSet<Getter> gatherAllCollectionGetters(final List<Method> methods, final ClassInfo classInfo) {
        return methods.stream() //
                .filter(this::isCollectionGetter) //
                .map(method -> toGetter(classInfo.loadClass(), method)) //
                .collect(ImmutableSortedSet.toImmutableSortedSet(Comparator.naturalOrder()));
    }

    private boolean isCollectionGetter(final Method method) {
        return method.getParameterCount() == 0 && //
                method.getName().startsWith(properties.getGetterPrefix()) && //
                Collection.class.isAssignableFrom(method.getReturnType());
    }

    private List<Field> gatherAllNonStaticAccessibleFields(final List<ClassInfo> classHierarchy, final String builderPackage) {
        return classHierarchy //
                .stream() //
                .map(ClassInfo::loadClass) //
                .map(Class::getDeclaredFields) //
                .flatMap(Arrays::stream) //
                .filter(not(field -> isStatic(field.getModifiers()))) //
                .filter(field -> accessibilityService.isAccessibleFrom(field, builderPackage)) //
                .collect(Collectors.toList());
    }

    private SortedSet<FieldAccessor> gatherAllFieldAccessors(final List<Field> fields, final ClassInfo classInfo) {
        return fields.stream() //
                .filter(this::isFieldAccessor) //
                .map(field -> toFieldAccessor(classInfo.loadClass(), field)) //
                .collect(ImmutableSortedSet.toImmutableSortedSet(Comparator.naturalOrder()));
    }

    private boolean isFieldAccessor(final Field field) {
        return !Modifier.isFinal(field.getModifiers()) ||
                // for final collections, it is assumed they are non-null so adding to them is possible
                Collection.class.isAssignableFrom(field.getType());
    }

    private boolean notYetCoveredByAnotherWriteAccessor(final WriteAccessor candidate, final Set<WriteAccessor> writeAccessors) {
        return writeAccessors
                .stream()
                .noneMatch(accessor -> getRawType(candidate.getPropertyType().getType()) == getRawType(accessor.getPropertyType().getType())
                        && candidate.getPropertyName().equals(accessor.getPropertyName()));
    }

    private Class<?> getRawType(final Type type) {
        return TypeToken.of(type).getRawType();
    }

    private Setter toSetter(final Class<?> clazz, final Method method) {
        final var param = method.getParameters()[0];
        return Setter.builder() //
                .methodName(method.getName()) //
                .propertyType(toPropertyType(clazz, param.getType(), method.getGenericParameterTypes()[0])) //
                .propertyName(dropSetterPrefix(method.getName())) //
                .visibility(visibilityService.toVisibility(method.getModifiers())) //
                .declaringClass(method.getDeclaringClass()) //
                .build();
    }

    private PropertyType toPropertyType(final Class<?> declaringClass, final Class<?> rawType, final Type genericType) {
        final var type = resolveType(declaringClass, genericType);
        if (rawType.isArray()) {
            return new ArrayType(type, rawType.getComponentType());
        } else if (Collection.class.isAssignableFrom(rawType)) {
            final var collectionType = resolveCollectionType(declaringClass, type);
            return new CollectionType(type, typeArg(collectionType, 0));
        } else if (Map.class.isAssignableFrom(rawType)) {
            final var mapType = resolveMapType(declaringClass, type);
            return new MapType(type, typeArg(mapType, 0), typeArg(mapType, 1));
        } else {
            return new SimpleType(type);
        }
    }

    @SuppressWarnings("java:S3252")
    private Getter toGetter(final Class<?> clazz, final Method method) {
        return Getter.builder() //
                .propertyType(toPropertyType(clazz, method.getReturnType(), method.getGenericReturnType())) //
                .methodName(method.getName()) //
                .propertyName(dropGetterPrefix(method.getName())) //
                .visibility(visibilityService.toVisibility(method.getModifiers())) //
                .declaringClass(method.getDeclaringClass()) //
                .build();
    }

    private FieldAccessor toFieldAccessor(final Class<?> clazz, final Field field) {
        return FieldAccessor.builder() //
                .propertyType(toPropertyType(clazz, field.getType(), field.getGenericType())) //
                .propertyName(field.getName())
                .visibility(visibilityService.toVisibility(field.getModifiers())) //
                .isFinal(Modifier.isFinal(field.getModifiers())) //
                .declaringClass(field.getDeclaringClass()) //
                .build();
    }

    @SuppressWarnings("unchecked")
    private Type resolveCollectionType(final Class<?> clazz, final Type collectionType) {
        if (collectionType instanceof ParameterizedType) {
            final TypeToken<? extends Collection<?>> typeToken = (TypeToken<? extends Collection<?>>) TypeToken.of(clazz).resolveType(collectionType);
            return typeToken.getSupertype(Collection.class).getType();
        } else {
            return Collection.class;
        }
    }

    @SuppressWarnings("unchecked")
    private Type resolveMapType(final Class<?> clazz, final Type mapType) {
        if (mapType instanceof ParameterizedType) {
            final TypeToken<? extends Map<?, ?>> typeToken = (TypeToken<? extends Map<?, ?>>) TypeToken.of(clazz).resolveType(mapType);
            return typeToken.getSupertype(Map.class).getType();
        } else {
            return Map.class;
        }
    }

    private Type typeArg(final Type type, final int num) {
        if (type instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType) type;
            return parameterizedType.getActualTypeArguments()[num];
        } else {
            return Object.class;
        }
    }

    private Type resolveType(final Class<?> clazz, final Type type) {
        return TypeToken.of(clazz).resolveType(type).getType();
    }

    @Override
    public String dropSetterPrefix(final String name) {
        Objects.requireNonNull(name);
        return dropMethodPrefix(properties.getSetterPrefix(), name);
    }

    @Override
    public String dropGetterPrefix(final String name) {
        Objects.requireNonNull(name);
        return dropMethodPrefix(properties.getGetterPrefix(), name);
    }

    private String dropMethodPrefix(final String prefix, final String name) {
        if (StringUtils.isEmpty(prefix) || name.length() <= prefix.length()) {
            return name;
        }
        final var paramName = name.replaceFirst('^' + Pattern.quote(prefix), "");
        return StringUtils.uncapitalize(paramName);
    }

    @Override
    public boolean isSetter(final WriteAccessor writeAccessor) {
        Objects.requireNonNull(writeAccessor);
        return writeAccessor instanceof Setter;
    }

    @Override
    public boolean isCollectionGetter(final WriteAccessor writeAccessor) {
        Objects.requireNonNull(writeAccessor);
        return writeAccessor instanceof Getter && writeAccessor.getPropertyType() instanceof CollectionType;
    }
}
