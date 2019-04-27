import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Counter {

    private int i = 0;
    private AtomicInteger atomicI = new AtomicInteger(0);
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        List<Thread> threadList = new ArrayList<>();
        final Counter counter = new Counter();

        long start = System.currentTimeMillis();
        for (int j = 0; j < 100; ++j) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    for (int i = 0; i < 10000; ++i) {
                        counter.count();
                    }
                    lock.unlock();
                }
            });
            threadList.add(t);
        }
        for (Thread t : threadList) {
            t.start();
        }
        for (Thread t : threadList) {
            t.join();
        }
        System.out.println(counter.i);
        System.out.println(System.currentTimeMillis() - start);

        threadList.clear();
        start = System.currentTimeMillis();
        for (int j = 0; j < 100; ++j) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; ++i) {
                        counter.safeCount();
                    }
                }
            });
            threadList.add(t);
        }
        for (Thread t : threadList) {
            t.start();
        }
        for (Thread t : threadList) {
            t.join();
        }
        System.out.println(counter.atomicI.get());
        System.out.println(System.currentTimeMillis() - start);
    }

    private void safeCount() {

        while (true) {
            int i = atomicI.get();
            boolean suc = atomicI.compareAndSet(i, ++i);
            if (suc) {
                break;
            }
        }
    }

    private void count() {

        ++i;
    }
}
