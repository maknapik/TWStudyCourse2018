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

        Thread t1 = new Thread(() -> {
            try
            {
                pc.produce();
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try
            {
                pc.consume();
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
