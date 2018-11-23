package pl.edu.agh.ActivationQueue;

import pl.edu.agh.Request.IRequest;
import pl.edu.agh.Request.PutRequest;
import pl.edu.agh.Request.TakeRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ActivationQueue {

    private Lock lock;
    private Condition queueEmpty;

    private List<IRequest> putRequests;
    private List<IRequest> takeRequests;

    private RequestType lastRequestType;

    public ActivationQueue() {
        lock = new ReentrantLock();
        queueEmpty = lock.newCondition();

        putRequests = new ArrayList<>();
        takeRequests = new ArrayList<>();

        lastRequestType = RequestType.PUT;
    }

    public synchronized void enqueue(IRequest request) {
        if(request instanceof TakeRequest) {
            takeRequests.add(request);
        } else if (request instanceof PutRequest) {
            putRequests.add(request);
        } else {
            throw new IllegalArgumentException();
        }

        notify();
    }

    public synchronized void enqueueBack(IRequest request) {
        if(request instanceof TakeRequest) {
            takeRequests.add(0, request);
        } else {
            putRequests.add(0, request);
        }

        notify();
    }

    public synchronized IRequest dequeue() {
        while(isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(!takeRequests.isEmpty() && putRequests.isEmpty()) {
            return takeRequests.remove(0);
        }

        if(!putRequests.isEmpty() && takeRequests.isEmpty()) {
            return putRequests.remove(0);
        }

        if(lastRequestType == RequestType.PUT) {
            if(!takeRequests.isEmpty()) {
                lastRequestType = RequestType.TAKE;
                return takeRequests.remove(0);
            }
        } else {
            if(!putRequests.isEmpty()) {
                lastRequestType = RequestType.PUT;
                return putRequests.remove(0);
            }
        }
        return null;
    }

    private boolean isEmpty() {
        return takeRequests.isEmpty() && putRequests.isEmpty();
    }
}
