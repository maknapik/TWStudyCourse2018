package Test;

import pl.edu.agh.Consumer;
import pl.edu.agh.Monitor.PC;
import pl.edu.agh.Producer;
import pl.edu.agh.Proxy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TestController {

    private static int TESTS_AMOUNT = 10 ;
    private static int THREADS_AMOUNT = 0;
    private static int UNITS_AMOUNT = 1000;

    private static List<Thread> threads = new ArrayList<>();

    public static void startTests() {
        try {
            try {
                FileWriter fileWriter = new FileWriter("/home/mateusz/Documents/Semester V/TWStudyCourse2018/Lab5/ActiveObject/out/production/ActiveObject/pl/edu/agh/results.txt");

                for(int i = 0 ; i <= 10 ; i++) {
                    THREADS_AMOUNT += 1000;
                    UNITS_AMOUNT = 10;
                    executeTest(fileWriter, true, true);
                }

                fileWriter.write("--------------------------------------------------\n");
                UNITS_AMOUNT = 0;
                for(int i = 0 ; i <= 10 ; i++) {
                    THREADS_AMOUNT = 10;
                    UNITS_AMOUNT += 1000;
                    executeTest(fileWriter, true, false);
                }

                fileWriter.write("--------------------------------------------------\n");
                fileWriter.write("MONITOR\n");
                fileWriter.write("--------------------------------------------------\n");

                System.out.println("MONITOR");

                THREADS_AMOUNT = 0;
                for(int i = 0 ; i <= 10 ; i++) {
                    THREADS_AMOUNT += 1000;
                    UNITS_AMOUNT = 10;
                    executeTest(fileWriter, false, true);
                }

                fileWriter.write("--------------------------------------------------\n");
                UNITS_AMOUNT = 0;
                for(int i = 0 ; i <= 10 ; i++) {
                    THREADS_AMOUNT = 10;
                    UNITS_AMOUNT += 1000;
                    executeTest(fileWriter, false, false);
                }

                fileWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("End of tests");
    }

    private static void executeTest(FileWriter fileWriter, boolean objectType, boolean type) throws InterruptedException {
        System.out.println("Parameters: threads - " + THREADS_AMOUNT + ", units - " + UNITS_AMOUNT);
        double summaryTime = 0;
        for(int i = 0 ; i < TESTS_AMOUNT ; i++) {

            double millis = System.currentTimeMillis();

            if(objectType) {
                createThreads();
            } else {
                createMonitorThreads();
            }

            startThreads();

            summaryTime += (System.currentTimeMillis() - millis);
        }

        summaryTime /= TESTS_AMOUNT;
        try {
            if(type) {
                fileWriter.write(Integer.toString(THREADS_AMOUNT) + " " + Double.toString((summaryTime / 1000) % 60) + "\n");
            } else {
                fileWriter.write(Integer.toString(UNITS_AMOUNT) + " " + Double.toString((summaryTime / 1000) % 60) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startThreads() throws InterruptedException {
        for(Thread thread : threads) {
            thread.start();
        }
        for(Thread thread : threads) {
            thread.join();
        }
    }

    private static void createThreads() {
        threads = new ArrayList<>();
        Proxy proxy = new Proxy(UNITS_AMOUNT * 2);

        for(int i = 0 ; i < THREADS_AMOUNT ; i++) {
            threads.add(new Thread(new Producer(proxy, UNITS_AMOUNT, i)));
            threads.add(new Thread(new Consumer(proxy, UNITS_AMOUNT, i)));
        }
    }

    private static void createMonitorThreads() {
        threads = new ArrayList<>();
        PC pc = new PC(UNITS_AMOUNT * 2);

        for(int i = 0 ; i < THREADS_AMOUNT ; i++) {
            threads.add(new Thread(() -> {
                int elementsToProduce = UNITS_AMOUNT;
                try
                {
                    while (elementsToProduce > 0) {
                        int amount = ThreadLocalRandom.current().nextInt(1, elementsToProduce + 1);
                        pc.produce(amount);
                        elementsToProduce -= amount;
                    }
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }));

            threads.add(new Thread(() -> {
                int elementsToConsume = UNITS_AMOUNT;
                try {
                    while (elementsToConsume > 0) {
                        int amount = ThreadLocalRandom.current().nextInt(1, elementsToConsume + 1);
                        pc.consume(amount);
                        elementsToConsume -= amount;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));
        }
    }
}
