package org.codecop.monolith.playground;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        neighbours.report(new Position(position.getX(), position.getY() - 1));
        neighbours.report(new Position(position.getX(), position.getY() + 1));
        waitForJms();

        tick();

        assertAlive(true);
    }

    @Test
    void cellWithTwoNeighboursOutsideDies() throws InterruptedException {
        status.setAlive(true);
        neighbours.report(new Position(3, 1));
        neighbours.report(new Position(3, 3));
        waitForJms();

        tick();

        assertAlive(false);
    }

    @Inject
    SeedProducer seeder;

    @Test
    void seedThisCell() throws InterruptedException {
        status.setAlive(false);

        seeder.seed(position);
        waitForJms();

        assertAlive(true);
    }

    @Test
    void seedDifferentCell() throws InterruptedException {
        status.setAlive(false);

        seeder.seed(new Position(position.getX() + 1, position.getY() + 1));
        waitForJms();

        assertAlive(false);
    }

}
