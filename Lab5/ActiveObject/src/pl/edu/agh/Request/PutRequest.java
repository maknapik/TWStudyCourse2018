package pl.edu.agh.Request;

import pl.edu.agh.Future.PutFuture;
import pl.edu.agh.Servant.Buffer;

import java.util.List;

public class PutRequest implements IRequest {

    private Buffer servant;
    private PutFuture result;
    private List<Integer> elementsToAdd;

    public PutRequest(Buffer servant, PutFuture result, List<Integer> elementsToAdd) {
        this.servant = servant;
        this.result = result;
        this.elementsToAdd = elementsToAdd;
    }

    @Override
    public void present() {
        System.out.println("Put " + elementsToAdd.size());
    }

    @Override
    public boolean guard() {
        return servant.size() + elementsToAdd.size() > servant.capacity();
    }

    @Override
    public void call() {
        servant.put(elementsToAdd);
        result.set(elementsToAdd.size());
    }
}
