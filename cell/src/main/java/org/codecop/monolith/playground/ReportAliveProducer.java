package org.codecop.monolith.playground;

import static io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME;

import io.micronaut.jms.annotations.JMSProducer;
import io.micronaut.jms.annotations.Queue;
import io.micronaut.messaging.annotation.MessageBody;

@JMSProducer(CONNECTION_FACTORY_BEAN_NAME)
public interface ReportAliveProducer {

    @Queue("${config.jms.aliveQueue}")
    void report(@MessageBody Position at);

}