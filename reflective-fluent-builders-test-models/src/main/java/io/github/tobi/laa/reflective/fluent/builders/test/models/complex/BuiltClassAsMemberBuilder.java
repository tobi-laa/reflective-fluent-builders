package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import lombok.Data;

class BuiltClassAsMemberBuilder {

    private int aField;

    BuiltClassAsMemberBuilder aField(int aField) {
        this.aField = aField;
        return this;
    }

    BuiltClassAsMember build() {
        final BuiltClassAsMember result = new BuiltClassAsMember();
        result.setAField(aField);
        return result;
    }

    @Data
    static class BuiltClassAsMember {
        private int aField;
    }
}