package io.github.tobi.laa.reflective.fluent.builders.mojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * An {@code <include>} tag as specified within the {@link GenerateBuildersMojo} plugin configuration.
 * </p>
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ValidInclude
public class Include extends AbstractIncludeExclude {

    Include(final String packageName, final String className) {
        super(packageName, className);
    }
}
