package org.codecop.monolith.playground.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.codecop.monolith.playground.gol.Position;
import org.junit.jupiter.api.Test;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest
class JmsViaHttpTest {

    @Inject
    Position position;

    @Inject
    @Client("/")
    HttpClient client;

    // --- JMS via HTTP ---

    @Inject
    SeedQueueSpy seedSpy;

    @Test
    void seedThisCellHttp() throws InterruptedException {
        seedSpy.recorded = null;
        HttpResponse<?> response = client.toBlocking().exchange(HttpRequest.POST("/seed", ""));
        assertEquals(201, response.code());

        waitForJms();

        assertEquals(position.getX(), seedSpy.recorded.getX());
        assertEquals(position.getY(), seedSpy.recorded.getY());
    }

    @Inject
    TickQueueSpy tickSpy;

    @Test
    void triggerGlobalTick() throws InterruptedException {
        tickSpy.recorded = -1;
        HttpResponse<?> response = client.toBlocking().exchange(HttpRequest.POST("/tick", ""));
        assertEquals(201, response.code());

        waitForJms();

        assertNotEquals(-1, tickSpy.recorded);
    }

    private void waitForJms() throws InterruptedException {
        Thread.sleep(500);
    }

}
