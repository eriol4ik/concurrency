package concurrency.example.e01_CountDownLatch;

import concurrency.log.Log;

public class Launcher01 {
    public static void main(String[] args) {
        TestHarness testHarness = new TestHarness();
        try {
            long time = testHarness.timeTasks(5, () -> Log.info("Hello from " + Thread.currentThread().getName()));
            Log.info(time);
        } catch (InterruptedException e) {
            Log.info("InterruptedException from main");
        }
    }
}
