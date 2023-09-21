package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.google.common.reflect.TypeToken;
import com.squareup.javapoet.CodeBlock;
import com.sun.net.httpserver.Headers;
import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.CollectionInitializerCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.MapInitializerCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.CollectionSetter;
import io.github.tobi.laa.reflective.fluent.builders.model.MapSetter;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.print.attribute.standard.PrinterStateReasons;
import javax.script.SimpleBindings;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.*;
import java.util.concurrent.*;
import java.util.jar.Attributes;

/**
 * <p>
 * Implementation of {@link CollectionInitializerCodeGenerator} and {@link MapInitializerCodeGenerator} which covers the
 * well-known collections and maps from the Java Collections API. Prerequisite is that they can be initialized solely by
 * calling their default, parameterless constructor such as {@link java.util.ArrayList} or {@link java.util.HashMap}.
 * The collections and maps that are implemented by them such as {@link java.util.List} or {@link java.util.SortedMap}
 * are supported implicitly as well.
 * </p>
 */
@Named
@Singleton
class CollectionsApiInitializerCodeGenerator implements CollectionInitializerCodeGenerator, MapInitializerCodeGenerator {

    @SuppressWarnings("rawtypes")
    static final List<Class<? extends Collection>> SUPPORTED_COLLECTIONS = List.of(
            ArrayList.class, //
            HashSet.class, //
            TreeSet.class, //
            ArrayDeque.class, //
            ConcurrentLinkedDeque.class, //
            ConcurrentLinkedQueue.class, //
            ConcurrentSkipListSet.class, //
            CopyOnWriteArrayList.class, //
            CopyOnWriteArraySet.class, //
            DelayQueue.class, //
            LinkedBlockingDeque.class, //
            LinkedBlockingQueue.class, //
            LinkedHashSet.class, //
            LinkedList.class, //
            LinkedTransferQueue.class, //
            PriorityBlockingQueue.class, //
            PriorityQueue.class, //
            Stack.class, //
            SynchronousQueue.class);

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
    public boolean isApplicable(final CollectionSetter collectionSetter) {
        Objects.requireNonNull(collectionSetter);
        return SUPPORTED_COLLECTIONS.stream() //
                .anyMatch(type -> getRawType(collectionSetter.getParamType()).isAssignableFrom(type));
    }

    @Override
    public CodeBlock generateCollectionInitializer(final CollectionSetter collectionSetter) {
        Objects.requireNonNull(collectionSetter);
        return SUPPORTED_COLLECTIONS.stream() //
                .filter(type -> getRawType(collectionSetter.getParamType()).isAssignableFrom(type)) //
                .map(type -> CodeBlock.builder().add("new $T<>()", type).build()) //
                .findFirst() //
                .orElseThrow(() -> new CodeGenerationException("Generation of initializing code blocks for " + collectionSetter + " is not supported."));
    }

    @Override
    public boolean isApplicable(final MapSetter mapSetter) {
        Objects.requireNonNull(mapSetter);
        return SUPPORTED_MAPS.stream() //
                .anyMatch(type -> getRawType(mapSetter.getParamType()).isAssignableFrom(type));
    }

    @Override
    public CodeBlock generateMapInitializer(final MapSetter mapSetter) {
        Objects.requireNonNull(mapSetter);
        return SUPPORTED_MAPS.stream() //
                .filter(type -> getRawType(mapSetter.getParamType()).isAssignableFrom(type)) //
                .map(type -> CodeBlock.builder().add("new $T<>()", type).build()) //
                .findFirst() //
                .orElseThrow(() -> new CodeGenerationException("Generation of initializing code blocks for " + mapSetter + " is not supported."));
    }

    private Class<?> getRawType(final Type type) {
        return TypeToken.of(type).getRawType();
    }
}
