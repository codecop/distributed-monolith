package org.codecop.monolith.playground;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class Junit5ParamTest {

    @ParameterizedTest
    @ValueSource(ints = { 1, 3, 5, -3, 15, Integer.MAX_VALUE })
    void isOdd_ShouldReturnTrueForOddNumbers(int number) {
        assertTrue(isOdd(number));
    }

    static boolean isOdd(int number) {
        return number % 2 != 0;
    }
}
