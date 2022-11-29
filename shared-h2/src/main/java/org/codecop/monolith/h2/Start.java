package org.codecop.monolith.h2;

public class Start {

    public static void main(String[] args) throws Exception {
        // see https://www.h2database.com/javadoc/org/h2/tools/Server.html
        Server.main(new String[] { //
                "-tcp", //
                "-tcpPort", "9092", //
                "-tcpAllowOthers", //
                "-tcpPassword", "secretShutdown99", //
                "-ifNotExists", //
                // debug web console
                "-web", //
                "-webPort", "8082", //
        });
    }

}
