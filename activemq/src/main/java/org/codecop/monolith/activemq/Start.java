package org.codecop.monolith.activemq;

import org.apache.activemq.console.Main;

public class Start {

    public static void main(String[] args) {
        // Main.main(new String[] { "start", "-h" });
        Main.main(new String[] { "start", "broker:(tcp://0.0.0.0:61616)?persistent=false&useJmx=true" });

        // see https://activemq.apache.org/how-do-i-embed-a-broker-inside-a-connection.html
        //        BrokerService broker = createBroker(61616);
        //        startAndWait(broker);
    }

    //    public static BrokerService createBroker(int port) throws Exception {
    //        BrokerService broker = new BrokerService();
    //        broker.setPersistent(false);
    //        broker.setUseJmx(true);
    //        broker.addConnector("tcp://0.0.0.0:" + port);
    //        return broker;
    //    }
    //
    //    public static void startAndWait(BrokerService broker) throws Exception {
    //        broker.start();
    //        broker.waitUntilStopped();
    //    }

}
