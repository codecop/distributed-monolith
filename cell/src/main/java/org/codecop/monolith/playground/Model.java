package org.codecop.monolith.playground;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class Model {

    @Inject
    private Life life;
    @Inject
    private Position position;

    private int countNeighbours;
    private int lastClock = -1;

    public void seed(Position at) {
        if (position.equals(at)) {
            life.seed();
        }
    }

    public void recordLivingNeighbour(Position at) {
        if (position.isNext(at)) {
            countNeighbours++;
        }
    }

    public void tick(int clock) {
        if (clock > lastClock) {
            lastClock = clock;
            life.withNeighbours(countNeighbours);
            countNeighbours = 0;
        }
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

}
