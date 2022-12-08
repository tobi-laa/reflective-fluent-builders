package com.github.tobi.laa.reflective.fluent.builders.generator.service.impl;

import com.github.tobi.laa.reflective.fluent.builders.generator.model.*;
import com.github.tobi.laa.reflective.fluent.builders.generator.service.api.ClassService;
import com.github.tobi.laa.reflective.fluent.builders.generator.service.api.SetterService;
import com.github.tobi.laa.reflective.fluent.builders.generator.service.api.VisibilityService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

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
@RequiredArgsConstructor
public class SetterServiceImpl implements SetterService {

    @lombok.NonNull
    private final VisibilityService visibilityService;

    @lombok.NonNull
    private final ClassService classService;

    @lombok.NonNull
    private final String setterPrefix;

    @Override
    public Set<Setter> gatherAllSetters(final Class<?> clazz) {
        Objects.requireNonNull(clazz);
        return classService.collectFullClassHierarchy(clazz) //
                .stream() //
                .map(Class::getDeclaredMethods) //
                .flatMap(Arrays::stream) //
                .filter(this::isSetter) //
                .map(this::toSetter) //
                .collect(Collectors.toSet());
    }

    private boolean isSetter(final Method method) {
        return method.getParameterCount() == 1 && method.getName().startsWith(setterPrefix);
    }

    private Setter toSetter(final Method method) {
        final var param = method.getParameters()[0];
        if (param.getType().isArray()) {
            return ArraySetter.builder().paramComponentType(param.getType().getComponentType())
                    .methodName(method.getName())
                    .paramType(param.getType())
                    .paramName(dropSetterPrefix(method.getName()))
                    .visibility(visibilityService.toVisibility(param.getModifiers()))
                    .build();
        } else if (Collection.class.isAssignableFrom(param.getType())) {
            return CollectionSetter.builder().paramTypeArg(typeArg(param, 0))
                    .methodName(method.getName())
                    .paramType(param.getType())
                    .paramName(dropSetterPrefix(method.getName()))
                    .visibility(visibilityService.toVisibility(param.getModifiers()))
                    .build();
        } else if (Map.class.isAssignableFrom(param.getType())) {
            return MapSetter.builder()
                    .keyType(typeArg(param, 0))
                    .valueType(typeArg(param, 1))
                    .methodName(method.getName())
                    .paramType(param.getType())
                    .paramName(dropSetterPrefix(method.getName()))
                    .visibility(visibilityService.toVisibility(param.getModifiers()))
                    .build();
        } else {
            return SimpleSetter.builder()
                    .methodName(method.getName())
                    .paramType(param.getType())
                    .paramName(dropSetterPrefix(method.getName()))
                    .visibility(visibilityService.toVisibility(param.getModifiers()))
                    .build();
        }
    }

    private Type typeArg(final Parameter param, final int num) {
        if (param.getParameterizedType() instanceof final ParameterizedType parameterizedType) {
            return parameterizedType.getActualTypeArguments()[num];
        } else {
            return Object.class;
        }
    }

    @Override
    public String dropSetterPrefix(final String name) {
        Objects.requireNonNull(name);
        if (StringUtils.isEmpty(setterPrefix) || name.length() <= setterPrefix.length()) {
            return name;
        }
        final var paramName = name.replaceFirst('^' + Pattern.quote(setterPrefix), "");
        return StringUtils.uncapitalize(paramName);
    }
}
