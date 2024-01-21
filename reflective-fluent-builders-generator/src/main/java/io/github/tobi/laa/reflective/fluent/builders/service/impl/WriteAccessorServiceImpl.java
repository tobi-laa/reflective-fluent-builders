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
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        final var methods = classService.collectFullClassHierarchy(classInfo) //
                .stream() //
                .map(ClassInfo::loadClass) //
                .map(Class::getDeclaredMethods) //
                .flatMap(Arrays::stream) //
                .filter(not(Method::isBridge)) //
                .filter(method -> accessibilityService.isAccessibleFrom(method, builderPackage)) //
                .collect(Collectors.toList());
        final var setters = methods.stream() //
                .filter(this::isSetter) //
                .map(method -> toSetter(clazz, method)) //
                .collect(ImmutableSortedSet.toImmutableSortedSet(Comparator.naturalOrder()));
        if (properties.isGetAndAddEnabled()) {
            final var collectionGetters = methods.stream() //
                    .filter(this::isCollectionGetter) //
                    .filter(method -> noCorrespondingSetter(method, setters)) //
                    .map(method -> toCollectionGetter(clazz, method)) //
                    .collect(ImmutableSortedSet.toImmutableSortedSet(Comparator.naturalOrder()));
            return ImmutableSortedSet.<WriteAccessor>naturalOrder().addAll(setters).addAll(collectionGetters).build();
        } else {
            return setters.stream().collect(ImmutableSortedSet.toImmutableSortedSet(Comparator.naturalOrder()));
        }
    }

    private boolean isSetter(final Method method) {
        return method.getParameterCount() == 1 && method.getName().startsWith(properties.getSetterPrefix());
    }

    private boolean isCollectionGetter(final Method method) {
        return method.getParameterCount() == 0 && //
                method.getName().startsWith(properties.getGetterPrefix()) && //
                Collection.class.isAssignableFrom(method.getReturnType());
    }

    private boolean noCorrespondingSetter(final Method method, final Set<Setter> setters) {
        return setters.stream()
                .noneMatch(setter -> getRawType(setter.getPropertyType().getType()) == method.getReturnType() &&
                        setter.getPropertyName().equals(dropGetterPrefix(method.getName())));
    }

    private Class<?> getRawType(final Type type) {
        return TypeToken.of(type).getRawType();
    }

    private Setter toSetter(final Class<?> clazz, final Method method) {
        final var param = method.getParameters()[0];
        final var type = resolveType(clazz, method.getGenericParameterTypes()[0]);
        final PropertyType propertyType;
        if (param.getType().isArray()) {
            propertyType = new ArrayType(type, param.getType().getComponentType());
        } else if (Collection.class.isAssignableFrom(param.getType())) {
            final var collectionType = resolveCollectionType(clazz, type);
            propertyType = new CollectionType(type, typeArg(collectionType, 0));
        } else if (Map.class.isAssignableFrom(param.getType())) {
            final var mapType = resolveMapType(clazz, type);
            propertyType = new MapType(type, typeArg(mapType, 0), typeArg(mapType, 1));
        } else {
            propertyType = new SimpleType(type);
        }
        return Setter.builder() //
                .methodName(method.getName()) //
                .propertyType(propertyType) //
                .propertyName(dropSetterPrefix(method.getName())) //
                .visibility(visibilityService.toVisibility(method.getModifiers())) //
                .declaringClass(method.getDeclaringClass()) //
                .build();
    }

    @SuppressWarnings("java:S3252")
    private Getter toCollectionGetter(final Class<?> clazz, final Method method) {
        final var propertyType = resolveType(clazz, method.getGenericReturnType());
        final var collectionType = resolveCollectionType(clazz, propertyType);
        return Getter.builder() //
                .propertyType(new CollectionType(propertyType, typeArg(collectionType, 0)))
                .methodName(method.getName()) //
                .propertyName(dropGetterPrefix(method.getName())) //
                .visibility(visibilityService.toVisibility(method.getModifiers())) //
                .declaringClass(method.getDeclaringClass()) //
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
