package org.codecop;

import static io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME;

import io.micronaut.jms.annotations.JMSProducer;
import io.micronaut.jms.annotations.Queue;
import io.micronaut.messaging.annotation.MessageBody;

@JMSProducer(CONNECTION_FACTORY_BEAN_NAME)
public interface StringMessageProducer {

    @Queue("${config.jms.destinationQueue}")
    void send(@MessageBody String body);

    // see https://github.com/tylervz/amq-jms-example
}
