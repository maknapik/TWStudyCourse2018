import static java.lang.Thread.sleep;

public class Philosopher {

    private boolean isThinking;
    private boolean isEating;

    public boolean isEating() {
        return isEating;
    }

    public void eat() throws InterruptedException {
        isThinking = false;
        isEating = true;
        System.out.println("Eating");
    }

    public void think() throws InterruptedException {
        isEating = false;
        isThinking = true;
        System.out.println("Thinking");
    }
}