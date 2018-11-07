import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class PC2 {
    static final int N = 10;
    private int do_wstawienia;
    private int do_pobrania;
    private List<State> state;

    private ReentrantLock lock = new ReentrantLock();
    private Condition zablokowany = lock.newCondition();

    PC2() {
        do_wstawienia = 0;
        do_pobrania = 0;

        state = new ArrayList<>();
        for(int i = 0 ; i < N ; i++) {
            state.add(State.PUSTY);
        }
    }

    int getN() {
        return N;
    }

    int wstaw_pocz() {
        if(state.get(do_wstawienia) != State.PUSTY) {
            System.out.println("Stracono porcje: " + do_wstawienia);
            return N;
        } else {
            state.set(do_wstawienia, State.ZAJETY);
            int i = do_wstawienia;
            do_wstawienia = (do_wstawienia + 1) % N;
            System.out.println("Rozpoczeto wstawianie porcji na: " + do_wstawienia);
            return i;
        }
    }

    void wstaw_koniec(int i) {
        lock.lock();
        state.set(i, State.PELNY);
        if(i == do_pobrania) {
            zablokowany.signal();
        }
        System.out.println("Zakonczono wstawianie porcji na: " + i);
        lock.unlock();
    }

    int pobierz_pocz() throws InterruptedException {
        lock.lock();
        if(state.get(do_pobrania) != State.PELNY) {
            System.out.println("Czekanie na pobieranie porcji na: " + do_pobrania);
            zablokowany.await();
        }
        int i = do_pobrania;
        do_pobrania = (do_pobrania + 1) % N;
        System.out.println("    Rozpoczeto pobieranie porcji na: " + i);
        lock.unlock();
        return i;
    }

    void pobierz_koniec(int i) {
        state.set(i, State.PUSTY);
        System.out.println("    Zakonczono pobieranie porcji na: " + i);
    }
}
