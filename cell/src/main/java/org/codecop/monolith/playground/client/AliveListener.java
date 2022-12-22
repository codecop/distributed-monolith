package org.codecop.monolith.playground.client;

import static io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.codecop.monolith.playground.events.ClockedPosition;
import org.codecop.monolith.playground.events.ClockedPositionConverter;

import io.micronaut.jms.annotations.JMSListener;
import io.micronaut.jms.annotations.Topic;
import io.micronaut.messaging.annotation.MessageBody;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
@JMSListener(CONNECTION_FACTORY_BEAN_NAME)
public class AliveListener {

    List<ClockedPosition> livingCells = new ArrayList<>();

    @Inject
    private ClockedPositionConverter converter;

    @Topic(value = "${config.jms.aliveQueue}")
    public void onLivingNeighbour(@MessageBody ClockedPosition message) {
        livingCells.add(message);
    }

    @Topic(value = "${config.jms.tickQueue}")
    public void onTick(@MessageBody int clock) {
        livingCells = livingCells.stream(). //
                filter(p -> p.getClock() >= clock). //
                collect(Collectors.toList());
    }

}
