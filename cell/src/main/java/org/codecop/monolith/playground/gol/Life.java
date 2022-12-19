package org.codecop.monolith.playground.gol;

import java.util.Objects;

import jakarta.inject.Singleton;

@Singleton
public class Life {

    public static final Life ALIVE = new Life(true);
    public static final Life DEAD = new Life(false);

    private boolean alive;

    // initial construction by IoC
    public Life() {
        this(false);
    }

    private Life(boolean alive) {
        this.alive = alive;
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Life)) {
            return false;
        }
        Life other = (Life) obj;
        return alive == other.alive;
    }

    @Override
    public int hashCode() {
        return Objects.hash(alive);
    }

}
