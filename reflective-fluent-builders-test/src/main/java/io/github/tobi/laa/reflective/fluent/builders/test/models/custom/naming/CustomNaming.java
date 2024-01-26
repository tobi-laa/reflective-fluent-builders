package io.github.tobi.laa.reflective.fluent.builders.test.models.custom.naming;

import java.util.List;

public class CustomNaming {

    private String simpleField;

    private List<String> collectionField;

    private List<String> collectionFieldWithAdder;

    public void modifyField(final String simpleField) {
        this.simpleField = simpleField;
    }

    public List<String> retrieveCollectionField() {
        return collectionField;
    }

    public void insertAnotherFieldIntoCollection(final String anotherField) {
        collectionField.add(anotherField);
    }
}
