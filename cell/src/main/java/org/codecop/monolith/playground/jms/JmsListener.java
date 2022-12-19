package org.codecop.monolith.playground.jms;

import static io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codecop.monolith.playground.gol.Model;
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
    private Model model;
    @Inject
    private ReportAliveProducer reportAlive;
    @Inject
    private TickProducer ticker;

    private int currentClock = -1;

    private final Map<Integer, List<Position>> futureNeighbours = new HashMap<>();

    @Topic(value = "${config.jms.seedQueue}")
    public void onSeed(@MessageBody ClockedPosition message) {
        // seed ignores clock
        model.seed(fromDto(message));
        broadcastLife(currentClock);
    }

    private Position fromDto(ClockedPosition message) {
        return new Position(message.getX(), message.getY());
    }

    @Topic(value = "${config.jms.aliveQueue}")
    public void onLivingNeighbour(@MessageBody ClockedPosition message) {
        if (currentClock > message.getClock()) {
            logger.warn("Received LivingNeighbour with older clock {}, current {}, discarding: {}", //
                    message.getClock(), currentClock, message);
            return;
        }

        if (currentClock == message.getClock()) {
            model.recordLivingNeighbour(fromDto(message));

        } else /* in future */ {
            logger.info("Received LivingNeighbour with newer clock {}, current {}, storing: {}", //
                    message.getClock(), currentClock, message);
            storeForFuture(message);
        }
    }

    private void storeForFuture(ClockedPosition message) {
        int futureClock = message.getClock();
        if (!futureNeighbours.containsKey(futureClock)) {
            futureNeighbours.put(futureClock, new ArrayList<Position>());
        }
        futureNeighbours.get(futureClock).add(fromDto(message));
    }

    @Topic(value = "${config.jms.tickQueue}")
    public void onTick(@MessageBody int clock) {
        if (currentClock >= clock) {
            logger.warn("Received Tick with older clock {}, current {}, ignoring", clock, currentClock);
            return;
        }

        while (currentClock < clock) {
            currentClock++;

            model.tick();

            broadcastLife(clock);
            checkFuture();
        }
    }

    private void broadcastLife(int clock) {
        if (model.isAlive()) {
            reportAlive.report(toDto(clock, model.getPosition()));
        }
    }

    private ClockedPosition toDto(int clock, Position position) {
        return new ClockedPosition(clock, position.getX(), position.getY());
    }

    private void checkFuture() {
        if (futureNeighbours.containsKey(currentClock)) {
            futureNeighbours.get(currentClock).stream().forEach(model::recordLivingNeighbour);
            futureNeighbours.remove(currentClock);
        }
    }

    public void triggerTick() {
        ticker.tick(currentClock + 1);
    }
}
