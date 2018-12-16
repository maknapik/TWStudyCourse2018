import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class PC {
    private static final int M = 10;
    private int actual_amount;

    private ReentrantLock lock = new ReentrantLock();
    private Condition pierw_prod = lock.newCondition();
    private Condition reszt_prod = lock.newCondition();
    private Condition pierw_kons = lock.newCondition();
    private Condition reszt_kons = lock.newCondition();

    private boolean has_pierw_prod;
    private boolean has_pierw_kons;

    PC() {
        actual_amount = 0;
    }

    int getM() {
        return M;
    }

    void produce(int amount) throws InterruptedException {
        lock.lock();

        while(has_pierw_prod) {
            reszt_prod.await();
        }

        has_pierw_prod = true;

        while (M*2 - actual_amount < amount) {
            pierw_prod.await();
        }

        actual_amount += amount;

        System.out.println("Added: " + amount + ", buffer: " + actual_amount);

        reszt_prod.signal();

        pierw_kons.signal();

        has_pierw_prod = false;
        lock.unlock();
    }

    void consume(int amount) throws InterruptedException {
        lock.lock();

        while(has_pierw_kons) {
            reszt_kons.await();
        }

        has_pierw_kons = true;

        while (actual_amount < amount) {
            pierw_kons.await();
        }

        actual_amount -= amount;

        System.out.println("Taken " + amount + ", buffer: " + actual_amount);

        reszt_kons.signal();

        pierw_prod.signal();

        has_pierw_kons = false;
        lock.unlock();
    }
}