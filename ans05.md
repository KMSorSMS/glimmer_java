task1

---------------------------------

#### 1、关于多线程：

线程是在进程之下的概念，而进程是指程序在内存里运行时的状态，也就是在运行过程中的程序。

##### 进程：有自己独立资源

##### 并发：同个时刻只有一条指令进行，但是多个进程指令被同一个cpu快速轮换执行

##### 并行：同一时刻，多条指令在多个处理器上同时执行

##### 线程：是进程的执行单元，就像进程在操作系统的地位一样。当进程被初始化的时候主线程就被建立了（一般来说是main)。进程可以拥有多个线程。

## *ps:线程会共享同一进程的全部资源，自己仅有堆栈、程序计数器、局部变量----这为同步控制操作有了铺垫*

### 多线程：是扩展了多进程的概念，s.t.同一个进程可以同时并发处理多个任务。（所以线程也被称作轻量级进程）。

#### 2、了解一下JAVA中实现多线程的几种方法。

### Java多线程实现的方式有四种

- **1.继承Thread类，重写run方法**

  ![image-20220908113414373](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220908113414373.png)

  ![image-20220908113602144](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220908113602144.png)

  ![image-20220908113754415](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220908113754415.png)

- **2.实现Runnable接口（函数式接口），重写run方法，实现Runnable接口的实现类的实例对象作为Thread构造函数的target**

  ![image-20220908114011609](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220908114011609.png)

- **3.通过Callable和FutureTask创建线程**

  ![image-20220908121418158](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220908121418158.png)

  ![image-20220908121914498](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220908121914498.png)

- **4.通过线程池创建线程**

#### 3、首先再试试以Lambda表达式的形式实现一下Runnable接口吧！

package task5;



public class ThreadCount {

  public static void main(String[] *args*) {

​    Runnable target = ()-> {

​      for(int i=0;i<100;i++){

​        System.out.println(Thread.currentThread().getName()+":"+i);

​      }

​    } ;

​    for(var j=0;j<100;j++)

​    {

​      System.out.println(Thread.currentThread().getName()+":"+j);

​      if(j==20)

​      {

​        new Thread(target,"线程1").start();

​        new Thread(target,"线程2").start();

​      }

​    }

  }

}

-----

package task5;



public class ThreadCount1 extends Thread {

  public void run() {

​    for (int i = 0; i < 100; i++) {

​      System.out.println(this.getName() + ":" + i);

​    }

  }



  public static void main(String[] *args*) {

​    for (var j = 0; j < 100; j++) {

​      System.out.println(Thread.currentThread().getName() + ":" + j);

​      if (j == 20) {

​       new ThreadCount1().start();

​       new ThreadCount1().start();

​      }

​    }



  }



}

![image-20220908115833167](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220908115833167.png)

------

- ##### 调用start方法开启线程和直接调用run方法有什么区别？

  完全不一样，run是执行thread的run方法，就是在执行普通类的一个方法，然而start才是开启了一个新的线程，然后在这个新的线程里面执行run的内容

- Thread-1和Thread-2都会数100下吗？为什么 i 值最后不是200？

  确实。都是i不会是200，首先是这里i<100就限制了（出题人不该这样问），而且哪怕不存在这个限制，i也不会连续累加（通过thread继承方式是不可能做得到的），首先是我这里i是局部变量不会被共享，哪怕i成为了实例变量，（当然静态变量那就可以，你赢了），因为用thread继承方式创建的子类的两个实例，所以这里两个实例不一样，在线程里面是不会共享的。

  <u>BUT，如果是runnable并且i是实例变量的话，你会看见i是在连续增加的，因为虽然建了两个不一样的thread实例，但是它们的target都是同一个runnable对象，那么这个对象的实例变量i就是共享的，那么就会连续的变化</u>

  

- 对比一下你使用的2种方法的优劣势。

  既然讲的这么清楚了，也就是看对于执行过程中的数据交互的需求了，优劣就是看情况而言的了，不过还有就是继承thread的方式会无法再去继承其他类，而实现runnable的接口却还能再去实现其它接口，所以尽量都用runnable吧

  

---------------------

task2

----------

- 谈谈为什么会出现这样的结果。

  当然是因为没有加线程锁了，一个线程刚过了判定条件还没有修改共有数据--抱枕剩余量的时候切换到了另外一个线程，那么自然就会导致另外一个线程也能去取抱枕，所以就出错了