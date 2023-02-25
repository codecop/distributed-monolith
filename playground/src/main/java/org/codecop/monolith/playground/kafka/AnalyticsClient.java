package org.codecop.monolith.playground.kafka;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;
import reactor.core.publisher.Mono;

@KafkaClient
public interface AnalyticsClient {

    @Topic("analytics")
    Mono<BookAnalytics> updateAnalytics(BookAnalytics book);
}
