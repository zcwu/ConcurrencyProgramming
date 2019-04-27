import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CountDownLatchTest {

    private static CountDownLatch latch = new CountDownLatch(10);
    private static long num = 0;
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; ++i) {
            Thread thread = new Thread(new Increase(), "Thread-" + i);
            thread.start();
            //thread.join();
            //latch.countDown();
        }
        latch.await();
        System.out.println(Thread.currentThread().getName());
        System.out.println(num);
    }

    private static class Increase implements Runnable {
        @Override
        public void run() {
            lock.lock();
            for (int i = 0; i < 10000; ++i) {
                num++;
            }
            lock.unlock();
            System.out.println(Thread.currentThread().getName());
            latch.countDown();
        }
    }
}
