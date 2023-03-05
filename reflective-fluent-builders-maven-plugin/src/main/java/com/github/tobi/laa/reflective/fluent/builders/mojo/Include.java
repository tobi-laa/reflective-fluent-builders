package com.github.tobi.laa.reflective.fluent.builders.mojo;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * An {@code <include>} tag as specified within the {@link GenerateBuildersMojo} plugin configuration.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Valid
public class Include {

    private String packageName;

    private String className;
}
