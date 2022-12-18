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
    Status status;

    @Test
    void cellHasPosition() {
        String response = client.toBlocking().retrieve(HttpRequest.GET("/position.json"));
        assertEquals("{\"x\":1,\"y\":2}", response);
    }

    @Test
    void newCellIsDead() {
        assertAlive(false);
    }

    private void assertAlive(boolean state) {
        String response = client.toBlocking().retrieve(HttpRequest.GET("/alive.json"));
        assertEquals("{\"alive\":" + state + "}", response);
    }

    @Inject
    TickProducer producer;

    @Test
    void cellDiesWithoutNeighbours() throws InterruptedException {
        status.setAlive(true);
        // assertAlive(true);

        producer.tick("");
        Thread.sleep(1000);
        
        assertAlive(false);
    }

    // seed message
    // cell is alive
}
