package concurrency.example.e01_CountDownLatch;

import concurrency.log.Log;

public class Launcher01 {
    public static void main(String[] args) {
        TestHarness testHarness = new TestHarness();
        try {
            long time = testHarness.timeTasks(5, () -> Log.line("Hello from " + Thread.currentThread().getName()));
            Log.line(time);
        } catch (InterruptedException e) {
            Log.line("InterruptedException from main");
        }
    }
}
