package org.codecop.monolith.playground.gol;

import jakarta.inject.Singleton;

@Singleton
public class Life {

    private boolean alive;

    // initial construction by IoC
    public Life() {
    }

    public void seed() {
        this.alive = true;
    }

    public void kill() {
        this.alive = false;
    }

    public void withNeighbours(int countNeighbours) {
        this.alive = (this.alive && countNeighbours == 2) || countNeighbours == 3;
    }

    public boolean isAlive() {
        return alive;
    }

}
