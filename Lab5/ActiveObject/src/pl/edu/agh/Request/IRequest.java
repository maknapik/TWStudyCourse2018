package pl.edu.agh.Request;

public interface IRequest {

    boolean guard();

    void call();

    void present();
}
