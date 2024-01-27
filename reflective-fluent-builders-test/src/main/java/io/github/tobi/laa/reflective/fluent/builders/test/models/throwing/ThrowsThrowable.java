package io.github.tobi.laa.reflective.fluent.builders.test.models.throwing;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("all")
public class ThrowsThrowable {

    public void setAnInt(final int anInt) throws IOException {
        // do nothing
    }

    public void setAString(final String aString) throws StackOverflowError {
        // do nothing
    }

    public void setALong(final long aLong) throws Throwable {
        // do nothing
    }

    public List<String> getList() throws Exception {
        throw new Exception();
    }

    void addAnItem(final String item) throws RuntimeException {
        // do nothing
    }
}
