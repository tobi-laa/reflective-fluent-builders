package com.github.tobi.laa.reflective.fluent.builders.mojo;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * <p>
 * Validation annotation for {@link Include}.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Constraint(validatedBy = IncludeValidator.class)
@interface ValidInclude {

    String message() default "Invalid <include> tag. Exactly one of the fields packageName or className needs to be initialized.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
