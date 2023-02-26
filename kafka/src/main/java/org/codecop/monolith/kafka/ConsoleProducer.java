package org.codecop.monolith.kafka;

import java.util.Arrays;

public class ConsoleProducer {
    public static void main(String[] args) {
        kafka.tools.ConsoleProducer.main(Args.from(Arrays.asList( //
                "--bootstrap-server", "PLAINTEXT://:9092" //
                // ,"--topic", "analytics" //
        ), args));
    }
}

/*
 * kafka-console-consumer --bootstrap-server PLAINTEXT://:9092 --from-beginning --topic analytics
 * kafka-console-producer --bootstrap-server PLAINTEXT://:9092 --topic analytics
 */
