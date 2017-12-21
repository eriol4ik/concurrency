package concurrency.example.e02_Semaphore;

public class Launcher02 {
    private static final int COUNT = 5;

    public static void main(String[] args) {
        BoundedHashSet<Integer> set = new BoundedHashSet<>(COUNT);
        for (int i = 0; i < COUNT; i++) {
            set.add(i);
        }
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < COUNT; i++) {
                set.add(i);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < COUNT; i++) {
                set.remove(i);
            }
        });
        thread1.start();
        thread2.start();
    }
}
