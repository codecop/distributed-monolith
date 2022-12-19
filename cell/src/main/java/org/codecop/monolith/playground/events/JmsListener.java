package org.codecop.monolith.playground.events;

import static io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME;

import org.codecop.monolith.playground.gol.Cell;

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

    @Inject
    private Cell cell;
    @Inject
    private ReportAliveProducer reportAlive;
    @Inject
    private TickProducer ticker;

    @Inject
    private Time now;

    @Inject
    private ClockedPositionConverter converter;

    @Topic(value = "${config.jms.seedQueue}")
    public void onSeed(@MessageBody ClockedPosition message) {
        // seed ignores clock
        cell.seed(converter.fromDto(message));
        broadcastLife(now.getCurrentClock());
    }

    @Topic(value = "${config.jms.aliveQueue}")
    public void onLivingNeighbour(@MessageBody ClockedPosition message) {
        if (now.isStale(message)) {
            now.reportStale("LivingNeighbour", message);

        } else if (now.isNow(message)) {
            cell.recordLivingNeighbour(converter.fromDto(message));

        } else /* in future */ {
            now.onLivingNeighbourInFuture(message);
        }
    }

    @Topic(value = "${config.jms.tickQueue}")
    public void onTick(@MessageBody int clock) {
        if (!now.isNewer(clock)) {
            now.reportStale("Tick", clock);
            return;
        }

        while (now.isNewer(clock)) {
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
