package pl.edu.agh.Scheduler;

import pl.edu.agh.ActivationQueue.ActivationQueue;
import pl.edu.agh.Request.IRequest;

public class Scheduler {

    private ActivationQueue queue;

    public Scheduler() {
        queue = new ActivationQueue();
    }

    public void enqueue(IRequest request) {
        queue.enqueue(request);
    }

    public void dispatch() {
        IRequest request = queue.dequeue();

        if(request == null) {
            return;
        }

        if(!request.guard()) {
            request.call();
        } else {
            queue.enqueueBack(request);
        }
    }
}
