package org.codecop.monolith.playground;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StatusTest {

    @Test
    void updateWithTwoNeighboursLivesOn() {
        Status status = Status.ALIVE;

        status.withNeighbours(2);

        assertEquals(Status.ALIVE, status);
    }

}
