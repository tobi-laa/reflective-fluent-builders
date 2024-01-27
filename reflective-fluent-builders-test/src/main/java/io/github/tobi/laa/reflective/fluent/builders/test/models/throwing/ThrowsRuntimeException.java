package io.github.tobi.laa.reflective.fluent.builders.test.models.throwing;

@SuppressWarnings("all")
public class ThrowsRuntimeException {

    public void setAnInt(final int anInt) throws IllegalArgumentException {
        // do nothing
    }

    public void setAString(final String aString) throws IllegalStateException {
        // do nothing
    }

    void addAnItem(final String item) throws RuntimeException {
        // do nothing
    }
}
