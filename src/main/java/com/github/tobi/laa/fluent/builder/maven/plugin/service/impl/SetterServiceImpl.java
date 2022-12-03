package com.github.tobi.laa.fluent.builder.maven.plugin.service.impl;

import com.github.tobi.laa.fluent.builder.maven.plugin.model.*;
import com.github.tobi.laa.fluent.builder.maven.plugin.service.api.SetterService;
import com.github.tobi.laa.fluent.builder.maven.plugin.service.api.VisibilityService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private final String setterPrefix;

    @Override
    public Set<Setter> gatherAllSetters(final Class<?> clazz) {
        Objects.requireNonNull(clazz);
        return fullClassHierarchy(clazz) //
                .map(Class::getDeclaredMethods) //
                .flatMap(Arrays::stream) //
                .filter(this::isSetter) //
                .map(this::toSetter) //
                .collect(Collectors.toSet());
    }

    // TODO add service for class hierarchy + finding all classes from a package
    private Stream<Class<?>> fullClassHierarchy(final Class<?> clazz) {
        final Set<Class<?>> classHierarchy = new HashSet<>();
        for (var i = clazz; i != null; i = i.getSuperclass()) {
            classHierarchy.add(i);
            Arrays.stream(i.getInterfaces()).forEach(classHierarchy::add);
        }
        classHierarchy.remove(Object.class);
        return classHierarchy.stream();
    }

    private boolean isSetter(final Method method) {
        return method.getParameterCount() == 1 && method.getName().startsWith(setterPrefix);
    }

    private Setter toSetter(final Method method) {
        final var param = method.getParameters()[0];
        final AbstractSetter.AbstractSetterBuilder builder;
        if (param.getType().isArray()) {
            builder = ArraySetter.builder().paramComponentType(param.getType().getComponentType());
        } else if (Collection.class.isAssignableFrom(param.getType())) {
            builder = CollectionSetter.builder().paramTypeArg(typeArg(param, 0));
        } else if (Map.class.isAssignableFrom(param.getType())) {
            builder = MapSetter.builder() //
                    .keyType(typeArg(param, 0)) //
                    .valueType(typeArg(param, 1));
        } else {
            builder = SimpleSetter.builder();
        }
        return builder
                .methodName(method.getName())
                .paramType(param.getType())
                .paramName(dropSetterPrefix(method.getName()))
                .visibility(visibilityService.toVisibility(param.getModifiers()))
                .build();
    }

    private Type typeArg(final Parameter param, final int num) {
        final Type typeArg;
        if (param.getParameterizedType() instanceof ParameterizedType) {
            final var parameterizedType = (ParameterizedType) param.getParameterizedType();
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
