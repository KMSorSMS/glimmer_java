package task5;

public class ThreadCount {
    public static void main(String[] args) {
        Runnable target = ()-> {
            for(int i=0;i<100;i++){
                System.out.println(Thread.currentThread().getName()+":"+i);
            }
        } ;
        for(var j=0;j<100;j++)
        {
            System.out.println(Thread.currentThread().getName()+":"+j);
            if(j==20)
            {
                new Thread(target,"线程1").start();
                new Thread(target,"线程2").start();
            }
        }
    }
}
