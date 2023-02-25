package org.codecop.monolith.playground.jms;

import static io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME;

import io.micronaut.jms.annotations.JMSListener;
import io.micronaut.jms.annotations.Queue;
import io.micronaut.messaging.annotation.MessageBody;
import jakarta.inject.Inject;

@JMSListener(CONNECTION_FACTORY_BEAN_NAME)
public class TextConsumer {

    @Inject
    TextStore messages;

    @Queue(value = "${config.jms.destinationQueue}", concurrency = "1-5")
    public void receive(@MessageBody String body) {
        // see https://micronaut-projects.github.io/micronaut-jms/2.1.0/guide/index.html
        messages.messages.add(body);
    }
}
