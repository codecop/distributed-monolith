package org.codecop.monolith.playground.http;

import java.util.Objects;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class PositionResource {

    private int x;
    private int y;

    public PositionResource() {
    }

    public PositionResource(int x, int y) {
        this.x = x;
        this.y = y;
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
        if (!(obj instanceof PositionResource)) {
            return false;
        }
        PositionResource other = (PositionResource) obj;
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
