package org.codecop.monolith.playground.jms;

import static io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME;

import io.micronaut.jms.annotations.JMSProducer;
import io.micronaut.jms.annotations.Topic;
import io.micronaut.messaging.annotation.MessageBody;

@JMSProducer(CONNECTION_FACTORY_BEAN_NAME)
public interface SeedProducer {

    @Topic("${config.jms.seedQueue}")
    void seed(@MessageBody ClockedPosition message);

}
