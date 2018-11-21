package pl.edu.agh.Request;

import pl.edu.agh.Future.TakeFuture;
import pl.edu.agh.Servant.Buffer;

public class TakeRequest implements IRequest {

    private Buffer servant;
    private TakeFuture result;
    private int amount;

    public TakeRequest(Buffer servant, TakeFuture result, int amount) {
        this.servant = servant;
        this.result = result;
        this.amount = amount;
    }

    @Override
    public boolean guard() {
        return servant.size() < amount;
    }

    @Override
    public void call() {
        result.set(servant.take(amount));
    }
}
