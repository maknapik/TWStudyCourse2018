package pl.edu.agh.ActivationQueue;

import pl.edu.agh.Request.IRequest;
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

    public void enqueue(IRequest request) {
        lock.lock();

        if(request instanceof TakeRequest) {
            takeRequests.add(request);
        } else {
            putRequests.add(request);
        }

        queueEmpty.signal();

        lock.unlock();
    }

    public void enqueueBack(IRequest request) {
        lock.lock();

        if(request instanceof TakeRequest) {
            takeRequests.add(request);

            IRequest requestToMove = takeRequests.remove(0);
            takeRequests.add(requestToMove);
        } else {
            putRequests.add(request);

            IRequest requestToMove = putRequests.remove(0);
            putRequests.add(requestToMove);
        }

        queueEmpty.signal();

        lock.unlock();
    }

    public IRequest dequeue() {
        lock.lock();

        while(isEmpty()) {
            try {
                queueEmpty.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        lock.unlock();

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
