package org.codecop.monolith.playground;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.codecop.monolith.playground.gol.Life;
import org.codecop.monolith.playground.gol.Position;
import org.codecop.monolith.playground.jms.AliveQueueSpy;
import org.codecop.monolith.playground.jms.ClockedPosition;
import org.codecop.monolith.playground.jms.ReportAliveProducer;
import org.codecop.monolith.playground.jms.SeedProducer;
import org.codecop.monolith.playground.jms.TickProducer;
import org.junit.jupiter.api.Test;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest
class CellTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    Life life;

    @Inject
    Position position;

    static int clock;

    @Test
    void cellHasPosition() {
        String response = client.toBlocking().retrieve(HttpRequest.GET("/position.json"));
        assertEquals("{\"x\":" + position.getX() + ",\"y\":" + position.getY() + "}", response);
    }

    @Test
    void newCellIsDead() {
        life.kill(); // not first test
        assertAlive(false);
    }

    private void assertAlive(boolean state) {
        String response = client.toBlocking().retrieve(HttpRequest.GET("/alive.json"));
        assertEquals("{\"alive\":" + state + "}", response);
    }

    @Inject
    TickProducer ticker;

    @Test
    void cellDiesWithoutNeighbours() throws InterruptedException {
        life.seed();

        tick();

        assertAlive(false);
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

        assertAlive(true);
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

        assertAlive(false);
    }

    @Inject
    SeedProducer seeder;

    @Test
    void seedThisCell() throws InterruptedException {
        life.kill();

        seeder.seed(positionRelative(0, 0));
        waitForJms();

        assertAlive(true);
    }

    @Test
    void seedDifferentCell() throws InterruptedException {
        life.kill();

        seeder.seed(positionRelative(1, 1));
        waitForJms();

        assertAlive(false);
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

        assertAlive(true); // not goal of test
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
