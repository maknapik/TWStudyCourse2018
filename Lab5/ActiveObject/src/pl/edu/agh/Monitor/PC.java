package pl.edu.agh.Monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PC {
    private int capacity;
    private int actual_amount;
    private List<Integer> buffer;

    private ReentrantLock lock = new ReentrantLock();
    private Condition pierw_prod = lock.newCondition();
    private Condition reszt_prod = lock.newCondition();
    private Condition pierw_kons = lock.newCondition();
    private Condition reszt_kons = lock.newCondition();

    public PC(int capacity) {
        this.capacity = capacity;
        actual_amount = 0;
        buffer = new ArrayList<>();
    }

    public void produce(int amount) throws InterruptedException {
        lock.lock();

        if (lock.hasWaiters(pierw_prod)) {
            reszt_prod.await();
        }
        while (capacity - actual_amount < amount) {
            pierw_prod.await();
        }

        actual_amount += amount;
        for(int i = 0 ; i < amount ; i++) {
            buffer.add((int)(Math.random() * 5));
        }

        //System.out.println("Added: " + amount + ", buffer: " + actual_amount);

        reszt_prod.signal();
        pierw_kons.signal();

        lock.unlock();
    }

    public void consume(int amount) throws InterruptedException {
        lock.lock();

        if (lock.hasWaiters(pierw_kons)) {
            reszt_kons.await();
        }
        while (actual_amount < amount) {
            pierw_kons.await();
        }

        actual_amount -= amount;

        for(int i = 0 ; i < amount ; i++) {
            buffer.remove(0);
        }

        reszt_kons.signal();
        pierw_prod.signal();

        lock.unlock();
    }
}