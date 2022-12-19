package org.codecop.monolith.playground.jms;

import static io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME;

import org.codecop.monolith.playground.gol.Model;
import org.codecop.monolith.playground.gol.Position;

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
        model.seed(fromDto(message));
    }

    private Position fromDto(ClockedPosition message) {
        return new Position(message.getX(), message.getY());
    }

    @Topic(value = "${config.jms.aliveQueue}")
    public void onLivingNeighbour(@MessageBody ClockedPosition message) {
        model.recordLivingNeighbour(fromDto(message));
    }

    @Topic(value = "${config.jms.tickQueue}")
    public void onTick(@MessageBody int clock) {
        if (currentClock < clock) {
            currentClock = clock;

            model.tick();

            if (model.isAlive()) {
                reportAlive.report(toDto(clock, model.getPosition()));
            }
        }
    }

    private ClockedPosition toDto(int clock, Position position) {
        return new ClockedPosition(clock, position.getX(), position.getY());
    }

}
