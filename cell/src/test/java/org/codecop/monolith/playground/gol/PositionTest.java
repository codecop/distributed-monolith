package org.codecop.monolith.playground.gol;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.ConfiguredEqualsVerifier;
import nl.jqno.equalsverifier.Warning;

class PositionTest {

    @Test
    void isNext() {
        Position position = new Position(2, 3);
        assertTrue(position.isNext(new Position(2 - 1, 3 - 1)));
        assertTrue(position.isNext(new Position(2, 3 - 1)));
        assertTrue(position.isNext(new Position(2 + 1, 3 - 1)));
        assertTrue(position.isNext(new Position(2 - 1, 3)));
        assertTrue(position.isNext(new Position(2 + 1, 3)));
        assertTrue(position.isNext(new Position(2 - 1, 3 + 1)));
        assertTrue(position.isNext(new Position(2, 3 + 1)));
        assertTrue(position.isNext(new Position(2 + 1, 3 + 1)));
    }

    @Test
    void isNextIsNotTheSame() {
        Position position = new Position(2, 3);
        assertFalse(position.isNext(new Position(2, 3)));
    }

    @Test
    void isNotNext() {
        Position position = new Position(2, 3);
        assertFalse(position.isNext(new Position(2 - 2, 3 - 1)));
        assertFalse(position.isNext(new Position(2, 3 - 2)));
        assertFalse(position.isNext(new Position(2 + 2, 3 - 1)));
        assertFalse(position.isNext(new Position(2 - 2, 3)));
        assertFalse(position.isNext(new Position(2 + 2, 3)));
        assertFalse(position.isNext(new Position(2 - 1, 3 + 2)));
        assertFalse(position.isNext(new Position(2, 3 + 2)));
        assertFalse(position.isNext(new Position(2 + 2, 3 + 1)));
    }

    @Test
    void equalsContract() {
        new ConfiguredEqualsVerifier(). //
                suppress(Warning.NONFINAL_FIELDS). //
                forClass(Position.class). //
                verify();
    }
}
