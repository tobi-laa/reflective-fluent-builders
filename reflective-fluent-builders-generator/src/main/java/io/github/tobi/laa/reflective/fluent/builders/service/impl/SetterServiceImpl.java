package io.github.tobi.laa.reflective.fluent.builders.service.impl;

import com.google.common.collect.ImmutableSortedSet;
import io.github.tobi.laa.reflective.fluent.builders.model.*;
import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import io.github.tobi.laa.reflective.fluent.builders.service.api.ClassService;
import io.github.tobi.laa.reflective.fluent.builders.service.api.SetterService;
import io.github.tobi.laa.reflective.fluent.builders.service.api.VisibilityService;
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
    private final BuildersProperties properties;

    @Override
    public SortedSet<Setter> gatherAllSetters(final Class<?> clazz) {
        Objects.requireNonNull(clazz);
        final List<Method> methods = classService.collectFullClassHierarchy(clazz) //
                .stream() //
                .map(Class::getDeclaredMethods) //
                .flatMap(Arrays::stream) //
                .collect(Collectors.toList());
        final ImmutableSortedSet<Setter> setters = methods.stream() //
                .filter(this::isSetter) //
                .map(this::toSetter) //
                .collect(ImmutableSortedSet.toImmutableSortedSet(Comparator.naturalOrder()));
        if (properties.isGetAndAddEnabled()) {
            final ImmutableSortedSet<CollectionGetAndAdder> getAndAdders = methods.stream() //
                    .filter(this::isCollectionGetter) //
                    .filter(method -> noCorrespondingSetter(method, setters)) //
                    .map(this::toGetAndAdder) //
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
                .noneMatch(setter -> setter.getParamType() == method.getReturnType() &&
                        setter.getParamName().equals(dropGetterPrefix(method.getName())));
    }

    private Setter toSetter(final Method method) {
        final Parameter param = method.getParameters()[0];
        if (param.getType().isArray()) {
            return ArraySetter.builder().paramComponentType(param.getType().getComponentType()) //
                    .methodName(method.getName()) //
                    .paramType(param.getType()) //
                    .paramName(dropSetterPrefix(method.getName())) //
                    .visibility(visibilityService.toVisibility(method.getModifiers())) //
                    .build();
        } else if (Collection.class.isAssignableFrom(param.getType())) {
            return CollectionSetter.builder().paramTypeArg(typeArg(param, 0)) //
                    .methodName(method.getName()) //
                    .paramType(param.getType()) //
                    .paramName(dropSetterPrefix(method.getName())) //
                    .visibility(visibilityService.toVisibility(method.getModifiers())) //
                    .build();
        } else if (Map.class.isAssignableFrom(param.getType())) {
            return MapSetter.builder() //
                    .keyType(typeArg(param, 0)) //
                    .valueType(typeArg(param, 1)) //
                    .methodName(method.getName()) //
                    .paramType(param.getType()) //
                    .paramName(dropSetterPrefix(method.getName())) //
                    .visibility(visibilityService.toVisibility(method.getModifiers())) //
                    .build();
        } else {
            return SimpleSetter.builder() //
                    .methodName(method.getName()) //
                    .paramType(param.getType()) //
                    .paramName(dropSetterPrefix(method.getName())) //
                    .visibility(visibilityService.toVisibility(method.getModifiers())) //
                    .build();
        }
    }

    private CollectionGetAndAdder toGetAndAdder(final Method method) {
        final Type returnType = method.getGenericReturnType();
        return CollectionGetAndAdder.builder().paramTypeArg(typeArg(returnType, 0)) //
                .methodName(method.getName()) //
                .paramType(method.getReturnType()) //
                .paramName(dropGetterPrefix(method.getName())) //
                .visibility(visibilityService.toVisibility(method.getModifiers())) //
                .build();
    }

    private Type typeArg(final Parameter param, final int num) {
        return typeArg(param.getParameterizedType(), num);
    }

    private Type typeArg(final Type type, final int num) {
        if (type instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType) type;
            return parameterizedType.getActualTypeArguments()[num];
        } else {
            return Object.class;
        }
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
