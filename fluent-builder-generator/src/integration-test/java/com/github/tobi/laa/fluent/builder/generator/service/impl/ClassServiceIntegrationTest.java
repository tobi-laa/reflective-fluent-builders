package com.github.tobi.laa.fluent.builder.generator.service.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassServiceIntegrationTest {

    private final ClassServiceImpl classServiceImpl = new ClassServiceImpl();

    @Test
    void testThisIsTest() {
        long res = classServiceImpl.thisIsTest(4, 5);
        assertEquals(97, res);
    }
}
