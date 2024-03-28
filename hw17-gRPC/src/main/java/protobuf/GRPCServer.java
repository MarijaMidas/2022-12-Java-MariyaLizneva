package protobuf;

import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GRPCServer {

    public static final int SERVER_PORT = 8190;

    private static final Logger log = LoggerFactory.getLogger(GRPCServer.class);

    public static void main(String[] args)throws IOException,InterruptedException {
        var service = new NumberGeneratedService();
        var server = ServerBuilder.forPort(SERVER_PORT).addService(service).build();
        server.start();
        log.info("Server waiting for client connections...");
        server.awaitTermination();
    }
}
