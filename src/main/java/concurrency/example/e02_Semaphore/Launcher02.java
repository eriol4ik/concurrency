package concurrency.example.e02_Semaphore;

public class Launcher02 {
    public static void main(String[] args) {
        BoundedHashSet<Integer> set = new BoundedHashSet<>(5);
        for (int i = 0; i < 5; i++) {
            set.add(i);
        }
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                set.add(i);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                set.remove(i++);
            }
        });
        thread1.start();
        thread2.start();
    }
}
