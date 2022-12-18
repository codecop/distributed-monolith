package org.codecop.monolith.playground;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StatusTest {

    @Test
    void updateWithTwoNeighboursLivesOn() {
        Life status = Life.ALIVE;

        status.withNeighbours(2);

        assertEquals(Life.ALIVE, status);
    }

    @Test
    void updateWithThreeNeighboursIsBord() {
        Life status = Life.DEAD;
        
        status.withNeighbours(3);
        
        assertEquals(Life.ALIVE, status);
    }

}
