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
    Life status;

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
        status.setAlive(false); // not first test
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
        status.setAlive(true);
        // assertAlive(true);

        tick();

        assertAlive(false);
    }

    private void tick() throws InterruptedException {
        ticker.tick(clock++);
        waitForJms();
    }

    private void waitForJms() throws InterruptedException {
        Thread.sleep(500);
    }

    @Inject
    ReportAliveProducer neighbours;

    @Test
    void cellWithTwoNeighboursLivesOn() throws InterruptedException {
        status.setAlive(true);
        neighbours.report(new ClockedPosition(clock, position.getX(), position.getY() - 1));
        neighbours.report(new ClockedPosition(clock, position.getX(), position.getY() + 1));
        waitForJms();

        tick();

        assertAlive(true);
    }

    @Test
    void cellWithTwoNeighboursOutsideDies() throws InterruptedException {
        status.setAlive(true);
        neighbours.report(new ClockedPosition(clock, 3, 1));
        neighbours.report(new ClockedPosition(clock, 3, 3));
        waitForJms();

        tick();

        assertAlive(false);
    }

    @Inject
    SeedProducer seeder;

    @Test
    void seedThisCell() throws InterruptedException {
        status.setAlive(false);

        seeder.seed(new ClockedPosition(clock, position.getX(), position.getY()));
        waitForJms();

        assertAlive(true);
    }

    @Test
    void seedDifferentCell() throws InterruptedException {
        status.setAlive(false);

        seeder.seed(new ClockedPosition(clock, position.getX() + 1, position.getY() + 1));
        waitForJms();

        assertAlive(false);
    }

    @Inject
    AliveQueueSpy aliveQueueSpy;

    @Test
    void cellSendsLivingStateAfterTick() throws InterruptedException {
        status.setAlive(false);
        neighbours.report(new ClockedPosition(clock, position.getX() - 1, position.getY() - 1));
        neighbours.report(new ClockedPosition(clock, position.getX() - 1, position.getY()));
        neighbours.report(new ClockedPosition(clock, position.getX() - 1, position.getY() + 1));
        waitForJms();

        tick();

        assertAlive(true); // not goal of test
        assertEquals(position, aliveQueueSpy.recorded.getPosition());
    }

    @Test
    void deadCellSendsNothingAfterTick() throws InterruptedException {
        status.setAlive(false);
        aliveQueueSpy.recorded = null;

        tick();

        assertNull(aliveQueueSpy.recorded);
    }

}
