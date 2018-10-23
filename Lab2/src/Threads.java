
public class Threads {
    public static int x;

    public static void start() throws InterruptedException {
        x = 0;
        Semaphore sem = new Semaphore();

        Thread threadInc = new Thread(() -> {
            for(int i = 0 ; i < 10000 ; i++) {
                sem.take();
                x++;
                sem.release();
            }
        });

        Thread threadDec = new Thread(() -> {
            for(int i = 0 ; i < 10000 ; i++) {
                sem.take();
                x--;
                sem.release();
            }
        });

        threadInc.start();
        threadDec.start();

        threadInc.join();
        threadDec.join();
    }
}
