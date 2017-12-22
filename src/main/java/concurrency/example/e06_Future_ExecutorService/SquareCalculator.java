package concurrency.example.e06_Future_ExecutorService;

import concurrency.log.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SquareCalculator {
    private ExecutorService executorService;

    public SquareCalculator(int nThreads) {
        executorService = Executors.newFixedThreadPool(nThreads);
    }

    public Future<Integer> calculate(Integer input) {
        return executorService.submit(() -> calculateSquare(input));
    }

    private int calculateSquare(Integer input) throws InterruptedException {
        Thread.sleep(1000);
        int result = input + input;
        Log.line("From " + Thread.currentThread().getName() + ": " + result);
        return result;
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
