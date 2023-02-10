package com.github.tobi.laa.reflective.fluent.builders.generator.service.impl;

import com.github.tobi.laa.reflective.fluent.builders.generator.exception.CodeGenerationException;
import com.github.tobi.laa.reflective.fluent.builders.generator.model.MapSetter;
import com.github.tobi.laa.reflective.fluent.builders.generator.service.api.MapInitializerCodeBlockGeneratorService;
import com.squareup.javapoet.CodeBlock;
import com.sun.net.httpserver.Headers;

import javax.print.attribute.standard.PrinterStateReasons;
import javax.script.SimpleBindings;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.jar.Attributes;

/**
 * <p>
 * Implementation of {@link MapInitializerCodeBlockGeneratorService} which covers all well-known maps that
 * can be initialized solely by calling their default, parameterless constructor such as {@link java.util.HashMap} as
 * well as the maps that are implemented by them such as {@link java.util.SortedMap}.
 * </p>
 */
public class SimpleMapInitializerCodeBlockGeneratorServiceImpl implements MapInitializerCodeBlockGeneratorService {

    @SuppressWarnings("rawtypes")
    static final List<Class<? extends Map>> SUPPORTED_MAPS = List.of( //
            HashMap.class, //
            LinkedHashMap.class, //
            TreeMap.class, //
            ConcurrentHashMap.class, //
            ConcurrentSkipListMap.class, //
            Attributes.class, //
            Hashtable.class, //
            Headers.class, //
            IdentityHashMap.class, //
            PrinterStateReasons.class, //
            Properties.class, //
            RenderingHints.class, //
            SimpleBindings.class, //
            UIDefaults.class, //
            WeakHashMap.class);

    @Override
    public boolean isApplicable(final MapSetter mapSetter) {
        Objects.requireNonNull(mapSetter);
        return SUPPORTED_MAPS.stream() //
                .anyMatch(type -> mapSetter.getParamType().isAssignableFrom(type));
    }

    @Override
    public CodeBlock generateMapInitializer(final MapSetter mapSetter) {
        Objects.requireNonNull(mapSetter);
        return SUPPORTED_MAPS.stream() //
                .filter(type -> mapSetter.getParamType().isAssignableFrom(type)) //
                .map(type -> CodeBlock.builder().add("new $T<>()", type).build()) //
                .findFirst() //
                .orElseThrow(() -> new CodeGenerationException("Generation of initializing code blocks for " + mapSetter + " is not supported."));
    }
}
