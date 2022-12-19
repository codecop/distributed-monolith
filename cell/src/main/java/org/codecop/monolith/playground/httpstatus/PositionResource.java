package org.codecop.monolith.playground.httpstatus;

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

}
