public class Semaphore {
    private boolean state;

    public synchronized void take() {
        while(state) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.state = true;
    }

    public synchronized void release() {
        this.state = false;
        notify();
    }
}
