package concurrency.example.e06_Future_ExecutorService;

import concurrency.log.Log;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Launcher06 {
    private static final int REFRESH_RATE = 300;
    private static final int N_THREADS = 2;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        SquareCalculator squareCalculator = new SquareCalculator(N_THREADS);

        Future<Integer> future1 = squareCalculator.calculate(10);
        Future<Integer> future2 = squareCalculator.calculate(100);
        Future<Integer> future3 = squareCalculator.calculate(1000);

        while (!future1.isDone() || !future2.isDone() || !future3.isDone()) {
            Log.fline("future1 is %s, future2 is %s, future3 is %s",
                    future1.isDone() ? "done" : "not done",
                    future2.isDone() ? "done" : "not done",
                    future3.isDone() ? "done" : "not done");
            Thread.sleep(REFRESH_RATE);
        }

        Integer result1 = future1.get();
        Integer result2 = future2.get();
        Integer result3 = future3.get();
        Log.fline("%s, %s, %s", result1, result2, result3);

        squareCalculator.shutdown();
    }
}
