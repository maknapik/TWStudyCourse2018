import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class PC {

    private Queue<Integer> list = new LinkedList<>();
    private int capacity = 10;

    private Lock lock = new ReentrantLock();
    private Condition isEmpty = lock.newCondition();
    private Condition isFull = lock.newCondition();

    void produce(int index) throws InterruptedException
    {
        int value = 0;
        while (true)
        {
            lock.lock();

            while (list.size() == capacity) {
                System.out.println(String.format("Producer %d is waiting", index));
                isEmpty.await();
            }

            System.out.println(String.format("Producer %d produced - %d", index, value));

            list.add(value++);

            isFull.signal();

            lock.unlock();
        }
    }

    void consume(int index) throws InterruptedException
    {
        while (true)
        {
            lock.lock();

            while (list.size() == 0) {
                System.out.println(String.format("Consumer %d is waiting", index));
                isFull.await();
            }

            int val = list.poll();

            System.out.println(String.format("Consumer %d consumed - %d", index, val));

            isEmpty.signal();

            lock.unlock();
        }
    }
}