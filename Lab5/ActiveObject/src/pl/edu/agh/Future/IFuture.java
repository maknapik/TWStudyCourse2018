package pl.edu.agh.Future;

public interface IFuture {

    boolean ready();

    Object take();

    void set(Object o);
}
