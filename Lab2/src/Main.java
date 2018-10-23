public class Main {
    public static void main(String[] args) throws InterruptedException {
        for(int i = 0 ; i < 100 ; i++) {
            Threads.start();
            System.out.println(Threads.x);
        }
    }
}
