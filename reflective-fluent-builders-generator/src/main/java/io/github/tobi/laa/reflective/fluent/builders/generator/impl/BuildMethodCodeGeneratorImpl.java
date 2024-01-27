package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.MethodSpec;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuildMethodCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuildMethodStepCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.model.WriteAccessor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.lang.model.element.Modifier;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;

import static com.google.common.collect.ImmutableSortedSet.copyOf;
import static io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.OBJECT_SUPPLIER_FIELD_NAME;

/**
 * <p>
 * Standard implementation of {@link BuildMethodCodeGenerator}.
 * </p>
 */
@Named
@Singleton
class BuildMethodCodeGeneratorImpl implements BuildMethodCodeGenerator {

    private static final String OBJECT_TO_BUILD_FIELD_NAME = "objectToBuild";

    @lombok.NonNull
    private final SortedSet<BuildMethodStepCodeGenerator> stepCodeGenerators;

    @Inject
    @SuppressWarnings("unused")
    BuildMethodCodeGeneratorImpl(final Set<BuildMethodStepCodeGenerator> stepCodeGenerators) {
        // to ensure deterministic outputs, sets are sorted on construction
        final var compareByClassName = Comparator.comparing(o -> o.getClass().getName());
        this.stepCodeGenerators = copyOf(compareByClassName, stepCodeGenerators);
    }

    @Override
    public MethodSpec generateBuildMethod(final BuilderMetadata builderMetadata) {
        Objects.requireNonNull(builderMetadata);
        final var clazz = builderMetadata.getBuiltType().getType();
        final MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("build")
                .addModifiers(Modifier.PUBLIC)
                .returns(clazz.loadClass());
        builderMetadata
                .getExceptionTypes()
                .stream()
                .filter(this::isCheckedException)
                .sorted(Comparator.comparing(Class::getName))
                .forEach(methodBuilder::addException);
        methodBuilder.addStatement("final $T $L = this.$L.get()", clazz.loadClass(), OBJECT_TO_BUILD_FIELD_NAME, OBJECT_SUPPLIER_FIELD_NAME);
        for (final WriteAccessor writeAccessor : builderMetadata.getBuiltType().getWriteAccessors()) {
            stepCodeGenerators.stream()
                    .filter(gen -> gen.isApplicable(writeAccessor))
                    .forEach(gen -> methodBuilder.addCode(gen.generate(writeAccessor)));
        }
        methodBuilder.addStatement("return $L", OBJECT_TO_BUILD_FIELD_NAME);
        return methodBuilder.build();
    }

    private boolean isCheckedException(final Class<? extends Throwable> exceptionType) {
        return !RuntimeException.class.isAssignableFrom(exceptionType) && !Error.class.isAssignableFrom(exceptionType);
    }
}
