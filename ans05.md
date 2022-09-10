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

改进这些程序，使得再次执行BuyTest类时能出现如下结果：（最好能使用2种方法） ![2-2.png](https://join.glimmer.space/File/image/2-2.png)

一、通过同步代码块

![image-20220909092304373](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220909092304373.png)

![image-20220909092350929](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220909092350929.png)

二、通过同步方法

记录一下不小心出的问题：

![image-20220909092840576](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220909092840576.png)

![image-20220909092850016](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220909092850016.png)

本以为这个逻辑很对，唉，大意了![image-20220909092929284](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220909092929284.png)

所以我要用同步方法来锁的话需要改动一下购买的方式

![image-20220909093905480](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220909093905480.png)

成功了捏。。。😋![image-20220909093938926](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220909093938926.png)

![image-20220909093948555](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220909093948555.png)









![image-20220909101729795](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220909101729795.png)





![image-20220909101709970](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220909101709970.png)

![image-20220909103659018](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220909103659018.png)

这里有个问题困扰我许久，为什么在两次补货之间一定要让main线程等待，我把main线程的优先级已经调到了最低，而且只有一个glimmer对象作为监视器，当佬2在等待的过程中，补了一次货，然后调用了notify，那么佬2是对这个监视器处于等待状态的高优先级的线程，但是如果第二次补货前不加sleep，那么佬2线程仍然无法执行，我十分不理解。





因为main线程里面notify也是唤醒佬2线程，那么佬2线程只是进入就绪状态，按照优先级运行而已，那么由于main线程继续执行，两个补货接的很快，所以就绪后运行这个过程中main的语句又执行了

-------------------------------

![image-20220910121456172](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220910121456172.png)

![image-20220910121543450](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220910121543450.png)

package task5;

import java.io.*;

public class ThreadCopyWithStop02 {
    String srcPath, destPath;
    // 建立记录位置的类
    // StartPos stp = new StartPos();

​    /**
​     * 启动方法
​          */
​        public void init() {
​        System.out.println("输入你想获取的资源的目标地址和文件保存地址：");
​        // 将System.in对象转换成Reader对象
​        var reader = new InputStreamReader(System.in);
​        // 将普通的Reader包装成BufferedReader
​        var br = new BufferedReader(reader);

        try {
            // 定义源文件路径和目的路径
            srcPath = br.readLine();
            destPath = br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.MultiThreadCopyFile(srcPath, destPath, 3);
        // 通过check线程来检测进度
        new CheckThread(srcPath, destPath).start();
        }

​    /**
​     * 实现多线程复制文件
​          *
​          * @param srcPath   源文件地址
​               * @param destPath  目标文件位置
​               * @param ThreadNum 线程数量
​                    */
​                public void MultiThreadCopyFile(String srcPath, String destPath, int threadNum) {
​        // 参数校验
​        if (threadNum < 1 || srcPath == null || destPath == null) {
​                    return;
​        }

        // 文件划分
        File file = new File(srcPath);
        // 获取文件长度
        long length = file.length();
        // 计算子线程处理的长度
        long len = length / threadNum;
        // 创建并划分每个子线程所读的长度
        for (int i = 0; i < threadNum - 1; i++) {
            new SubCopyThread(srcPath, destPath, i * len, (i + 1) * len).start();
            System.out.println("线程" + i + "开始工作");
        }
        // 剩余的分给最后一个线程
        new SubCopyThread(srcPath, destPath, (threadNum - 1) * len, file.length()).start();
        System.out.println("线程" + (threadNum - 1) + "开始工作");
        }

​    /**
​     * 定义一个线程子类，实现复制的线程操作
​          */
​        class SubCopyThread extends Thread {
​        private String srcPath;// 源文件路径
​        private String destPath;// 目标文件路径
​        private long startIndex;// 线程读取/写入开始位置
​        private long endIndex;// 线程读取/写入结束位置
​        // 创建Downloadlog对象
​        private DownLoadLog log;
​        // 构造函数

        public SubCopyThread(String srcPath, String destPath, long startIndex, long endIndex) {
            this.srcPath = srcPath;// 源文件路径
            this.destPath = destPath;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
    
        }
    
        public String getSrcPath() {
            return srcPath;
        }
    
        public String getDestPath() {
            return destPath;
        }
    
        @Override
        public void run() {
            if (startIndex >= endIndex) {
                return;
            }
            // 子线程操作
            // 由RandomAccessFile读取/写入，StartIndex是指针，通过seek()固定位置
            try (RandomAccessFile srcFile = new RandomAccessFile(srcPath, "r"); // 读模式，读取文件
                    RandomAccessFile destFile = new RandomAccessFile(destPath, "rw"); // 目标存储文件写入
                    var fos = new FileOutputStream("E:\\schoollearn\\welight\\task5\\" + "log.txt");
                    var fis = new FileInputStream("E:\\schoollearn\\welight\\task5\\" + "log.txt");
                    var oos = new ObjectOutputStream(fos); // 打开写入的对象
                    var ois = new ObjectInputStream(fis);// 打开要读入的对象
            ) {
    
        ​        if (fis.available() > 0) {// 则不是第一次下载
        ​            log = (DownLoadLog) ois.readObject();
        ​            startIndex = log.getStartIndex();
        ​            endIndex = log.getEndIndex();
        ​            if (log.getIsFinish()) {// 说明完成了
        ​                return;
        ​            }
        ​        } else {
        ​            log = new DownLoadLog(startIndex, endIndex);
        ​        }
        ​        // 指针移动到指定位置
        ​        srcFile.seek(startIndex);
        ​        destFile.seek(startIndex);
    
        ​        long index = startIndex;// 标志读取的起始位置，
    
        ​        byte[] bytesRead = new byte[100];// 读取内容到字节数组
        ​        int readNum;
        ​        while ((readNum = srcFile.read(bytesRead)) != -1) {// 读取文件到结尾（要是会的话
        ​            index += readNum;
    
        ​            destFile.write(bytesRead, 0, readNum);
        ​            log.setStartIndex(index);
        ​            oos.writeObject(log);// 把对象写入
        ​            if (index >= endIndex) {
        ​                log.setFinish();// 标志对象结束
        ​                // //把文件删除
        ​                oos.close();
        ​                ois.close();
        ​                if (new File("E:\\schoollearn\\welight\\task5\\" + "log.txt").delete()) {
        ​                    System.out.println("成功删除log" );
        ​                } else {
        ​                    System.out.println("删除失败");
        ​                }
        ​                // return;
        ​                break;// 读到结尾就结束了
    
        ​            }
    
        ​        }
        ​    } catch (Exception ex) {
        ​        ex.printStackTrace();
        ​    }
        }

​    }

​    public static void main(String[] args) {
​        new ThreadCopyWithStop02().init();
​    }
}

/**
 * 定义一个线程子类，来检测进度
 */

class CheckThread extends Thread {
    String srcPath, destPath;

​    CheckThread(String srcPath, String destPath) {
​        this.srcPath = srcPath;
​        this.destPath = destPath;
​    }

​    public void run() {
​        try {
​            long src = (new File(srcPath)).length();
​            long dest = (new File(destPath)).length();
​            while (src > dest) {
​                src = (new File(srcPath)).length();
​                dest = (new File(destPath)).length();
​                System.out.println("已完成" + String.format("%.2f", (((double) dest / (double) src)) * 100) + "%");
​                CheckThread.sleep(100);
​            }
​        } catch (Exception e) {
​            e.printStackTrace();
​        }
​    }
}

/**
 * 定义一个可序列化类，该类用来存储各个起始点线程的当前复制位置
 */
  class DownLoadLog implements Serializable {
    private long startIndex, endIndex;
    private boolean isFinish = false;

    public DownLoadLog(long startIndex, long endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public void setFinish() {
        this.isFinish = true;
    }

    public boolean getIsFinish() {
        return isFinish;
    }

    public void setStartIndex(long startIndex) {
        this.startIndex = startIndex;
    }

    public void setEndIndex(long endIndex) {
        this.endIndex = endIndex;
    }

    public long getStartIndex() {
        return startIndex;
    }

    public long getEndIndex() {
        return endIndex;
    }
  }

// //对应的操作类
// class LogOpreator {

// private DownLoadLog d;
// private ObjectInputStream obi;
// private ObjectOutputStream obo;
// private File file;

// public LogOpreator(File file) {
// this.file = file;
// }

// //写入对象到文件
// public void write(long startIndex,long endIndex,boolean isFinish){
// d=new DownLoadLog(startIndex, endIndex);
// try {
// obo=new ObjectOutputStream(new FileOutputStream(file));
// obo.writeObject(d);
// obo.close();
// } catch (FileNotFoundException e) {

// e.printStackTrace();
// } catch (IOException e) {

// e.printStackTrace();
// }
// }

// //取出point
// public long getStart(){
// try {
// obi=new ObjectInputStream(new FileInputStream(file));
// Object logOb = obi.readObject();
// DownLoadLog log=(DownLoadLog)logOb;
// obi.close();
// return log.getStartIndex();
// } catch (Exception e) {

// e.printStackTrace();
// }

// return 0;
// }
// public boolean readIsFinish(){
// try {
// obi=new ObjectInputStream(new FileInputStream(file));
// Object logOb = obi.readObject();
// DownLoadLog log=(DownLoadLog)logOb;
// obi.close();
// return log.getIsFinish();
// } catch (Exception e) {

// e.printStackTrace();
// }
// return false;
// }

// }

![image-20220910122659726](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220910122659726.png)
