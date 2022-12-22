package org.codecop.monolith.playground.gol;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class LifeTest {

    @ParameterizedTest
    @ValueSource(ints = { 2, 3 })
    void updateWithTwoNeighboursLivesOn(int countNeighbours) {
        Life life = givenAlive();

        life.withNeighbours(countNeighbours);

        assertTrue(life.isAlive());
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 4, 5, 6, 7, 8, })
    void updateWithTooManyNeighboursDies(int countNeighbours) {
        Life life = givenAlive();

        life.withNeighbours(countNeighbours);

        assertFalse(life.isAlive());
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

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 4, 5, 6, 7, 8, })
    void updateWithTooManyNeighboursStaysDead(int countNeighbours) {
        Life life = givenDead();

        life.withNeighbours(countNeighbours);

        assertFalse(life.isAlive());
    }

    private Life givenDead() {
        return new Life();
    }

    @Test
    void seed() {
        Life life = givenDead();
        assertFalse(life.isAlive());

        life.seed();

        assertTrue(life.isAlive());
    }

    @Test
    void kill() {
        Life life = givenAlive();
        assertTrue(life.isAlive());

        life.kill();

        assertFalse(life.isAlive());
    }

}
