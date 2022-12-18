package org.codecop.monolith.playground;

import static io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME;

import io.micronaut.jms.annotations.JMSListener;
import io.micronaut.jms.annotations.Queue;
import io.micronaut.messaging.annotation.MessageBody;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
@JMSListener(CONNECTION_FACTORY_BEAN_NAME)
public class JmsListener {

    @Inject
    private Life status;

    private int countNeighbours;

    @Queue(value = "${config.jms.tickQueue}", concurrency = "1-5")
    public void onTick(@SuppressWarnings("unused") @MessageBody String unused) {
        status.withNeighbours(countNeighbours);
        countNeighbours = 0;
    }

    @Queue(value = "${config.jms.aliveQueue}", concurrency = "1-5")
    public void onLivingNeighbour(@MessageBody Position at) {
        countNeighbours++;
    }
}
