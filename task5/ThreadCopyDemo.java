package task5;

import java.io.*;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ThreadCopyDemo {
    public static void main(String[] args)  { 
        System.out.println("main thread start...");  //主线程开始
        //定义源文件路径和目的路径
        String srcpath = "E:\\schoollearn\\welight\\task5\\src.txt"; 
        String destpath = "E:\\schoollearn\\welight\\task5\\des.txt";

        MutilThreadCopyFile(srcpath,destpath,3);
         
        System.out.println("main thread end..."); //主线程结束
    }


    public static void MutilThreadCopyFile(String srcPath,String destPath,Integer ThreadNum){
        //参数校验
        if(ThreadNum < 1){
            return;
        }

        //文件划分
        File file = new File(srcPath);
        //获取文件长度
        long length = file.length();
        //计算子线程处理的长度
        long len = length/ThreadNum;

        // 创建并划分每个子线程所读的长度
        for (int i = 0; i < ThreadNum-1; i++) {
            SubThread subThread = new SubThread(srcPath, destPath, i * len, (i + 1) * len);
            System.out.println("第"+i+"个子线程启动");
            subThread.start();
            System.out.println("第"+i+"个子线程结束");
        }
        //把剩余的分给最后一个线程
        SubThread subThread = new SubThread(srcPath, destPath, (ThreadNum-1) * len, file.length());
        System.out.println("lastThread start...");
        subThread.start();
        System.out.println("lastThread end...");

    }
}
//自定义子线程类   继承Thread
class SubThread extends Thread{
    private String srcPath; 
    private String destPath;
    private long startIndex;
    private long endIndex;
    //构造函数：
    public SubThread(String srcPath,String destPath,long startIndex,long endIndex){
        this.srcPath = srcPath;//源文件路径
        this.destPath = destPath;//目的路径
        this.startIndex = startIndex;//开始位置
        this.endIndex = endIndex;//结束位置
    }


    @Override
    public void run() {  //子线程操作
        //随机访问类读取源文件，通过seek（）方法将指针移到固定位置，将读取的内容写入目的文件
        try {
            RandomAccessFile srcFile = new RandomAccessFile(srcPath,"r");  //源文件，只读
            RandomAccessFile destFile = new RandomAccessFile(destPath, "rw"); //目的文件，读写

            //指针移到指定位置
            srcFile.seek(startIndex);
            destFile.seek(startIndex);

            long index = startIndex; //标志读取的起始位置
            
            byte[] bytes = new byte[5]; //读取内容到数组 
            
            int  n;
            while ((n = srcFile.read(bytes)) != -1){//读到文件结尾

                index+=n; //更新读取的位置
                /**
                 * 解释一下，明显这个线程可能会多读一些后面的数据输入
                 * 但是后面线程会同样写在这里，所以不会出错。
                 */
                destFile.write(bytes,0,n); //将读的数组写入目的路径

                if(index >= endIndex){  //读到当前线程的范围结尾处
                    break;
                }
            }
            srcFile.close();//关闭流
            destFile.close();

        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}

