package concurrency.example.e04_CyclicBarrier;

import concurrency.log.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Launcher04 {
    private static final int COUNT = 5;
    private static final List<Long> list = Collections.synchronizedList(new ArrayList<>());
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(COUNT, new LongListSummarizer());
    private static long i = 0;
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        while (!reader.readLine().equals("q")) {
            new RandomNumberGenerator().start();
        }
        System.exit(0);
    }

    private static class RandomNumberGenerator extends Thread {
        @Override
        public void run() {
            list.add(i++);
            Log.line("Generated " + i);
            try {
                int await = cyclicBarrier.await();
                Log.line("await returned " + await + " from " + Thread.currentThread().getName());
            } catch (InterruptedException | BrokenBarrierException e) {
                Log.error("InterruptedException | BrokenBarrierException");
            }
        }
    }

    private static class LongListSummarizer implements Runnable {
        @Override
        public void run() {
            Log.line("Summarizing... -> " + list.stream().mapToLong(n -> n).sum());
        }
    }
}
