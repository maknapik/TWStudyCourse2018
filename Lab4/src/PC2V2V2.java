import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class PC2V2V2 {

    private Queue<Integer> puste;
    private Queue<Integer> pelne;
    private ReentrantLock lock = new ReentrantLock();
    private Condition czek_prod = lock.newCondition();
    private Condition czek_kons = lock.newCondition();

    PC2V2V2(int M){
        puste = new LinkedList<>();
        pelne = new LinkedList<>();

        for(int i = 0 ; i < M ; i++) {
            do_kolejki(QueueType.PUSTE, i);
        }
    }

    private void do_kolejki(QueueType queueType, int pos) {
        if(queueType == QueueType.PELNE) {
            pelne.offer(pos);
        } else {
            puste.offer(pos);
        }
    }

    private int z_kolejki(QueueType queueType) {
        if(queueType == QueueType.PELNE) {
            return pelne.poll();
        } else {
            return puste.poll();
        }
    }

    private boolean pusta_kol(QueueType queueType) {
        if(queueType == QueueType.PELNE) {
            return pelne.isEmpty();
        } else {
            return puste.isEmpty();
        }
    }

    private void pokaz_kol(QueueType queueType) {
        if(queueType == QueueType.PELNE) {
            System.out.println(pelne);
        } else {
            System.out.println(puste);
        }
    }

    int wstaw_pocz() throws InterruptedException {
        lock.lock();
        while (pusta_kol(QueueType.PUSTE)) {
            czek_prod.await();
        }
        int i = z_kolejki(QueueType.PUSTE);
        pokaz_kol(QueueType.PELNE);
        pokaz_kol(QueueType.PUSTE);
        do_kolejki(QueueType.PELNE, i);
        System.out.println("Rozpoczecie wstawiania na: " + i);
        lock.unlock();
        return i;
    }

    void wstaw_koniec(int i) {
        lock.lock();
        System.out.println("Zakonczenie wstawiania na: " + i);
        pokaz_kol(QueueType.PELNE);
        pokaz_kol(QueueType.PUSTE);
        czek_kons.signal();
        lock.unlock();
    }

    int pobierz_pocz() throws InterruptedException {
        lock.lock();
        while (pusta_kol(QueueType.PELNE)) {
            czek_kons.await();
        }
        System.out.println();
        pokaz_kol(QueueType.PELNE);
        pokaz_kol(QueueType.PUSTE);
        int i = z_kolejki(QueueType.PELNE);
        System.out.println("    Rozpoczecie pobierania na: " + i);
        lock.unlock();
        return i;
    }

    void pobierz_koniec(int i) {
        lock.lock();
        do_kolejki(QueueType.PUSTE, i);
        System.out.println("    Zakonczenie pobierania na: " + i);
        pokaz_kol(QueueType.PELNE);
        pokaz_kol(QueueType.PUSTE);
        czek_prod.signal();
        lock.unlock();
    }

}