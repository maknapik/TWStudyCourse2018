package pl.edu.agh.Future;

import java.util.List;

public class TakeFuture implements IFuture {

    private List<Integer> elements;

    @Override
    public boolean ready() {
        return elements != null;
    }

    @Override
    public synchronized Object take() {
        return elements;
    }

    @Override
    public synchronized void set(Object elements) {
        this.elements = (List<Integer>) elements;
    }
}
