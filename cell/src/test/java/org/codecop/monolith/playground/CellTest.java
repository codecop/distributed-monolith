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

    @Test
    void cellHasPosition() {
        String response = client.toBlocking().retrieve(HttpRequest.GET("/position.json"));
        assertEquals("{\"x\":1,\"y\":2}", response);
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

        ticker.tick("");
        Thread.sleep(1000);

        assertAlive(false);
    }

    @Inject
    ReportAliveProducer neighbours;

    @Test
    void cellWithTwoNeighboursLivesOn() throws InterruptedException {
        status.setAlive(true);
        neighbours.report(new Position(1, 1));
        neighbours.report(new Position(1, 3));
        Thread.sleep(1000);

        ticker.tick("");
        Thread.sleep(1000);

        assertAlive(true);
    }

    // seed message
    // cell is alive
}
