package org.codecop.monolith.playground.jms;

import java.beans.Transient;
import java.util.Objects;

import org.codecop.monolith.playground.gol.Position;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class ClockedPosition {

    private int clock;
    private int x;
    private int y;

    public ClockedPosition(int clock, int x, int y) {
        this.clock = clock;
        this.x = x;
        this.y = y;
    }

    @Transient
    public Position getPosition() {
        return new Position(x, y);
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClockedPosition)) {
            return false;
        }
        ClockedPosition other = (ClockedPosition) obj;
        return x == other.x && y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }
}
