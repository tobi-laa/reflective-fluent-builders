package com.github.tobi.laa.reflective.fluent.builders.mojo;

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
@ValidExclude
public class Exclude extends AbstractIncludeExclude {

    private String packageRegex;

    private String classRegex;

    Exclude(final String packageName, final String packageRegex, final String className, final String classRegex) {
        super(packageName, className);
        this.packageRegex = packageRegex;
        this.classRegex = classRegex;
    }

    Predicate<Class<?>> toPredicate() {
        if (getPackageName() != null) {
            return c -> c.getPackage().getName().equals(getPackageName());
        } else if (packageRegex != null) {
            return c -> Pattern.compile(packageRegex).matcher(c.getPackage().getName()).find();
        } else if (getClassName() != null) {
            return c -> c.getName().equals(getClassName());
        } else if (classRegex != null) {
            return c -> Pattern.compile(classRegex).matcher(c.getName()).find();
        } else {
            throw new IllegalStateException("No field has been initialized. Exactly one of the fields packageName, packageRegex, className or classRegex needs to be initialized in order to be able to construct a predicate.");
        }
    }
}
