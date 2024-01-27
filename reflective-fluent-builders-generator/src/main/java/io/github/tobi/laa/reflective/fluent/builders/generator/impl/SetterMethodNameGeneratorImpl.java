package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import io.github.tobi.laa.reflective.fluent.builders.generator.api.SetterMethodNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.Adder;
import io.github.tobi.laa.reflective.fluent.builders.model.Getter;
import io.github.tobi.laa.reflective.fluent.builders.model.Setter;
import io.github.tobi.laa.reflective.fluent.builders.model.WriteAccessor;
import io.github.tobi.laa.reflective.fluent.builders.service.api.WriteAccessorService;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Objects;

/**
 * <p>
 * Default implementation of {@link SetterMethodNameGenerator}.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
class SetterMethodNameGeneratorImpl implements SetterMethodNameGenerator {

    @lombok.NonNull
    private final WriteAccessorService writeAccessorService;

    @Override
    public String generate(final WriteAccessor writeAccessor) {
        Objects.requireNonNull(writeAccessor);
        if (writeAccessorService.isCollectionGetter(writeAccessor)) {
            final Getter getter = (Getter) writeAccessor;
            return writeAccessorService.dropGetterPrefix(getter.getMethodName());
        } else if (writeAccessorService.isSetter(writeAccessor)) {
            final Setter setter = (Setter) writeAccessor;
            return writeAccessorService.dropSetterPrefix(setter.getMethodName());
        } else if (writeAccessorService.isAdder(writeAccessor)) {
            final Adder adder = (Adder) writeAccessor;
            return writeAccessorService.dropAdderPattern(adder.getMethodName());
        } else {
            return writeAccessor.getPropertyName();
        }
    }
}
