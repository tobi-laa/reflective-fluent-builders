package com.github.tobi.laa.reflective.fluent.builders.mojo;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * <p>
 * An {@code <exclude>} tag as specified within the {@link GenerateBuildersMojo} plugin configuration.
 * </p>
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Valid
public class Exclude extends Include {

    private String packageRegex;

    private String classRegex;

    Exclude(final String packageName, final String packageRegex, final String className, final String classRegex) {
        super(packageName, className);
        this.packageRegex = packageRegex;
        this.classRegex = classRegex;
    }

    Predicate<Class<?>> toPredicate() {
        if (getPackageName() != null) {
            return c -> c.getPackageName().equals(getPackageName());
        } else if (packageRegex != null) {
            return c -> Pattern.compile(packageRegex).matcher(c.getPackageName()).find();
        } else if (getClassName() != null) {
            return c -> c.getName().equals(getClassName());
        } else if (classRegex != null) {
            return c -> Pattern.compile(classRegex).matcher(c.getName()).find();
        } else {
            throw new IllegalStateException("No field has been initialized. Exactly one of the fields packageName, packageRegex, className or classRegex needs to be initialized in order to be able to construct a predicate.");
        }
    }
}
