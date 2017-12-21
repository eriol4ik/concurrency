package concurrency.example.e03_Exchanger;

import concurrency.log.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Exchanger;

public class Launcher03 {
    private static final Exchanger<String> stringExchanger = new Exchanger<>();

    public static void main(String[] args) {
        Thread thread0 = createThreadAndExchange("thread0");
        Thread thread1 = createThreadAndExchange("thread1");
        thread0.start();
        readFromConsoleAndStartThread(thread1);
    }

    private static Thread createThreadAndExchange(String text) {
        return new Thread(() -> {
            String s = null;
            try {
                s = stringExchanger.exchange(text);
            } catch (InterruptedException e) {
                Log.error("InterruptedException");
            }
            Log.line("Receive " + s + " in " + Thread.currentThread().getName());

        });
    }

    private static void readFromConsoleAndStartThread(Thread thread1) {
        boolean toggle = true;
        while (toggle) {
            BufferedReader reader;
            try {
                reader = new BufferedReader(new InputStreamReader(System.in));
                String line = reader.readLine();
                if (line.equals("start")) {
                    thread1.start();
                    toggle = false;
                }
            } catch (IOException e) {
                Log.error("IOException");
            }
        }
    }
}
