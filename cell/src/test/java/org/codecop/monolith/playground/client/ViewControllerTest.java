package org.codecop.monolith.playground.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.codecop.monolith.playground.events.ClockedPosition;
import org.codecop.monolith.playground.events.ReportAliveProducer;
import org.codecop.monolith.playground.events.Time;
import org.junit.jupiter.api.Test;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest
class ViewControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    Time time;
    
    @Inject
    ReportAliveProducer neighbours;

    @Test
    void emptyGrid() {
        String response = getGrid();
        assertEquals(//
                ".....\n" + //
                "     \n" + //
                ".....\n" + //
                "     \n" + //
                ".....\n",
                response);
    }

    private String getGrid() {
        return client.toBlocking().retrieve(HttpRequest.GET("/grid"));
    }

    @Test
    void seeSomeAlive() throws InterruptedException {
        neighbours.report(new ClockedPosition(time.getCurrent(), 0, 1));
        neighbours.report(new ClockedPosition(time.getCurrent(), 1, 1));
        neighbours.report(new ClockedPosition(time.getCurrent(), 2, 1));
        waitForJms();
        
        String response = getGrid();
        assertEquals(//
                ".....\n" + //
                "###  \n" + //
                ".....\n" + //
                "     \n" + //
                ".....\n",
                response);
    }

    private void waitForJms() throws InterruptedException {
        Thread.sleep(500);
    }

}
