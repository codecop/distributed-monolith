package org.codecop.monolith.playground;

import static io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME;

import io.micronaut.jms.annotations.JMSListener;
import io.micronaut.jms.annotations.Topic;
import io.micronaut.messaging.annotation.MessageBody;
import jakarta.inject.Inject;

/**
 * Wrapper of the model as JMS receiver.
 */
@JMSListener(CONNECTION_FACTORY_BEAN_NAME)
public class JmsListener {

    @Inject
    private Model model;
    @Inject
    private ReportAliveProducer reportAlive;

    @Topic(value = "${config.jms.seedQueue}")
    public void onSeed(@MessageBody Position at) {
        model.seed(at);
    }

    @Topic(value = "${config.jms.aliveQueue}")
    public void onLivingNeighbour(@MessageBody Position at) {
        model.recordLivingNeighbour(at);
    }

    @Topic(value = "${config.jms.tickQueue}")
    public void onTick(@MessageBody int clock) {
        model.tick(clock);
        if (model.isAlive()) {
            reportAlive.report(model.getPosition());
        }
    }
}
