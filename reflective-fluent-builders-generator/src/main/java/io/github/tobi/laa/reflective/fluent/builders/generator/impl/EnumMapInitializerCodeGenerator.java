package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.CodeBlock;
import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.MapInitializerCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.MapType;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.EnumMap;
import java.util.Objects;

/**
 * <p>
 * Implementation of {@link MapInitializerCodeGenerator} which covers {@link EnumMap}.
 * </p>
 */
@Named
@Singleton
class EnumMapInitializerCodeGenerator implements MapInitializerCodeGenerator {

    @Override
    public boolean isApplicable(final MapType mapType) {
        Objects.requireNonNull(mapType);
        return mapType.getType() == EnumMap.class;
    }

    @Override
    public CodeBlock generateMapInitializer(final MapType mapType) {
        if (isApplicable(mapType)) {
            return CodeBlock.builder()
                    .add("new $T<>($T.class)", mapType.getType(), mapType.getKeyType())
                    .build();
        } else {
            throw new CodeGenerationException("Generation of initializing code blocks for " + mapType + " is not supported.");
        }
    }
}
