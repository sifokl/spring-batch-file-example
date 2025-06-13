package com.batch.foobarquix.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FooBarQuixTransformerServiceTest {

    private final FooBarQuixTransformerService underTest = new FooBarQuixTransformerService();

    @ParameterizedTest(name = "transform({0}) should return \"{1}\"")
    @DisplayName("Should transform number into expected FooBarQuix string")
    @CsvSource({
            "3, FOOFOO",
            "5, BARBAR",
            "7, QUIX",
            "1, 1",
            "15, FOOBARBAR",
            "53, BARFOO",
            "33, FOOFOOFOO",
            "9, FOO",
            "0, FOOBAR",
            "100, BAR"
    })
    void testTransformations(int input, String expected) {
        assertEquals(expected, underTest.transform(input));
    }
}
