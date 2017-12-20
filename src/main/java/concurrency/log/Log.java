package concurrency.log;

public class Log {
    public static void info(Object o) {
        System.out.println(o);
    }

    public static void error(Object o) {
        System.err.println(o);
    }
}
