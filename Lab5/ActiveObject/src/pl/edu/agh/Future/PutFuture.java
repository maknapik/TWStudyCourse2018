package pl.edu.agh.Future;

public class PutFuture implements IFuture {

    private int amount;

    @Override
    public boolean ready() {
        return amount > 0;
    }

    @Override
    public synchronized Object take() {
        return amount;
    }

    @Override
    public synchronized void set(Object o) {
        amount = (int) o;
    }
}
