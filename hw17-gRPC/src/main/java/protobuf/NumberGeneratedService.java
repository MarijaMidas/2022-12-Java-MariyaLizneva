package protobuf;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protobuf.generated.NumbersServiceGrpc;
import protobuf.generated.Request;
import protobuf.generated.Response;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


public class NumberGeneratedService extends NumbersServiceGrpc.NumbersServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(NumberGeneratedService.class);

    private static final int EXECUTOR_POOL_SIZE = 1;
    private static final int EXECUTOR_DELAY = 0;
    private static final int EXECUTOR_PERIOD = 2;

    @Override
    public void getNumber(Request request, StreamObserver<Response> responseObserver) {
        log.info("Start new numbers sequence: first {}, last {}", request.getFirstValue(), request.getLastValue());

        var current = new AtomicLong(request.getFirstValue());
        var executor = Executors.newScheduledThreadPool(EXECUTOR_POOL_SIZE);
        Runnable task = () -> {
            var value = current.incrementAndGet();
            var response = Response.newBuilder().setValue(value).build();
            responseObserver.onNext(response);
            if(value == request.getLastValue()){
                executor.shutdown();
                responseObserver.onCompleted();
            }
        };
        executor.scheduleAtFixedRate(task, EXECUTOR_DELAY, EXECUTOR_PERIOD, TimeUnit.SECONDS);
    }
}
