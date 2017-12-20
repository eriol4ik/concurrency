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
                Log.info("Acquiring...");
                super.acquire();
                Log.info("Done!");
            }
        };
    }

    public boolean add(T o) {
        Log.info("Adding " + o);
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
                Log.info("Not added " + o);
            } else {
                Log.info("Added " + o);
            }
            Log.info(set.size());
        }
    }

    public boolean remove(T o) {
        Log.info("Try removing " + o);
        boolean wasRemoved = set.remove(o);
        if (wasRemoved) {
            semaphore.release();
            Log.info("Removed " + o);
        }
        Log.info(set.size());
        return wasRemoved;
    }
}
