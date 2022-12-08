package com.github.tobi.laa.reflective.fluent.builders.test.models;

@lombok.Setter
public class SimpleClass {

    private int anInt;

    private String aString;

    private boolean booleanField;

    private Class<?> setClass;

    void anInt(final int anInt) {
        this.anInt = anInt;
    }

    void aString(final String aString) {
        this.aString = aString;
    }
}