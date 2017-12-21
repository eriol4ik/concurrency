package concurrency.example.e02_Semaphore;

import concurrency.log.Log;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class BoundedHashSet<T> {
    private final Set<T> set;
    private final Semaphore semaphore;

    public BoundedHashSet(int bound) {
        this.set = Collections.synchronizedSet(new HashSet<>());
        semaphore = new Semaphore(bound) {
            @Override
            public void acquire() throws InterruptedException {
                Log.line("Acquiring... (available = " + this.availablePermits() + ")");
                super.acquire();
                Log.line("-> Done!");
            }
        };
    }

    public boolean add(T o) {
        Log.line("Adding " + o);
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            Log.error("InterruptedException on acquiring");
        }
        boolean wasAdded = false;
        try {
            wasAdded = set.add(o);
            return wasAdded;
        } finally {
            if (!wasAdded) {
                semaphore.release();
                Log.line("-> Not added " + o + " ");
            } else {
                Log.line("-> Added " + o + " ");
            }
            Log.line("-> size = " + set.size());
        }
    }

    public boolean remove(T o) {
        Log.line("Try removing " + o);
        boolean wasRemoved = set.remove(o);
        if (wasRemoved) {
            semaphore.release();
            Log.line("-> Removed " + o + ", size = " + set.size());
        }
        return wasRemoved;
    }
}
