package org.codecop.monolith.playground.jms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest()
class JmsTest {

    @Inject
    StringMessageProducer producer;

    @Inject
    TextStore store;

    @Test
    void sendAndReceiveJMS() throws InterruptedException {
        assertEquals(0, store.messages.size());
        waitForJms();
        producer.send("This is a message");
        producer.send("This is a message"); // have to send two for one to arrive?
        waitForJms();
        assertEquals(1, store.messages.size());
        System.out.println(store.messages);
    }

    private void waitForJms() throws InterruptedException {
        Thread.sleep(500);
    }
}
