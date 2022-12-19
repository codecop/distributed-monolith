package org.codecop.monolith.playground;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LifeTest {

    @Test
    void updateWithTwoNeighboursLivesOn() {
        Life life = Life.ALIVE;

        life.withNeighbours(2);

        assertEquals(Life.ALIVE, life);
    }

    @Test
    void updateWithThreeNeighboursIsBord() {
        Life life = Life.DEAD;

        life.withNeighbours(3);

        assertEquals(Life.ALIVE, life);
    }

}
