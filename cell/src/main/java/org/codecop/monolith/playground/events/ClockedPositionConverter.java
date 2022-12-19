package org.codecop.monolith.playground.events;

import org.codecop.monolith.playground.gol.Position;

import jakarta.inject.Singleton;

@Singleton
public class ClockedPositionConverter {

    public Position fromDto(ClockedPosition message) {
        return new Position(message.getX(), message.getY());
    }

    public ClockedPosition toDto(int clock, Position position) {
        return new ClockedPosition(clock, position.getX(), position.getY());
    }

}
