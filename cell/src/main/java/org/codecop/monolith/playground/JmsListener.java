package org.codecop.monolith.playground;

import static io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME;

import org.codecop.monolith.playground.gol.Model;

import io.micronaut.jms.annotations.JMSListener;
import io.micronaut.jms.annotations.Topic;
import io.micronaut.messaging.annotation.MessageBody;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 * Wrapper of the model as JMS receiver.
 */
@Singleton
@JMSListener(CONNECTION_FACTORY_BEAN_NAME)
public class JmsListener {

    @Inject
    private Model model;
    @Inject
    private ReportAliveProducer reportAlive;

    private int currentClock = -1;

    @Topic(value = "${config.jms.seedQueue}")
    public void onSeed(@MessageBody ClockedPosition message) {
        // seed ignores clock
        model.seed(message.fromDto());
    }

    @Topic(value = "${config.jms.aliveQueue}")
    public void onLivingNeighbour(@MessageBody ClockedPosition message) {
        model.recordLivingNeighbour(message.fromDto());
    }

    @Topic(value = "${config.jms.tickQueue}")
    public void onTick(@MessageBody int clock) {
        if (currentClock < clock) {
            currentClock = clock;
            model.tick();
            if (model.isAlive()) {
                reportAlive.report(new ClockedPosition(clock, model.getPosition().getX(), model.getPosition().getY()));
            }
        }
    }

}
