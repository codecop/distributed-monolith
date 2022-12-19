package org.codecop.monolith.playground.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.codecop.monolith.playground.gol.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 * Controlling the clock and time machine.
 */
@Singleton
public class Time {

    private final Logger logger = LoggerFactory.getLogger(Time.class);
    private final Map<Integer, List<Position>> futureNeighbours = new HashMap<>();
    private int current = -1;

    @Inject
    private ClockedPositionConverter converter;

    public int getCurrent() {
        return current;
    }

    public void storeLivingNeighbourForFuture(ClockedPosition message) {
        logger.info("Received {} with newer clock {}, current {}, storing: {}", //
                "LivingNeighbour", message.getClock(), current, message);

        int futureClock = message.getClock();
        Position position = converter.fromDto(message);
        storeForFuture(futureClock, position);
    }

    private void storeForFuture(int futureClock, Position position) {
        if (!futureNeighbours.containsKey(futureClock)) {
            futureNeighbours.put(futureClock, new ArrayList<Position>());
        }
        futureNeighbours.get(futureClock).add(position);
    }

    public void eachFutureLivingNeighbour(Consumer<Position> consumer) {
        if (futureNeighbours.containsKey(current)) {
            futureNeighbours.get(current).stream().forEach(consumer);
            futureNeighbours.remove(current);
        }
    }

    public void nextTime() {
        current++;
    }

    public boolean isStale(ClockedPosition message) {
        return message.getClock() < current;
    }

    public boolean isNow(ClockedPosition message) {
        return message.getClock() == current;
    }

    public void reportStale(String kind, int clock) {
        logger.warn("Received {} with older clock {}, current {}, ignoring", //
                kind, clock, current);
    }

    public void reportStale(String kind, ClockedPosition message) {
        logger.warn("Received {} with older clock {}, current {}, discarding: {}", //
                kind, message.getClock(), current, message);
    }

    public boolean isNewer(int clock) {
        return clock > current;
    }

}
