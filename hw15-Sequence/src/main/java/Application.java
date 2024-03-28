
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.currentThread;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private static Semaphore semaphore1 = new Semaphore(1);
    private static Semaphore semaphore2 = new Semaphore(0);
    static AtomicInteger counter = new AtomicInteger(0);
    static boolean lock = false;
    static boolean asc = true;

    private static void action(Semaphore semaphore1, Semaphore semaphore2) {
        while (!currentThread().isInterrupted()) {
            try {
                semaphore1.acquire();
                lock = !lock;
                if(lock){
                    if(counter.get() == 1){
                        asc = true;
                    }
                    inc(asc);
                    if(counter.get() == 10){
                        asc = false;
                    }

                }
                logger.info("I am: {} number: {}", currentThread().getName(),counter);
                sleep();
                semaphore2.release();
            } catch (InterruptedException ex) {
                currentThread().interrupt();
            }
        }
    }

    private static void inc(boolean queue){
        if(queue){
            counter.incrementAndGet();
        }else {
            counter.decrementAndGet();
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        var t1 = new Thread(()->action(semaphore1,semaphore2));
        var t2 = new Thread(()->action(semaphore2,semaphore1));

        t1.setName("First Thread");
        t2.setName("Second Thread");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
