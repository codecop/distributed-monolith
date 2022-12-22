package org.codecop.monolith.playground.events;

import static io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME;

import io.micronaut.jms.annotations.JMSListener;
import io.micronaut.jms.annotations.Topic;
import io.micronaut.messaging.annotation.MessageBody;

@JMSListener(CONNECTION_FACTORY_BEAN_NAME)
public class SeedQueueSpy {

    public ClockedPosition recorded;

    @Topic(value = "${config.jms.seedQueue}")
    public void onSeed(@MessageBody ClockedPosition message) {
        this.recorded = message;
    }
}
