package org.codecop.monolith.h2;

public class Server {

    /*
     * When running without options, -tcp, -web, -browser and -pg are started. <br>
     * <br>
     * Options are case sensitive. Supported options[-help] or [-?]Print the list of options<br>
     * [-web] Start the web server with the H2 Console<br>
     * [-webAllowOthers] Allow other computers to connect - see below<br>
     * [-webDaemon] Use a daemon thread<br>
     * [-webPort <port>] The port (default\: 8082)<br>
     * [-webSSL] Use encrypted (HTTPS) connections<br>
     * [-webAdminPassword] Password of DB Console administrator<br>
     * [-browser] Start a browser connecting to the web server<br>
     * [-tcp] Start the TCP server<br>
     * [-tcpAllowOthers] Allow other computers to connect - see below<br>
     * [-tcpDaemon] Use a daemon thread<br>
     * [-tcpPort <port>] The port (default\: 9092)<br>
     * [-tcpSSL] Use encrypted (SSL) connections<br>
     * [-tcpPassword <pwd>] The password for shutting down a TCP server<br>
     * [-tcpShutdown "<url>"] Stop the TCP server; example\: tcp\://localhost<br>
     * [-tcpShutdownForce] Do not wait until all connections are closed<br>
     * [-pg] Start the PG server<br>
     * [-pgAllowOthers] Allow other computers to connect - see below<br>
     * [-pgDaemon] Use a daemon thread<br>
     * [-pgPort <port>] The port (default\: 5435)<br>
     * [-properties "<dir>"] Server properties (default\: ~, disable\: null)<br>
     * [-baseDir <dir>] The base directory for H2 databases (all servers)<br>
     * [-ifExists] Only existing databases may be opened (all servers)<br>
     * [-ifNotExists] Databases are created when accessed<br>
     * [-trace] Print additional trace information (all servers)<br>
     * [-key <from> <to>] Allows to map a database name to another (all servers)<br>
     * The options -xAllowOthers are potentially risky.<br>
     * <br>
     * For details, see Advanced Topics / Protection against Remote Access.
     */
    public static void main(String[] args) throws Exception {
        org.h2.tools.Server.main(args);
    }

}
