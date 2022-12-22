package org.codecop.monolith.playground.events;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.codecop.monolith.playground.events.ClockedPosition;
import org.codecop.monolith.playground.events.ReportAliveProducer;
import org.codecop.monolith.playground.events.TickProducer;
import org.codecop.monolith.playground.gol.Life;
import org.codecop.monolith.playground.gol.Position;
import org.junit.jupiter.api.Test;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest
class TimeTest {

    @Inject
    Life life;

    @Inject
    Position position;

    static int clock = -1;

    @Inject
    TickProducer ticker;

    @Inject
    ReportAliveProducer neighbours;

    @Test
    void storeFutureNeighboursForNextTick() throws InterruptedException {
        life.kill();
        neighbours.report(positionRelative(-1, -1));
        neighbours.report(positionRelative(-1, 0));
        neighbours.report(futurePositionRelative(1, -1, 1));
        neighbours.report(futurePositionRelative(1, -1, 0));
        neighbours.report(positionRelative(-1, 1));
        waitForJms();
        tick();
        assertTrue(life.isAlive()); // not goal of test

        tick();

        // still alive because two neighbours
        assertTrue(life.isAlive());

        tick();
        // now dead
        assertFalse(life.isAlive());
    }

    private ClockedPosition positionRelative(int dx, int dy) {
        return new ClockedPosition(clock, position.getX() + dx, position.getY() + dy);
    }

    private ClockedPosition futurePositionRelative(int dt, int dx, int dy) {
        return new ClockedPosition(clock + dt, position.getX() + dx, position.getY() + dy);
    }

    private void tick() throws InterruptedException {
        ticker.tick(++clock);
        waitForJms();
    }

    private void waitForJms() throws InterruptedException {
        Thread.sleep(500);
    }

}
