package org.codecop.monolith.h2;

public class Stop {

    public static void main(String[] args) throws Exception {
        Server.main(new String[] { //
                "-tcpShutdown", "tcp://localhost:9092", //
                "-tcpPassword", "secretShutdown99", //
        });
    }

}
