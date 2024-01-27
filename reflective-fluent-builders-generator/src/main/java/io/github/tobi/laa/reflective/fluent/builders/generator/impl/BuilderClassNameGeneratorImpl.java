package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.ClassName;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * Standard implementation of {@link BuilderClassNameGenerator}.
 * </p>
 */
@Named
@Singleton
class BuilderClassNameGeneratorImpl implements BuilderClassNameGenerator {

    @Override
    public ClassName generateClassName(final BuilderMetadata builderMetadata) {
        Objects.requireNonNull(builderMetadata);
        final Deque<BuilderMetadata> builderHierarchy = enclosingBuilderHierarchy(builderMetadata);
        BuilderMetadata currentBuilder = builderHierarchy.pop();
        ClassName className = ClassName.get(currentBuilder.getPackageName(), currentBuilder.getName());
        while (!builderHierarchy.isEmpty()) {
            currentBuilder = builderHierarchy.pop();
            className = className.nestedClass(currentBuilder.getName());
        }
        return className;
    }

    private Deque<BuilderMetadata> enclosingBuilderHierarchy(final BuilderMetadata builderMetadata) {
        final Deque<BuilderMetadata> builderHierarchy = new ArrayDeque<>();
        builderHierarchy.push(builderMetadata);
        Optional<BuilderMetadata> currentEnclosingBuilder = builderMetadata.getEnclosingBuilder();
        while (currentEnclosingBuilder.isPresent()) {
            builderHierarchy.push(currentEnclosingBuilder.get());
            currentEnclosingBuilder = currentEnclosingBuilder.get().getEnclosingBuilder();
        }
        return builderHierarchy;
    }
}
