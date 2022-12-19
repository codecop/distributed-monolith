package org.codecop.monolith.playground;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class ClockedPosition {

    private int clock;
    private PositionDto position;

    public ClockedPosition(int clock, PositionDto position) {
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

    public PositionDto getPosition() {
        return position;
    }

    public void setPosition(PositionDto value) {
        this.position = value;
    }

}
