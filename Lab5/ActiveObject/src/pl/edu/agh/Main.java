package pl.edu.agh;

import Test.TestController;

import java.util.ArrayList;
import java.util.List;


public class Main {

    private static List<Thread> threads = new ArrayList<>();
    private static Proxy proxy = new Proxy(Parameters.CAPACITY);

    public static void main(String[] args) {

        /*generateProducers();
        generateConsumers();

        for(Thread thread : threads) {
            thread.start();
        }*/

        TestController.startTests();
    }

    private static void generateProducers() {
        for(int i = 0 ; i < Parameters.PRODUCERS_AMOUNT ; i++) {
            threads.add(new Thread(new Producer(proxy, 10, i)));
        }
    }

    private static void generateConsumers() {
        for(int i = 0 ; i < Parameters.CONSUMERS_AMOUNT ; i++) {
            threads.add(new Thread(new Consumer(proxy, 10, i)));
        }
    }
}
