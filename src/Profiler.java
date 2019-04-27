import sun.nio.ch.ThreadPool;

public class Profiler {

    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<>();

    public static void begin() {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static Long end() {
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }
}
