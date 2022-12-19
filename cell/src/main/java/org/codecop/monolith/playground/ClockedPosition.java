package org.codecop.monolith.playground;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class ClockedPosition {

    private int clock;
    private Position value;

    public ClockedPosition() {
    }

    public ClockedPosition(int clock, Position value) {
        this.clock = clock;
        this.value = value;
    }

    public int getClock() {
        return clock;
    }

    public void setClock(int clock) {
        this.clock = clock;
    }

    public Position getValue() {
        return value;
    }

    public void setValue(Position value) {
        this.value = value;
    }

}
