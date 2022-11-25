package org.codecop.monolith.kafka;

public class Start {

    public static void main(String[] args) {
        // KAFKA_HEAP_OPTS=-Xmx1G -Xms1G
        System.getProperties().put("kafka.logs.dir", "./target/kafka-log4j");
        System.getProperties().put("log4j.configuration", "file:./src/main/resources/log4j.properties");
        kafka.Kafka.main(new String[] { "./src/main/resources/server.properties" });
    }

}
