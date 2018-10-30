import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String args[]) throws InterruptedException {
        /*List<Philosopher> philosophers = new ArrayList<>();
        List<Semaphore> forks = new ArrayList<>();

        for(int i = 0 ; i < 5 ; i++) {
            philosophers.add(new Philosopher());
            forks.add(new Semaphore());
            forks.get(i).release();
        }

        List<Thread> threads = new ArrayList<>();

        for(int i = 0 ; i < 5 ; i++) {
            final int I = i;
            threads.add(new Thread(() -> {
                while(true) {
                    try {
                        philosophers.get(I).think();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    try {
                        forks.get(I).take();
                        forks.get((I + 1) % 5).take();
                        System.out.println("Forks: " + I + ", " + (I + 1) % 5);
                        philosophers.get(I).eat();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    forks.get(I).release();
                    forks.get((I + 1) % 5).release();
                }
            }));
        }

        for(int i = 0 ; i < 5 ; i++) {
            threads.get(i).start();
        }*/

        final PC pc = new PC();
        List<Thread> threads = new ArrayList<>();

        for(int i = 0 ; i < 100 ; i++) {
            final int index = i;
            threads.add(new Thread(() -> {
                try
                {
                    pc.produce(index);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }));

            threads.add(new Thread(() -> {
                try {
                    pc.consume(index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }
}
