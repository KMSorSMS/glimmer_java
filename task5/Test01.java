package task5;

import java.io.File;
import java.util.Scanner;

public class Test01 {

    public static void main(String[] args) throws Exception {
        File sourceFile=new File("性感荷官在线发牌.avi");
        File targetFile=new File("copy.avi");
        Scanner scan = new Scanner(System.in);
        System.out.println("请输入需要启动的线程数量(最多8个)");
        int copyNum=scan.nextInt();
        scan.close();
        if(copyNum>8||copyNum<=0){
            System.out.println("输入错误");
            return;
        }

        long copySize=sourceFile.length()/copyNum;//计算前copyNum-1个线程拷贝文件的分段大小
        int i;
        for(i=0;i<copyNum-1;i++){
            new DownloadUtilThreads(sourceFile, targetFile, copySize, copySize*i).start();
        }
        new DownloadUtilThreads(sourceFile, targetFile, copySize+(sourceFile.length()%copyNum), copySize*(i+1)).start();
        
    }
}