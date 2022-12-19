package org.codecop.monolith.playground;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class LifeDto {

    private boolean alive;

    public LifeDto() {
        this(false);
    }

    public LifeDto(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

}
