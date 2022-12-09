package com.github.tobi.laa.reflective.fluent.builders.test.models.visibility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PrivateConstructor {

    private int intField;
}
