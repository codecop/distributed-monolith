package org.codecop.monolith.playground.kafka;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient
public interface AnalyticsClient {

    @Topic("playground-analytics")
    void updateAnalytics(BookAnalytics book);

    // @Topic("playground-analytics")
    // void sendMessage(@KafkaKey String time, String message);

    // see https://guides.micronaut.io/latest/micronaut-kafka-gradle-java.html
    // see https://medium.com/techwasti/micronaut-kafka-consumer-producer-example-c53615a93e32
}
