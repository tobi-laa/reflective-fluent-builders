package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.CodeBlock;
import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants;
import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuildMethodStepCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.FieldAccessor;
import io.github.tobi.laa.reflective.fluent.builders.model.WriteAccessor;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Objects;

import static io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.OBJECT_TO_BUILD_VARIABLE_NAME;

/**
 * <p>
 * Implementation of {@link BuildMethodStepCodeGenerator} for (non-final) {@link FieldAccessor field accessors}.
 * </p>
 */
@Named
@Singleton
class FieldAccessorBuildMethodStepCodeGenerator implements BuildMethodStepCodeGenerator {

    @Override
    public boolean isApplicable(final WriteAccessor writeAccessor) {
        Objects.requireNonNull(writeAccessor);
        return writeAccessor instanceof FieldAccessor fieldAccessor && !fieldAccessor.isFinal();
    }

    @Override
    public CodeBlock generate(final WriteAccessor writeAccessor) {
        Objects.requireNonNull(writeAccessor);
        if (!isApplicable(writeAccessor)) {
            throw new CodeGenerationException("This generator is not applicable for " + writeAccessor);
        } else {
            return CodeBlock.builder()
                    .beginControlFlow("if (this.$L.$L)", BuilderConstants.CallSetterFor.FIELD_NAME, writeAccessor.getPropertyName())
                    .addStatement(
                            "$L.$L = this.$L.$L",
                            OBJECT_TO_BUILD_VARIABLE_NAME,
                            writeAccessor.getPropertyName(),
                            BuilderConstants.FieldValue.FIELD_NAME,
                            writeAccessor.getPropertyName())
                    .endControlFlow()
                    .build();
        }
    }
}
