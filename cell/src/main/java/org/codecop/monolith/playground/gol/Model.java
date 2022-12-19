package org.codecop.monolith.playground.gol;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class Model {

    @Inject
    private Life life;
    @Inject
    private Position position;

    private int countNeighbours;

    public void seed(Position at) {
        if (position.equals(at)) {
            seed();
        }
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

    public void seed() {
        life.seed();
    }

    public Life getLife() {
        return life;
    }

    public Position getPosition() {
        return position;
    }

}
