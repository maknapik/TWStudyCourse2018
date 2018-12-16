import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String args[]) {
//        PC2V2();
//        PC2V2V2();
        PC();
    }

    private static void PC() {
        final PC pc = new PC();
        List<Thread> threads = new ArrayList<>();

        int range = (pc.getM() - 1) + 1;
        for(int i = 0 ; i < 100 ; i++) {
            threads.add(new Thread(() -> {
                try
                {
                    while (true) {
                        pc.produce((int) (Math.random() * range) + 1);
                    }
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }));

            threads.add(new Thread(() -> {
                try {
                    while (true) {
                        pc.consume((int) (Math.random() * range) + 1);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }

    public static void PC2() {
        final PC2 pc = new PC2();
        List<Thread> threads = new ArrayList<>();

        threads.add(new Thread(() -> {
            while (true) {
                try {
                    int x = pc.pobierz_pocz();
                    pc.pobierz_koniec(x);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }));

        for(int i = 0 ; i < 10 ; i++) {
            threads.add(new Thread(() -> {
                    while (true) {
                        int x = pc.wstaw_pocz();
                        if(x < pc.getN()) {
                            pc.wstaw_koniec(x);
                        }
                    }
            }));
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }

    public static void PC2V2() {
        final PC2V2 pc = new PC2V2(10);
        List<Thread> threads = new ArrayList<>();

        for(int i = 0 ; i < 5 ; i++) {
            threads.add(new Thread(() -> {
                while (true) {
                    int x = 0;
                    try {
                        x = pc.wstaw_pocz();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pc.wstaw_koniec(x);
                }
            }));

            threads.add(new Thread(() -> {
                while (true) {
                    try {
                        int x = pc.pobierz_pocz();
                        pc.pobierz_koniec(x);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }));
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }

    public static void PC2V2V2() {
        final PC2V2V2 pc = new PC2V2V2(10);
        List<Thread> threads = new ArrayList<>();

        for(int i = 0 ; i < 5 ; i++) {
            threads.add(new Thread(() -> {
                while (true) {
                    int x = 0;
                    try {
                        x = pc.wstaw_pocz();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pc.wstaw_koniec(x);
                }
            }));

            threads.add(new Thread(() -> {
                while (true) {
                    try {
                        int x = pc.pobierz_pocz();
                        pc.pobierz_koniec(x);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }));
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }
}
