package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.CodeBlock;
import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants;
import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuildMethodStepCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.Setter;
import io.github.tobi.laa.reflective.fluent.builders.model.WriteAccessor;
import io.github.tobi.laa.reflective.fluent.builders.service.api.WriteAccessorService;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Objects;

import static io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.OBJECT_TO_BUILD_VARIABLE_NAME;

/**
 * <p>
 * Implementation of {@link BuildMethodStepCodeGenerator} for setters.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
class SetterBuildMethodStepCodeGenerator implements BuildMethodStepCodeGenerator {

    @lombok.NonNull
    private final WriteAccessorService writeAccessorService;

    @Override
    public boolean isApplicable(final WriteAccessor writeAccessor) {
        Objects.requireNonNull(writeAccessor);
        return writeAccessorService.isSetter(writeAccessor);
    }

    @Override
    public CodeBlock generate(final WriteAccessor writeAccessor) {
        Objects.requireNonNull(writeAccessor);
        if (!isApplicable(writeAccessor)) {
            throw new CodeGenerationException("This generator is not applicable for " + writeAccessor);
        } else {
            final Setter setter = (Setter) writeAccessor;
            return CodeBlock.builder()
                    .beginControlFlow("if (this.$L.$L)", BuilderConstants.CallSetterFor.FIELD_NAME, setter.getPropertyName())
                    .addStatement(
                            "$L.$L(this.$L.$L)",
                            OBJECT_TO_BUILD_VARIABLE_NAME,
                            setter.getMethodName(),
                            BuilderConstants.FieldValue.FIELD_NAME,
                            setter.getPropertyName())
                    .endControlFlow()
                    .build();
        }
    }
}
