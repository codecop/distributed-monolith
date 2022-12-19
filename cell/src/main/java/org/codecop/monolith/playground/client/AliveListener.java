package org.codecop.monolith.playground.client;

import static io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME;

import java.util.ArrayList;
import java.util.List;

import org.codecop.monolith.playground.events.ClockedPosition;
import org.codecop.monolith.playground.events.ClockedPositionConverter;
import org.codecop.monolith.playground.gol.Position;

import io.micronaut.jms.annotations.JMSListener;
import io.micronaut.jms.annotations.Topic;
import io.micronaut.messaging.annotation.MessageBody;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
@JMSListener(CONNECTION_FACTORY_BEAN_NAME)
public class AliveListener {

    final List<Position> livingCells = new ArrayList<>();

    @Inject
    private ClockedPositionConverter converter;

    @Topic(value = "${config.jms.aliveQueue}")
    public void onLivingNeighbour(@MessageBody ClockedPosition message) {
        livingCells.add(converter.fromDto(message));
    }

    @Topic(value = "${config.jms.tickQueue}")
    public void onTick(@SuppressWarnings("unused") @MessageBody int clock) {
        livingCells.clear();
    }

}
