import java.util.concurrent.*;

public class FixedThreadPoolTest {

    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        // test runnable
        for (int i = 0; i < 20; ++i) {
            executorService.submit(() -> {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        // test for callable
        int sum = 0;
        ConcurrentLinkedQueue<Future<Integer>> futureQueue = new ConcurrentLinkedQueue<>();
        CountDownLatch latch = new CountDownLatch(20);
        for (int i = 0; i < 20; ++i) {
            Future<Integer> future = executorService.submit(() -> {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName());
                latch.countDown();
                return Thread.currentThread().getPriority();
            });
            futureQueue.add(future);
        }
        try {
            latch.await();
            while (!futureQueue.isEmpty()) {
                sum += futureQueue.poll().get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(sum);

        executorService.shutdown();
    }
}
