package concurrency.log;

public class Log {
    public static void line(Object o) {
        System.out.println(o);
    }

    public static void inline(Object o) {
        System.out.print(o);
    }

    public static void error(Object o) {
        System.err.println(o);
    }
}
