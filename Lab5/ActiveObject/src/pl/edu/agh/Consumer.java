package pl.edu.agh;

import pl.edu.agh.Future.IFuture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Consumer implements Runnable {

    private Proxy proxy;
    private List<IFuture> results;
    private int elementsToConsume;

    public Consumer(Proxy proxy) {
        this.proxy = proxy;

        results = new ArrayList<>();

        elementsToConsume = ThreadLocalRandom.current().nextInt(1, Parameters.MAX_CONSUME_AMOUNT + 1);
    }

    @Override
    public void run() {
        while(elementsToConsume > 0) {
            int amount = ThreadLocalRandom.current().nextInt(1, elementsToConsume + 1);

            results.add(proxy.take(amount));

            elementsToConsume -= amount;

        }

        while(!results.isEmpty()) {
            checkResults();
        }
    }

    private void checkResults() {
        Iterator<IFuture> iterator = results.iterator();
        while(iterator.hasNext()) {
            IFuture result = iterator.next();
            if(result.ready()) {
                System.out.println("Consumed: " + result.take());
                iterator.remove();
            }
        }
    }
}
