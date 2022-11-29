package org.codecop.monolith.playground;

import static io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME;

import io.micronaut.jms.annotations.JMSProducer;
import io.micronaut.jms.annotations.Queue;
import io.micronaut.messaging.annotation.MessageBody;

//x@JMSProducer(CONNECTION_FACTORY_BEAN_NAME)
public interface StringMessageProducer {

    //x@Queue("${config.jms.destinationQueue}")
    void send(@MessageBody String body);

    // see https://github.com/tylervz/amq-jms-example
    // see https://micronaut-projects.github.io/micronaut-jms/2.1.0/guide/index.html
}
