package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.CodeBlock;
import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants;
import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuildMethodStepCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.Adder;
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
 * Implementation of {@link BuildMethodStepCodeGenerator} for adders.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
class AdderBuildMethodStepCodeGenerator implements BuildMethodStepCodeGenerator {

    @lombok.NonNull
    private final WriteAccessorService writeAccessorService;

    @Override
    public boolean isApplicable(final WriteAccessor writeAccessor) {
        Objects.requireNonNull(writeAccessor);
        return writeAccessorService.isAdder(writeAccessor);
    }

    @Override
    public CodeBlock generate(final WriteAccessor writeAccessor) {
        Objects.requireNonNull(writeAccessor);
        if (!isApplicable(writeAccessor)) {
            throw new CodeGenerationException("This generator is not applicable for " + writeAccessor);
        } else {
            final Adder adder = (Adder) writeAccessor;
            return CodeBlock.builder()
                    .beginControlFlow(
                            "if (this.$1L.$3L && this.$2L.$3L != null)",
                            BuilderConstants.CallSetterFor.FIELD_NAME,
                            BuilderConstants.FieldValue.FIELD_NAME,
                            adder.getPropertyName())
                    .addStatement(
                            "this.$L.$L.forEach($L::$L)",
                            BuilderConstants.FieldValue.FIELD_NAME,
                            adder.getPropertyName(),
                            OBJECT_TO_BUILD_VARIABLE_NAME,
                            adder.getMethodName())
                    .endControlFlow()
                    .build();
        }
    }
}
