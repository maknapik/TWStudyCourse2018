package pl.edu.agh;

import pl.edu.agh.Future.IFuture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Producer implements Runnable {

    private Proxy proxy;
    private List<IFuture> results;
    private int elementsToProduce;
    private int id;

    public Producer(Proxy proxy, int elementsToProduce, int id) {
        this.proxy = proxy;

        results = new ArrayList<>();

        this.elementsToProduce = elementsToProduce;
        this.id = id;
    }

    @Override
    public void run() {
        while(elementsToProduce > 0) {
            int amount = ThreadLocalRandom.current().nextInt(1, elementsToProduce + 1);
            List<Integer> elements = new ArrayList<>();

            for(int i = 0 ; i < amount ; i++) {
                elements.add(ThreadLocalRandom.current().nextInt(10));
            }

            results.add(proxy.put(elements));

            elementsToProduce -= amount;
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
                //System.out.println("Produced [" + id + "]: " + result.take());
                iterator.remove();
            }
        }
    }
}
