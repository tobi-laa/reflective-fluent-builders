package com.github.tobi.laa.reflective.fluent.builders.mojo;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * <p>
 * Validator implementation for {@link ValidExclude}.
 * </p>
 */
public class ExcludeValidator implements ConstraintValidator<ValidExclude, Exclude> {

    @Override
    public boolean isValid(final Exclude exclude, final ConstraintValidatorContext context) {
        if (exclude == null) {
            return true;
        } else {
            return Stream.of( //
                            exclude.getPackageName(), //
                            exclude.getPackageRegex(), //
                            exclude.getClassName(), //
                            exclude.getClassRegex()) //
                    .filter(Objects::nonNull) //
                    .count() == 1;
        }
    }
}
