package org.codecop.monolith.playground.kafka;

import org.codecop.monolith.playground.jms.TextStore;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Inject;

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
public class AnalyticsListener {

    @Inject
    TextStore store;

    @Topic("analytics")
    void updateAnalytics(BookAnalytics book) {
        store.messages.add(book.getBookIsbn());
        System.out.println("RECEIVED " + book);
    }
}
