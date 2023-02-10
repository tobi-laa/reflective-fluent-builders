package com.github.tobi.laa.reflective.fluent.builders.generator.service.impl;

import com.github.tobi.laa.reflective.fluent.builders.generator.exception.CodeGenerationException;
import com.github.tobi.laa.reflective.fluent.builders.generator.model.CollectionSetter;
import com.github.tobi.laa.reflective.fluent.builders.generator.service.api.CollectionInitializerCodeBlockGeneratorService;
import com.squareup.javapoet.CodeBlock;

import java.util.EnumSet;
import java.util.Objects;

/**
 * <p>
 * Implementation of {@link CollectionInitializerCodeBlockGeneratorService} which covers {@link java.util.EnumSet}.
 * </p>
 */
public class EnumSetInitializerCodeBlockerGeneratorServiceImpl implements CollectionInitializerCodeBlockGeneratorService {

    @Override
    public boolean isApplicable(final CollectionSetter collectionSetter) {
        Objects.requireNonNull(collectionSetter);
        return collectionSetter.getParamType() == EnumSet.class;
    }

    @Override
    public CodeBlock generateCollectionInitializer(final CollectionSetter collectionSetter) {
        if (isApplicable(collectionSetter)) {
            return CodeBlock.builder()
                    .add("$T.noneOf($T.class)", collectionSetter.getParamType(), collectionSetter.getParamTypeArg())
                    .build();
        } else {
            throw new CodeGenerationException("Generation of initializing code blocks for " + collectionSetter + " is not supported.");
        }
    }
}
