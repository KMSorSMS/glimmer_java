package task5;

import java.io.*;

public class ThreadCopyWithStop02 {
    String srcPath, destPath;
    // 建立记录位置的类
    // StartPos stp = new StartPos();

    /**
     * 启动方法
     */
    public void init() {
        System.out.println("输入你想获取的资源的目标地址和文件保存地址：");
        // 将System.in对象转换成Reader对象
        var reader = new InputStreamReader(System.in);
        // 将普通的Reader包装成BufferedReader
        var br = new BufferedReader(reader);

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

    /**
     * 实现多线程复制文件
     * 
     * @param srcPath   源文件地址
     * @param destPath  目标文件位置
     * @param ThreadNum 线程数量
     */
    public void MultiThreadCopyFile(String srcPath, String destPath, int threadNum) {
        // 参数校验
        if (threadNum < 1 || srcPath == null || destPath == null) {
            return;
        }

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

    /**
     * 定义一个线程子类，实现复制的线程操作
     */
    class SubCopyThread extends Thread {
        private String srcPath;// 源文件路径
        private String destPath;// 目标文件路径
        private long startIndex;// 线程读取/写入开始位置
        private long endIndex;// 线程读取/写入结束位置
        // 创建Downloadlog对象
        private DownLoadLog log;
        // 构造函数

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

                if (fis.available() > 0) {// 则不是第一次下载
                    log = (DownLoadLog) ois.readObject();
                    startIndex = log.getStartIndex();
                    endIndex = log.getEndIndex();
                    if (log.getIsFinish()) {// 说明完成了
                        return;
                    }
                } else {
                    log = new DownLoadLog(startIndex, endIndex);
                }
                // 指针移动到指定位置
                srcFile.seek(startIndex);
                destFile.seek(startIndex);

                long index = startIndex;// 标志读取的起始位置，

                byte[] bytesRead = new byte[100];// 读取内容到字节数组
                int readNum;
                while ((readNum = srcFile.read(bytesRead)) != -1) {// 读取文件到结尾（要是会的话
                    index += readNum;

                    destFile.write(bytesRead, 0, readNum);
                    log.setStartIndex(index);
                    oos.writeObject(log);// 把对象写入
                    if (index >= endIndex) {
                        log.setFinish();// 标志对象结束
                        // //把文件删除
                        oos.close();
                        ois.close();
                        if (new File("E:\\schoollearn\\welight\\task5\\" + "log.txt").delete()) {
                            System.out.println("成功删除log" );
                        } else {
                            System.out.println("删除失败");
                        }
                        // return;
                        break;// 读到结尾就结束了

                    }

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        new ThreadCopyWithStop02().init();
    }
}

/**
 * 定义一个线程子类，来检测进度
 */

class CheckThread extends Thread {
    String srcPath, destPath;

    CheckThread(String srcPath, String destPath) {
        this.srcPath = srcPath;
        this.destPath = destPath;
    }

    public void run() {
        try {
            long src = (new File(srcPath)).length();
            long dest = (new File(destPath)).length();
            while (src > dest) {
                src = (new File(srcPath)).length();
                dest = (new File(destPath)).length();
                System.out.println("已完成" + String.format("%.2f", (((double) dest / (double) src)) * 100) + "%");
                CheckThread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
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