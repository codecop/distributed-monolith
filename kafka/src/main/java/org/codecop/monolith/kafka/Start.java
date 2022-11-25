package org.codecop.monolith.kafka;

import java.io.File;

public class Start {

    public static void main(String[] args) {
        // KAFKA_HEAP_OPTS=-Xmx1G -Xms1G
        System.getProperties().put("kafka.logs.dir", "./target/kafka-logs");
        System.getProperties().put("log4j.configuration", "file:./src/main/resources/log4j.properties");

        // see https://hevodata.com/learn/kafka-without-zookeeper/

        // kafka.tools.StorageTool.main(new String[] { "random-uuid" });
        String clusterId = "bTkCYXlvSDKjfNaG-z_Smw";
        File logDir = new File("./target/kraft-combined-logs");
        if (!logDir.exists()) {
            kafka.tools.StorageTool.main(new String[] { //
                    //"-h",
                    "format", //
                    "--config", "./src/main/resources/server.properties", //  
                    "--cluster-id", clusterId, //  
            });
            System.exit(0); // is inside the StorageTool
        }

        kafka.Kafka.main(new String[] { //
                "./src/main/resources/server.properties", //
        });

    }

}
