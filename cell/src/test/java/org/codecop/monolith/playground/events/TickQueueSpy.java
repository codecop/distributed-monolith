package org.codecop.monolith.playground.events;

import static io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME;

import io.micronaut.jms.annotations.JMSListener;
import io.micronaut.jms.annotations.Topic;
import io.micronaut.messaging.annotation.MessageBody;

@JMSListener(CONNECTION_FACTORY_BEAN_NAME)
public class TickQueueSpy {

    public int recorded;
    // TODO use clocked with position of origin

    @Topic(value = "${config.jms.tickQueue}")
    public void onTick(@MessageBody int clock) {
        this.recorded = clock;
    }
}
