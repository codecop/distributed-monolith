package org.codecop.monolith.playground;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Singleton;

@Singleton
@Serdeable
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

    public void withNeighbours(int countNeighbours) {
        // TODO incomplete
        this.alive = this.alive && countNeighbours == 2;
    }

    // for JSON mapper
    public boolean isAlive() {
        return alive;
    }

    /* only for tests */ void setAlive(boolean alive) {
        this.alive = alive;
    }

}
