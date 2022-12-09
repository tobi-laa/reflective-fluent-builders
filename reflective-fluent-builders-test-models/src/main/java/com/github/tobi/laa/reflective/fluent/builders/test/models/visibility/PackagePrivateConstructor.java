package com.github.tobi.laa.reflective.fluent.builders.test.models.visibility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class PackagePrivateConstructor {

    private int intField;
}
