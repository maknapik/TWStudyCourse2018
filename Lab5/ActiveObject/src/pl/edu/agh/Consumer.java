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
    private int id;

    public Consumer(Proxy proxy, int elementsToConsume, int id) {
        this.proxy = proxy;

        results = new ArrayList<>();

        this.elementsToConsume = elementsToConsume;
        this.id = id;
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
                List<Integer> list = (List<Integer>) result.take();
                //System.out.println("\tConsumed [" + id + "]: " + list.size());
                iterator.remove();
            }
        }
    }
}
