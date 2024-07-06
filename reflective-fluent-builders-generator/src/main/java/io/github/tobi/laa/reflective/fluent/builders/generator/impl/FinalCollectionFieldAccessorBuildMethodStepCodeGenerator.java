package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.CodeBlock;
import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants;
import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuildMethodStepCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.CollectionType;
import io.github.tobi.laa.reflective.fluent.builders.model.FieldAccessor;
import io.github.tobi.laa.reflective.fluent.builders.model.WriteAccessor;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Objects;

import static io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.OBJECT_TO_BUILD_VARIABLE_NAME;

/**
 * <p>
 * Implementation of {@link BuildMethodStepCodeGenerator} for {@code final} collection {@link io.github.tobi.laa.reflective.fluent.builders.model.FieldAccessor field accessors}.
 * </p>
 */
@Named
@Singleton
class FinalCollectionFieldAccessorBuildMethodStepCodeGenerator implements BuildMethodStepCodeGenerator {

    @Override
    public boolean isApplicable(final WriteAccessor writeAccessor) {
        Objects.requireNonNull(writeAccessor);
        return writeAccessor instanceof FieldAccessor fieldAccessor
                && fieldAccessor.isFinal()
                && writeAccessor.getPropertyType() instanceof CollectionType;
    }

    @Override
    public CodeBlock generate(final WriteAccessor writeAccessor) {
        Objects.requireNonNull(writeAccessor);
        if (!isApplicable(writeAccessor)) {
            throw new CodeGenerationException("This generator is not applicable for " + writeAccessor);
        } else {
            return CodeBlock.builder()
                    .beginControlFlow(
                            "if (this.$1L.$3L && this.$2L.$3L != null)",
                            BuilderConstants.CallSetterFor.FIELD_NAME,
                            BuilderConstants.FieldValue.FIELD_NAME,
                            writeAccessor.getPropertyName())
                    .addStatement(
                            "this.$1L.$2L.forEach($3L.$2L::add)",
                            BuilderConstants.FieldValue.FIELD_NAME,
                            writeAccessor.getPropertyName(),
                            OBJECT_TO_BUILD_VARIABLE_NAME)
                    .endControlFlow()
                    .build();
        }
    }
}
