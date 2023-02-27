package org.codecop.monolith.kafka;

import java.util.Arrays;

public class Topics {
    public static void main(String[] args) {
        kafka.admin.TopicCommand.main(Args.from(Arrays.asList( //
                "--bootstrap-server", "PLAINTEXT://:9092" //
                // ,"--list" //
                // ,"--create", "--topic", "playground-analytics" //
        ), args));
    }
}

/*
 * kafka-topics --bootstrap-server PLAINTEXT://:9092 --list 
 * kafka-topics --bootstrap-server PLAINTEXT://:9092 --create --topic playground-analytics
 */
