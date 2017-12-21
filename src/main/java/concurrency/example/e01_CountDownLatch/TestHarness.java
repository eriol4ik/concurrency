package concurrency.example.e01_CountDownLatch;

import concurrency.log.Log;

import java.util.concurrent.CountDownLatch;

public class TestHarness {
    public long timeTasks(int nThreads, Runnable task) throws InterruptedException {
        CountDownLatch startGate = new CountDownLatch(1) {
            @Override
            public void countDown() {
                super.countDown();
                Log.line("countDown from startGate " + getCount());
            }
        };
        CountDownLatch endGate = new CountDownLatch(nThreads) {
            @Override
            public void countDown() {
                super.countDown();
                Log.line("countDown from endGate " + getCount());
            }
        };

        for (int i = 0; i < nThreads; i++) {
            Thread thread = new Thread(() -> {
                try {
                    startGate.await();
                    try {
                        task.run();
                    } finally {
                        endGate.countDown();
                    }
                } catch (InterruptedException e) {
                    Log.line("InterruptedException");
                }
            });
            thread.start();
        }

        long start = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
        return end - start;
    }
}
