package com.github.tobi.laa.reflective.fluent.builders.mojo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * Fields shared between {@link Include} and {@link Exclude}.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
abstract class AbstractIncludeExclude {

    private String packageName;

    private String className;
}