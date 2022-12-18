package org.codecop.monolith.playground;

import java.util.Objects;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.serde.annotation.Serdeable;

@ConfigurationProperties("position")
@Serdeable
public class Position {

    private int x;
    private int y;

    // for JSON mapper
    public Position() {
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isNext(Position other) {
        return Math.abs(x - other.x) <= 1 && Math.abs(y - other.y) <= 1;
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

    // only for test
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

}
