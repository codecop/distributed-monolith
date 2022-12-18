package org.codecop.monolith.playground;

import static io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME;

import io.micronaut.jms.annotations.JMSListener;
import io.micronaut.jms.annotations.Queue;
import io.micronaut.messaging.annotation.MessageBody;
import jakarta.inject.Inject;

/**
 * Wrapper of the model as JMS receiver.
 */
@JMSListener(CONNECTION_FACTORY_BEAN_NAME)
public class JmsListener {

    @Inject
    private Model model;

    @Queue(value = "${config.jms.tickQueue}", concurrency = "1-5")
    public void onTick(@SuppressWarnings("unused") @MessageBody String unused) {
        model.tick();
    }

    @Queue(value = "${config.jms.aliveQueue}", concurrency = "1-5")
    public void onLivingNeighbour(@MessageBody Position at) {
        model.recordLivingNeighbour(at);
    }
}
