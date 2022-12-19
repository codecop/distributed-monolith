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

@Singleton
public class Time {

    private final Logger logger = LoggerFactory.getLogger(Time.class);
    private final Map<Integer, List<Position>> futureNeighbours = new HashMap<>();
    private int current = -1;

    @Inject
    private ClockedPositionConverter converter;

    public int getCurrentClock() {
        return current;
    }

    public void onLivingNeighbourInFuture(ClockedPosition message) {
        logger.info("Received LivingNeighbour with newer clock {}, current {}, storing: {}", //
                message.getClock(), getCurrentClock(), message);

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

    public void eachFuture(Consumer<Position> consumer) {
        if (futureNeighbours.containsKey(current)) {
            futureNeighbours.get(current).stream().forEach(consumer);
            futureNeighbours.remove(current);
        }
    }

    public void nextTime() {
        current++;
    }

    public boolean isStale(ClockedPosition message) {
        int clock = message.getClock();
        return isStale(clock);
    }

    public boolean isStale(int clock) {
        return getCurrentClock() > clock;
    }

    public boolean isNow(ClockedPosition message) {
        int clock = message.getClock();
        return isNow(clock);
    }

    public boolean isNow(int clock) {
        return getCurrentClock() == clock;
    }

    public void reportStale(String kind, int clock) {
        logger.warn("Received {} with older clock {}, current {}, ignoring", //
                kind, clock, getCurrentClock());
    }

    public void reportStale(String kind, ClockedPosition message) {
        logger.warn("Received {} with older clock {}, current {}, discarding: {}", //
                kind, message.getClock(), getCurrentClock(), message);
    }

    public boolean isNewer(int clock) {
        return getCurrentClock() < clock;
    }

}
