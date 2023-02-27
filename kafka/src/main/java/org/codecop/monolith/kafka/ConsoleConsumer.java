package org.codecop.monolith.kafka;

import java.util.Arrays;

public class ConsoleConsumer {
    public static void main(String[] args) {
        System.getProperties().put("kafka.logs.dir", "./target/kafka-logs");
        System.getProperties().put("log4j.configuration", "file:./src/main/resources/log4j.properties");

        kafka.tools.ConsoleConsumer.main(Args.from(Arrays.asList( // 
                "--bootstrap-server", "PLAINTEXT://:9092" //
                // ,"--from-beginning", "--topic", "playground-analytics" //
        ), args));
    }
}

/*
 * kafka-console-consumer --bootstrap-server PLAINTEXT://:9092 --from-beginning --topic playground-analytics
 * kafka-console-producer --bootstrap-server PLAINTEXT://:9092 --topic playground-analytics
 */
