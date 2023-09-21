package io.github.tobi.laa.reflective.fluent.builders.service.impl;

import com.google.common.collect.ImmutableSortedSet;
import com.google.common.reflect.TypeToken;
import io.github.tobi.laa.reflective.fluent.builders.model.*;
import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import io.github.tobi.laa.reflective.fluent.builders.service.api.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.google.common.base.Predicates.not;

/**
 * <p>
 * Standard implementation of {@link SetterService}.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
class SetterServiceImpl implements SetterService {

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
    public SortedSet<Setter> gatherAllSetters(final Class<?> clazz) {
        Objects.requireNonNull(clazz);
        final String builderPackage = builderPackageService.resolveBuilderPackage(clazz);
        final List<Method> methods = classService.collectFullClassHierarchy(clazz) //
                .stream() //
                .map(Class::getDeclaredMethods) //
                .flatMap(Arrays::stream) //
                .filter(not(Method::isBridge)) //
                .filter(method -> accessibilityService.isAccessibleFrom(method, builderPackage)) //
                .collect(Collectors.toList());
        final ImmutableSortedSet<Setter> setters = methods.stream() //
                .filter(this::isSetter) //
                .map(method -> toSetter(clazz, method)) //
                .collect(ImmutableSortedSet.toImmutableSortedSet(Comparator.naturalOrder()));
        if (properties.isGetAndAddEnabled()) {
            final ImmutableSortedSet<CollectionGetAndAdder> getAndAdders = methods.stream() //
                    .filter(this::isCollectionGetter) //
                    .filter(method -> noCorrespondingSetter(method, setters)) //
                    .map(method -> toGetAndAdder(clazz, method)) //
                    .collect(ImmutableSortedSet.toImmutableSortedSet(Comparator.naturalOrder()));
            return ImmutableSortedSet.<Setter>naturalOrder().addAll(setters).addAll(getAndAdders).build();
        } else {
            return setters;
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
                .noneMatch(setter -> getRawType(setter.getParamType()) == method.getReturnType() &&
                        setter.getParamName().equals(dropGetterPrefix(method.getName())));
    }

    private Class<?> getRawType(final Type type) {
        return TypeToken.of(type).getRawType();
    }

    private Setter toSetter(final Class<?> clazz, final Method method) {
        final Parameter param = method.getParameters()[0];
        final Type paramType = resolveType(clazz, method.getGenericParameterTypes()[0]);
        if (param.getType().isArray()) {
            return ArraySetter.builder() //
                    .paramComponentType(param.getType().getComponentType()) //
                    .methodName(method.getName()) //
                    .paramType(paramType) //
                    .paramName(dropSetterPrefix(method.getName())) //
                    .visibility(visibilityService.toVisibility(method.getModifiers())) //
                    .declaringClass(method.getDeclaringClass()) //
                    .build();
        } else if (Collection.class.isAssignableFrom(param.getType())) {
            final Type collectionType = resolveCollectionType(clazz, paramType);
            return CollectionSetter.builder() //
                    .paramTypeArg(typeArg(collectionType, 0)) //
                    .methodName(method.getName()) //
                    .paramType(paramType) //
                    .paramName(dropSetterPrefix(method.getName())) //
                    .visibility(visibilityService.toVisibility(method.getModifiers())) //
                    .declaringClass(method.getDeclaringClass()) //
                    .build();
        } else if (Map.class.isAssignableFrom(param.getType())) {
            final Type mapType = resolveMapType(clazz, paramType);
            return MapSetter.builder() //
                    .keyType(typeArg(mapType, 0)) //
                    .valueType(typeArg(mapType, 1)) //
                    .methodName(method.getName()) //
                    .paramType(paramType) //
                    .paramName(dropSetterPrefix(method.getName())) //
                    .visibility(visibilityService.toVisibility(method.getModifiers())) //
                    .declaringClass(method.getDeclaringClass()) //
                    .build();
        } else {
            return SimpleSetter.builder() //
                    .methodName(method.getName()) //
                    .paramType(paramType) //
                    .paramName(dropSetterPrefix(method.getName())) //
                    .visibility(visibilityService.toVisibility(method.getModifiers())) //
                    .declaringClass(method.getDeclaringClass()) //
                    .build();
        }
    }

    @SuppressWarnings("java:S3252")
    private CollectionGetAndAdder toGetAndAdder(final Class<?> clazz, final Method method) {
        final Type paramType = resolveType(clazz, method.getGenericReturnType());
        final Type collectionType = resolveCollectionType(clazz, paramType);
        return CollectionGetAndAdder.builder() //
                .paramTypeArg(typeArg(collectionType, 0)) //
                .methodName(method.getName()) //
                .paramType(paramType) //
                .paramName(dropGetterPrefix(method.getName())) //
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
        final String paramName = name.replaceFirst('^' + Pattern.quote(prefix), "");
        return StringUtils.uncapitalize(paramName);
    }
}
