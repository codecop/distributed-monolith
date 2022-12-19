package org.codecop.monolith.playground.gol;

import java.util.Objects;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("position")
public class Position {

    private int x;
    private int y;

    // for JSON mapper
    public Position() {
        this(0, 0);
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isNext(Position other) {
        return Math.max(Math.abs(x - other.x), Math.abs(y - other.y)) == 1;
    }

    // for JSON mapper
    public int getX() {
        return x;
    }

    // for JSON mapper
    public void setX(int x) {
        this.x = x;
    }

    // for JSON mapper
    public int getY() {
        return y;
    }

    // for JSON mapper
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Position)) {
            return false;
        }
        Position other = (Position) obj;
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
