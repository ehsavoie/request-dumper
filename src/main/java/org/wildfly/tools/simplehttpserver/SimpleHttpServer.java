package org.wildfly.tools.simplehttpserver;

//DEPS info.picocli:picocli:4.7.0

import com.sun.net.httpserver.SimpleFileServer;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.net.InetSocketAddress;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

/**
 *
 * @author ehsavoie
 */
@Command(name = "request-dumper", mixinStandardHelpOptions = true, version = "request-dumper 0.1", description = "Simple HTTP request dumper with jbang")
public class SimpleHttpServer implements Callable<Integer> {

    @Parameters(index = "0", description = "Set the served directory (default is the current directory)", defaultValue = "")
    private File directory;

    @CommandLine.Option(names = "--port", description = "Set the port of the listening server", defaultValue = "5000")
    private int port;

    private HttpServer httpServer;

    public static void main(String[] args) {
        SimpleHttpServer server = new SimpleHttpServer();
        new CommandLine(server).execute(args);
        server.listen();
    }

    public void listen() {
        httpServer.start();
    }

    @Override
    public Integer call() throws Exception {
        httpServer = SimpleFileServer.createFileServer(new InetSocketAddress(port), directory.getAbsoluteFile().toPath(), SimpleFileServer.OutputLevel.VERBOSE);
        return 0;
    }
}
