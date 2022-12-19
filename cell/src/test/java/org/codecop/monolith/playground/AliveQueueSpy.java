package org.codecop.monolith.playground;

import static io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME;

import io.micronaut.jms.annotations.JMSListener;
import io.micronaut.jms.annotations.Topic;
import io.micronaut.messaging.annotation.MessageBody;

@JMSListener(CONNECTION_FACTORY_BEAN_NAME)
public class AliveQueueSpy {

    public Position recorded;

    @Topic(value = "${config.jms.aliveQueue}")
    public void onLivingNeighbour(@MessageBody ClockedPosition at) {
        this.recorded = at.getPosition();
    }
}
