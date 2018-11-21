package pl.edu.agh;

import java.util.ArrayList;
import java.util.List;

public class Main {

    static List<Thread> threads = new ArrayList<>();
    static Proxy proxy = new Proxy();

    public static void main(String[] args) {

        generateProducers();
        generateConsumers();

        for(Thread thread : threads) {
            thread.start();
        }
    }

    private static void generateProducers() {
        for(int i = 0 ; i < Parameters.PRODUCERS_AMOUNT ; i++) {
            threads.add(new Thread(new Producer(proxy)));
        }
    }

    private static void generateConsumers() {
        for(int i = 0 ; i < Parameters.CONSUMERS_AMOUNT ; i++) {
            threads.add(new Thread(new Consumer(proxy)));
        }
    }
}
