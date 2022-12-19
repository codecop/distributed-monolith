package org.codecop.monolith.playground.gol;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class LifeTest {

    @Test
    void updateWithTwoNeighboursLivesOn() {
        Life life = givenAlive();

        life.withNeighbours(2);

        assertTrue(life.isAlive());
    }

    private Life givenAlive() {
        Life life = new Life();
        life.seed();
        return life;
    }

    @Test
    void updateWithThreeNeighboursIsBord() {
        Life life = givenDead();

        life.withNeighbours(3);

        assertTrue(life.isAlive());
    }

    private Life givenDead() {
        return new Life();
    }

}
