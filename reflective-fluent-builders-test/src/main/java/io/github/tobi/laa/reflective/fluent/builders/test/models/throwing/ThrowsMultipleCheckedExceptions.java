package io.github.tobi.laa.reflective.fluent.builders.test.models.throwing;

import java.io.IOException;
import java.text.ParseException;

@SuppressWarnings("all")
public class ThrowsMultipleCheckedExceptions {

    public void setAnInt(final int anInt) throws IOException {
        // do nothing
    }

    public void setAString(final String aString) throws IllegalAccessException {
        // do nothing
    }

    public void setALong(final long aLong) throws ParseException {
        // do nothing
    }
}
