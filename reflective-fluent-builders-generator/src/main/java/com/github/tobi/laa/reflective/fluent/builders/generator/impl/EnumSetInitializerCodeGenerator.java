package com.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import com.github.tobi.laa.reflective.fluent.builders.generator.api.CollectionInitializerCodeGenerator;
import com.github.tobi.laa.reflective.fluent.builders.model.CollectionSetter;
import com.squareup.javapoet.CodeBlock;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.EnumSet;
import java.util.Objects;

/**
 * <p>
 * Implementation of {@link CollectionInitializerCodeGenerator} which covers {@link java.util.EnumSet}.
 * </p>
 */
@Named
@Singleton
class EnumSetInitializerCodeGenerator implements CollectionInitializerCodeGenerator {

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
