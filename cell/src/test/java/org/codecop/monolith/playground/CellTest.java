package org.codecop.monolith.playground;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.codecop.monolith.playground.events.AliveQueueSpy;
import org.codecop.monolith.playground.events.ClockedPosition;
import org.codecop.monolith.playground.events.ReportAliveProducer;
import org.codecop.monolith.playground.events.SeedProducer;
import org.codecop.monolith.playground.events.TickProducer;
import org.codecop.monolith.playground.gol.Life;
import org.codecop.monolith.playground.gol.Position;
import org.junit.jupiter.api.Test;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest
class CellTest {
    /*
     * Cannot split this test in JMS and HTTP. The JMS init does not work on second test.
     */

    @Inject
    Life life;

    @Inject
    Position position;

    static int clock;

    // --- HTTP ---

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void cellHasPosition() {
        String response = client.toBlocking().retrieve(HttpRequest.GET("/position.json"));
        assertEquals("{\"x\":" + position.getX() + ",\"y\":" + position.getY() + "}", response);
    }

    @Test
    void newCellIsDead() {
        life.kill();
        String response = client.toBlocking().retrieve(HttpRequest.GET("/alive.json"));
        assertEquals("{\"alive\":" + false + "}", response);
    }

    // --- JMS ---

    @Inject
    TickProducer ticker;

    @Test
    void cellDiesWithoutNeighbours() throws InterruptedException {
        life.seed();

        tick();

        assertFalse(life.isAlive());
    }

    private void tick() throws InterruptedException {
        ticker.tick(++clock);
        waitForJms();
    }

    private void waitForJms() throws InterruptedException {
        Thread.sleep(500);
    }

    @Inject
    ReportAliveProducer neighbours;

    @Test
    void cellWithTwoNeighboursLivesOn() throws InterruptedException {
        life.seed();
        neighbours.report(positionRelative(0, -1));
        neighbours.report(positionRelative(0, 1));
        waitForJms();

        tick();

        assertTrue(life.isAlive());
    }

    private ClockedPosition positionRelative(int dx, int dy) {
        return new ClockedPosition(clock, position.getX() + dx, position.getY() + dy);
    }

    @Test
    void cellWithTwoNeighboursOutsideDies() throws InterruptedException {
        life.seed();
        neighbours.report(positionRelative(2, -1));
        neighbours.report(positionRelative(2, +1));
        waitForJms();

        tick();

        assertFalse(life.isAlive());
    }

    @Inject
    SeedProducer seeder;

    @Test
    void seedThisCellJms() throws InterruptedException {
        life.kill();

        seeder.seed(positionRelative(0, 0));
        waitForJms();

        assertTrue(life.isAlive());
        // and report seed
        assertEquals(position.getX(), aliveQueueSpy.recorded.getX());
        assertEquals(position.getY(), aliveQueueSpy.recorded.getY());
    }

    @Test
    void seedDifferentCell() throws InterruptedException {
        life.kill();
        aliveQueueSpy.recorded = null;

        seeder.seed(positionRelative(1, 1));
        waitForJms();

        assertFalse(life.isAlive());
        // and not report seed
        assertNull(aliveQueueSpy.recorded);
    }

    @Inject
    AliveQueueSpy aliveQueueSpy;

    @Test
    void cellSendsLivingStateAfterTick() throws InterruptedException {
        life.kill();
        neighbours.report(positionRelative(-1, -1));
        neighbours.report(positionRelative(-1, 0));
        neighbours.report(positionRelative(-1, 1));
        waitForJms();

        tick();

        assertTrue(life.isAlive()); // not goal of test
        assertEquals(position.getX(), aliveQueueSpy.recorded.getX());
        assertEquals(position.getY(), aliveQueueSpy.recorded.getY());
    }

    @Test
    void deadCellSendsNothingAfterTick() throws InterruptedException {
        life.kill();
        aliveQueueSpy.recorded = null;

        tick();

        assertNull(aliveQueueSpy.recorded);
    }

}
