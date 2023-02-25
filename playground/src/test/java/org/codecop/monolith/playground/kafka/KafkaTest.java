package org.codecop.monolith.playground.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.codecop.monolith.playground.jms.TextStore;
import org.junit.jupiter.api.Test;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import reactor.core.publisher.Flux;

@MicronautTest()
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
        Flux.from(sender.updateAnalytics(book)). //
                map(b -> b.getBookIsbn());

        waitForKafka();
        assertEquals(2, store.messages.size());
        System.out.println(store.messages);
        // see https://guides.micronaut.io/latest/micronaut-kafka-gradle-java.html
    }

    private void waitForKafka() throws InterruptedException {
        Thread.sleep(1_000);
    }
}
