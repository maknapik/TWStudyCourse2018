package pl.edu.agh;

import pl.edu.agh.Future.IFuture;
import pl.edu.agh.Future.PutFuture;
import pl.edu.agh.Future.TakeFuture;
import pl.edu.agh.Request.PutRequest;
import pl.edu.agh.Request.TakeRequest;
import pl.edu.agh.Scheduler.Scheduler;
import pl.edu.agh.Servant.Buffer;

import java.util.List;

public class Proxy {

    private Scheduler scheduler;
    private Buffer servant;

    public Proxy(int capacity) {
        scheduler = new Scheduler();
        servant = new Buffer(capacity);
        Thread schedulerThread = new Thread(() -> {
            while(true) {
                scheduler.dispatch();
            }
        });

        schedulerThread.setDaemon(true);
        schedulerThread.start();
    }

    IFuture put(List<Integer> elements) {
        PutFuture result = new PutFuture();
        PutRequest request = new PutRequest(servant, result, elements);

        scheduler.enqueue(request);

        return result;
    }

    public IFuture take(int amount) {
        TakeFuture result = new TakeFuture();
        TakeRequest request = new TakeRequest(servant, result, amount);

        scheduler.enqueue(request);

        return result;
    }
}
