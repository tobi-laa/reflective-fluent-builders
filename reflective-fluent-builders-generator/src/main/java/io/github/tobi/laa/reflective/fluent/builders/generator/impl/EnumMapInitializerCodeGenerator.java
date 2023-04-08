package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.MapInitializerCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.MapSetter;
import com.squareup.javapoet.CodeBlock;

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
    public boolean isApplicable(final MapSetter mapSetter) {
        Objects.requireNonNull(mapSetter);
        return mapSetter.getParamType() == EnumMap.class;
    }

    @Override
    public CodeBlock generateMapInitializer(final MapSetter mapSetter) {
        if (isApplicable(mapSetter)) {
            return CodeBlock.builder()
                    .add("new $T<>($T.class)", mapSetter.getParamType(), mapSetter.getKeyType())
                    .build();
        } else {
            throw new CodeGenerationException("Generation of initializing code blocks for " + mapSetter + " is not supported.");
        }
    }
}
