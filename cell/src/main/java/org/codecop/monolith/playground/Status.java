package org.codecop.monolith.playground;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Singleton;

@Singleton
@Serdeable
public class Status {

    public static final Status ALIVE = new Status(true);
    public static final Status DEAD = new Status(false);

    private boolean alive;

    public Status() {
    }

    private Status(boolean alive) {
        this.alive = alive;
    }

    public void withNeighbours(int countNeighbours) {
        this.alive = this.alive && countNeighbours == 2;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

}
