package org.codecop.activemq;

import org.apache.activemq.broker.BrokerService;

public class Application {

    public static void main(String[] args) throws Exception {
        // see https://activemq.apache.org/how-do-i-embed-a-broker-inside-a-connection.html
        BrokerService broker = new BrokerService();
        broker.setPersistent(false);
        broker.setUseJmx(true);
        broker.addConnector("tcp://0.0.0.0:61616");
        broker.start();
        broker.waitUntilStopped();
    }

}
