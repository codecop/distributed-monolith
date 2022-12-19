package org.codecop.monolith.playground;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class ClockedPosition {

    private int clock;
    private Position position;

    public ClockedPosition(int clock, Position position) {
        this.clock = clock;
        this.position = position;
    }

    // for JSON mapper

    public ClockedPosition() {
    }

    public int getClock() {
        return clock;
    }

    public void setClock(int clock) {
        this.clock = clock;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position value) {
        this.position = value;
    }

}
