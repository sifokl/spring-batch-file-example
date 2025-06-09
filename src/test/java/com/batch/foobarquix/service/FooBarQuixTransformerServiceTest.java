package com.batch.foobarquix.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FooBarQuixTransformerServiceTest {

    private final FooBarQuixTransformerService underTest = new FooBarQuixTransformerService();

    @Test
    @DisplayName("Should return FOOFOO when number is 3 (div and contains)")
    void testNumber3() {
        assertEquals("FOOFOO", underTest.transform(3));
    }

    @Test
    @DisplayName("Should return BARBAR when number is 5")
    void testNumber5() {
        assertEquals("BARBAR", underTest.transform(5));
    }

    @Test
    @DisplayName("Should return QUIX when number is 7")
    void testNumber7() {
        assertEquals("QUIX", underTest.transform(7));
    }

    @Test
    @DisplayName("Should return same number as string (default behavior))")
    void testNumberWithNoRule() {
        assertEquals("1", underTest.transform(1));
    }

    @Test
    @DisplayName("Should apply multiple transformations: div and contains")
    void testComplexCase() {
        assertEquals("FOOBARBAR", underTest.transform(15));
        assertEquals("BARFOO", underTest.transform(53));
        assertEquals("FOOFOOFOO", underTest.transform(33));
    }

    @Test
    @DisplayName("Should return FOO for 9 (div 3)")
    void testDivisibleOnly() {
        assertEquals("FOO", underTest.transform(9));
    }

    @Test
    @DisplayName("Should return number when input is 0 (div 5 and div 3)")
    void testZero() {
        assertEquals("FOOBAR", underTest.transform(0)); // 0 % 3 == 0 and 0 % 5 == 0
    }

    @Test
    @DisplayName("Should handle max edge value 100")
    void testUpperBound() {
        assertEquals("BAR", underTest.transform(100)); // divisible by 5
    }
}
