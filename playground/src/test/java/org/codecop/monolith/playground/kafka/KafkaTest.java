package org.codecop.monolith.playground.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.codecop.monolith.playground.jms.TextStore;
import org.junit.jupiter.api.Test;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest
class KafkaTest {

    @Inject
    AnalyticsClient sender;

    @Inject
    TextStore store;
    
    @Test
    void sendAndReceiveMessage() throws InterruptedException {
        assertEquals(0, store.messages.size());

        BookAnalytics book = new BookAnalytics("1234", 1);
        sender.updateAnalytics(book);

        waitForKafka();
        assertEquals(1, store.messages.size());
    }

    private void waitForKafka() throws InterruptedException {
        Thread.sleep(5_000);
    }
}
