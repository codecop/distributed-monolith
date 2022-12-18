package org.codecop.monolith.playground;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PositionTest {

    @Test
    void isNext() {
        Position position = new Position(2, 3);
        assertTrue(position.isNext(new Position(2, 4)));
    }

    @Test
    void isNextIsNotTheSame() {
        Position position = new Position(2, 3);
        assertFalse(position.isNext(new Position(2, 3)));
    }

}
