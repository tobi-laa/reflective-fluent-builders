package com.github.tobi.laa.reflective.fluent.builders.service.impl;

import com.github.tobi.laa.reflective.fluent.builders.model.*;
import com.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import com.github.tobi.laa.reflective.fluent.builders.service.api.ClassService;
import com.github.tobi.laa.reflective.fluent.builders.service.api.SetterService;
import com.github.tobi.laa.reflective.fluent.builders.service.api.VisibilityService;
import com.google.common.collect.ImmutableSortedSet;
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

/**
 * <p>
 * Standard implementation of {@link SetterService}.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
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
        return classService.collectFullClassHierarchy(clazz) //
                .stream() //
                .map(Class::getDeclaredMethods) //
                .flatMap(Arrays::stream) //
                .filter(this::isSetter) //
                .map(this::toSetter) //
                .collect(ImmutableSortedSet.toImmutableSortedSet(Comparator.naturalOrder()));
    }

    private boolean isSetter(final Method method) {
        return method.getParameterCount() == 1 && method.getName().startsWith(properties.getSetterPrefix());
    }

    private Setter toSetter(final Method method) {
        final Parameter param = method.getParameters()[0];
        if (param.getType().isArray()) {
            return ArraySetter.builder().paramComponentType(param.getType().getComponentType()) //
                    .methodName(method.getName()) //
                    .paramType(param.getType()) //
                    .paramName(dropSetterPrefix(method.getName())) //
                    .visibility(visibilityService.toVisibility(param.getModifiers())) //
                    .build();
        } else if (Collection.class.isAssignableFrom(param.getType())) {
            return CollectionSetter.builder().paramTypeArg(typeArg(param, 0)) //
                    .methodName(method.getName()) //
                    .paramType(param.getType()) //
                    .paramName(dropSetterPrefix(method.getName())) //
                    .visibility(visibilityService.toVisibility(param.getModifiers())) //
                    .build();
        } else if (Map.class.isAssignableFrom(param.getType())) {
            return MapSetter.builder() //
                    .keyType(typeArg(param, 0)) //
                    .valueType(typeArg(param, 1)) //
                    .methodName(method.getName()) //
                    .paramType(param.getType()) //
                    .paramName(dropSetterPrefix(method.getName())) //
                    .visibility(visibilityService.toVisibility(param.getModifiers())) //
                    .build();
        } else {
            return SimpleSetter.builder() //
                    .methodName(method.getName()) //
                    .paramType(param.getType()) //
                    .paramName(dropSetterPrefix(method.getName())) //
                    .visibility(visibilityService.toVisibility(param.getModifiers())) //
                    .build();
        }
    }

    private Type typeArg(final Parameter param, final int num) {
        if (param.getParameterizedType() instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType) param.getParameterizedType();
            return parameterizedType.getActualTypeArguments()[num];
        } else {
            return Object.class;
        }
    }

    @Override
    public String dropSetterPrefix(final String name) {
        Objects.requireNonNull(name);
        if (StringUtils.isEmpty(properties.getSetterPrefix()) || name.length() <= properties.getSetterPrefix().length()) {
            return name;
        }
        final String paramName = name.replaceFirst('^' + Pattern.quote(properties.getSetterPrefix()), "");
        return StringUtils.uncapitalize(paramName);
    }
}
