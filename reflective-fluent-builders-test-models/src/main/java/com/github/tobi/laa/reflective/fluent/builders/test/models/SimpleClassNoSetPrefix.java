package com.github.tobi.laa.reflective.fluent.builders.test.models;

public class SimpleClassNoSetPrefix {

    private int anInt;

    private String aString;

    void anInt(final int anInt) {
        this.anInt = anInt;
    }

    void aString(final String aString) {
        this.aString = aString;
    }
}
