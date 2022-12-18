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

}
