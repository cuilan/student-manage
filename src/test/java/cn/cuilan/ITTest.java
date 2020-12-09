package cn.cuilan;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ITTest {

    private static final String THREAD_NAME_PRE = "Test-thread-";

    private static final int QUEUE_SIZE = 100;

    private static final ThreadFactory threadFactory = new ThreadFactory() {

        final AtomicInteger idx = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName(THREAD_NAME_PRE + idx.incrementAndGet());
            return thread;
        }
    };

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            3,
            3,
            3L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(QUEUE_SIZE),
            threadFactory
    );

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            executor.execute(() -> System.out.println(Thread.currentThread().getId() + " : " + Thread.currentThread().getName()));
        }
    }

}
