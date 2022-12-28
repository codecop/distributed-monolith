package org.codecop.monolith.playground.gol;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class Cell {

    @Inject
    private Life life;
    @Inject
    private Position position;

    private int countNeighbours;

    public boolean seed(Position at) {
        if (position.equals(at)) {
            life.seed();
            return true;
        }
        return false;
    }

    public void recordLivingNeighbour(Position at) {
        if (position.isNext(at)) {
            countNeighbours++;
        }
    }

    public void tick() {
        life.withNeighbours(countNeighbours);
        countNeighbours = 0;
    }

    public boolean isAlive() {
        return life.isAlive();
    }

    public Life getLife() {
        return life;
    }

    public Position getPosition() {
        return position;
    }

    /* for debugging */
    public int getCountNeighbours() {
        return countNeighbours;
    }
}
