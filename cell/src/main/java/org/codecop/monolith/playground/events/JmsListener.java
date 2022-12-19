package org.codecop.monolith.playground.events;

import static io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME;

import org.codecop.monolith.playground.gol.Cell;
import org.codecop.monolith.playground.gol.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.jms.annotations.JMSListener;
import io.micronaut.jms.annotations.Topic;
import io.micronaut.messaging.annotation.MessageBody;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 * Wrapper of the model as JMS receiver. Controlling the clock and time machine.
 */
@Singleton
@JMSListener(CONNECTION_FACTORY_BEAN_NAME)
public class JmsListener {

    private Logger logger = LoggerFactory.getLogger(JmsListener.class);

    @Inject
    private Cell cell;
    @Inject
    private ReportAliveProducer reportAlive;
    @Inject
    private TickProducer ticker;

    @Inject
    private Time now;

    @Inject
    ClockedPositionConverter converter;

    @Topic(value = "${config.jms.seedQueue}")
    public void onSeed(@MessageBody ClockedPosition message) {
        // seed ignores clock
        cell.seed(converter.fromDto(message));
        broadcastLife(now.getCurrentClock());
    }

    @Topic(value = "${config.jms.aliveQueue}")
    public void onLivingNeighbour(@MessageBody ClockedPosition message) {
        if (now.isOld(message)) {
            logger.warn("Received LivingNeighbour with older clock {}, current {}, discarding: {}", //
                    message.getClock(), now.getCurrentClock(), message);
            return;
        }

        if (now.getCurrentClock() == message.getClock()) {
            cell.recordLivingNeighbour(converter.fromDto(message));

        } else /* in future */ {
            now.onLivingNeighbourInFuture(message);
        }
    }

    @Topic(value = "${config.jms.tickQueue}")
    public void onTick(@MessageBody int clock) {
        if (now.getCurrentClock() >= clock) {
            logger.warn("Received Tick with older clock {}, current {}, ignoring", clock, now.getCurrentClock());
            return;
        }

        while (now.getCurrentClock() < clock) {
            now.nextTime();

            cell.tick();

            broadcastLife(clock);
            now.eachFuture(cell::recordLivingNeighbour);
        }
    }

    private void broadcastLife(int clock) {
        if (cell.isAlive()) {
            reportAlive.report(converter.toDto(clock, cell.getPosition()));
        }
    }

    public void triggerTick() {
        ticker.tick(now.getCurrentClock() + 1);
    }

    public void seed() {
        cell.seed(cell.getPosition());
        broadcastLife(now.getCurrentClock());
    }
}
