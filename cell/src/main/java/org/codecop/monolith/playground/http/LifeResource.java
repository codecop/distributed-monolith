package org.codecop.monolith.playground.http;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class LifeResource {

    private boolean alive;

    public LifeResource() {
        this(false);
    }

    public LifeResource(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

}
