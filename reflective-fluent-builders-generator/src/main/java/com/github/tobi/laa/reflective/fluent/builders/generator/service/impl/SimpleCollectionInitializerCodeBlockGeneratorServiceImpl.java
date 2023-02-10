package com.github.tobi.laa.reflective.fluent.builders.generator.service.impl;

import com.github.tobi.laa.reflective.fluent.builders.generator.exception.CodeGenerationException;
import com.github.tobi.laa.reflective.fluent.builders.generator.model.CollectionSetter;
import com.github.tobi.laa.reflective.fluent.builders.generator.service.api.CollectionInitializerCodeBlockGeneratorService;
import com.squareup.javapoet.CodeBlock;

import java.util.*;
import java.util.concurrent.*;

/**
 * <p>
 * Implementation of {@link CollectionInitializerCodeBlockGeneratorService} which covers all well-known collections that
 * can be initialized solely by calling their default, parameterless constructor such as {@link java.util.ArrayList} as
 * well as the collections that are implemented by them such as {@link java.util.List}.
 * </p>
 */
public class SimpleCollectionInitializerCodeBlockGeneratorServiceImpl implements CollectionInitializerCodeBlockGeneratorService {

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

    @Override
    public boolean isApplicable(final CollectionSetter collectionSetter) {
        Objects.requireNonNull(collectionSetter);
        return SUPPORTED_COLLECTIONS.stream() //
                .anyMatch(type -> collectionSetter.getParamType().isAssignableFrom(type));
    }

    @Override
    public CodeBlock generateCollectionInitializer(final CollectionSetter collectionSetter) {
        Objects.requireNonNull(collectionSetter);
        return SUPPORTED_COLLECTIONS.stream() //
                .filter(type -> collectionSetter.getParamType().isAssignableFrom(type)) //
                .map(type -> CodeBlock.builder().add("new $T<>()", type).build()) //
                .findFirst() //
                .orElseThrow(() -> new CodeGenerationException("Generation of initializing code blocks for " + collectionSetter + " is not supported."));
    }
}
