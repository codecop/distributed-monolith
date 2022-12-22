package org.codecop.monolith.playground.events;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.codecop.monolith.playground.gol.Position;
import org.junit.jupiter.api.Test;

class ClockedPositionConverterTest {

    ClockedPositionConverter c = new ClockedPositionConverter();

    @Test
    void testFromDto() {
        Position p = c.fromDto(new ClockedPosition(1, 2, 3));
        assertEquals(2, p.getX());
        assertEquals(3, p.getY());
    }

    @Test
    void testToDto() {
        ClockedPosition message = c.toDto(3, new Position(4, 5));
        assertEquals(3, message.getClock());
        assertEquals(4, message.getX());
        assertEquals(5, message.getY());
    }

}
