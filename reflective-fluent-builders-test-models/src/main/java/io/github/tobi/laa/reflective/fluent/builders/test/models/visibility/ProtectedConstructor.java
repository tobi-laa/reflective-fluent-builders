package io.github.tobi.laa.reflective.fluent.builders.test.models.visibility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProtectedConstructor {

    private int intField;
}
