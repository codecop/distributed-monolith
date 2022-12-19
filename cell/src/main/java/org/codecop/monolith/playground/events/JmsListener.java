package org.codecop.monolith.playground.events;

import static io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME;

import org.codecop.monolith.playground.gol.Cell;

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
    private Cell cell;
    @Inject
    private ClockedPositionConverter converter;
    @Inject
    private Time time;

    @Inject
    private ReportAliveProducer reportAlive;

    @Topic(value = "${config.jms.seedQueue}")
    public void onSeed(@MessageBody ClockedPosition message) {
        // seed ignores clock
        cell.seed(converter.fromDto(message));
        broadcastLife();
    }

    @Topic(value = "${config.jms.aliveQueue}")
    public void onLivingNeighbour(@MessageBody ClockedPosition message) {
        if (time.isStale(message)) {
            time.reportStale("LivingNeighbour", message);

        } else if (time.isNow(message)) {
            cell.recordLivingNeighbour(converter.fromDto(message));

        } else /* in future */ {
            time.storeLivingNeighbourForFuture(message);
        }
    }

    @Topic(value = "${config.jms.tickQueue}")
    public void onTick(@MessageBody int clock) {
        if (!time.isNewer(clock)) {
            time.reportStale("Tick", clock);
            return;
        }

        while (time.isNewer(clock)) {
            time.nextTime();

            cell.tick();
            broadcastLife(clock);

            time.eachFutureLivingNeighbour(cell::recordLivingNeighbour);
        }
    }

    private void broadcastLife() {
        broadcastLife(time.getCurrent());
    }

    private void broadcastLife(int clock) {
        if (cell.isAlive()) {
            reportAlive.report(converter.toDto(clock, cell.getPosition()));
        }
    }

}
