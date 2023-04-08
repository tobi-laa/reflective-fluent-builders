package io.github.tobi.laa.reflective.fluent.builders.mojo;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * <p>
 * Validator implementation for {@link ValidInclude}.
 * </p>
 */
public class IncludeValidator implements ConstraintValidator<ValidInclude, Include> {

    @Override
    public boolean isValid(final Include include, final ConstraintValidatorContext context) {
        if (include == null) {
            return true;
        } else {
            return Stream.of(include.getPackageName(), include.getClassName()) //
                    .filter(Objects::nonNull) //
                    .count() == 1;
        }
    }
}
