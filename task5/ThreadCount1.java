package task5;

public class ThreadCount1 extends Thread {
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(this.getName() + ":" + i);
        }
    }

    public static void main(String[] args) {
        for (var j = 0; j < 100; j++) {
            System.out.println(Thread.currentThread().getName() + ":" + j);
            if (j == 20) {
              new ThreadCount1().start();
              new ThreadCount1().start();
            }
        }

    }

}
