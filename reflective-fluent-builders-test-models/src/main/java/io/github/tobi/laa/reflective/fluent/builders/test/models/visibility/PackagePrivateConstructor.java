package io.github.tobi.laa.reflective.fluent.builders.test.models.visibility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class PackagePrivateConstructor {

    private int intField;

    private PackagePrivate packagePrivate;

    private PrivateClass privateClass;

    private static class PrivateClass {
        // no content
    }
}
