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
        for (int i = 0; i < 20; ++i) {
            Future<Integer> future = executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println(Thread.currentThread().getName());
                    return Thread.currentThread().getPriority();
                }
            });
            try {
                sum += future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println(sum);

        executorService.shutdown();
    }
}
