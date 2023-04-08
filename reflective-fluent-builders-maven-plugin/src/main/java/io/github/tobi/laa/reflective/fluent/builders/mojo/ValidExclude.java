package io.github.tobi.laa.reflective.fluent.builders.mojo;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * <p>
 * Validation annotation for {@link Exclude}.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Constraint(validatedBy = ExcludeValidator.class)
@interface ValidExclude {

    String message() default "Invalid <exclude> tag. Exactly one of the fields packageName, packageRegex, className or classRegex needs to be initialized.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
