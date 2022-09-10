// package task5;

// import java.io.*;

// public class ThreadCopy {
//     String srcPath, destPath;
//     //建立记录位置的类
//     // StartPos stp = new StartPos();

//     /**
//      * 启动方法
//      */
//     public void init() {
//         System.out.println("输入你想获取的资源的目标地址和文件保存地址：");
//         // 将System.in对象转换成Reader对象
//         var reader = new InputStreamReader(System.in);
//         // 将普通的Reader包装成BufferedReader
//         var br = new BufferedReader(reader);

//         try {
//             // 定义源文件路径和目的路径
//             srcPath = br.readLine();
//             destPath = br.readLine();
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         this.MultiThreadCopyFile(srcPath, destPath, 3);
//         // try{
//         // Thread.sleep(1);
//         // }
//         // catch(Exception e){
//         // e.printStackTrace();
//         // }
//         // 通过check线程来检测进度
//         new CheckThread(srcPath, destPath).start();
//     }

//     /**
//      * 实现多线程复制文件
//      * 
//      * @param srcPath   源文件地址
//      * @param destPath  目标文件位置
//      * @param ThreadNum 线程数量
//      */
//     public void MultiThreadCopyFile(String srcPath, String destPath, int threadNum) {
//         // 参数校验
//         if (threadNum < 1 || srcPath == null || destPath == null) {
//             return;
//         }
        
//         // 文件划分
//         File file = new File(srcPath);
//         // 获取文件长度
//         long length = file.length();
//         // 计算子线程处理的长度
//         long len = length / threadNum;
//         // 创建并划分每个子线程所读的长度
//         for (int i = 0; i < threadNum - 1; i++) {
//             new SubCopyThread(srcPath, destPath, i * len, (i + 1) * len).start();
//             System.out.println("线程" + i + "开始工作");
//         }
//         // 剩余的分给最后一个线程
//         new SubCopyThread(srcPath, destPath, (threadNum - 1) * len, file.length()).start();
//         System.out.println("线程" + (threadNum - 1) + "开始工作");
//     }

//     /**
//      * 定义一个线程子类，来检测进度
//      */

//     class CheckThread extends Thread{
//         String srcPath, destPath;

//         CheckThread(String srcPath, String destPath) {
//             this.srcPath = srcPath;
//             this.destPath = destPath;
//         }

//         public void run() {
//             try {
//                 long src = (new File(srcPath)).length();
//                 long dest = (new File(destPath)).length();
//                 while (src > dest) {
//                     src = (new File(srcPath)).length();
//                     dest = (new File(destPath)).length();
//                     System.out.println("已完成" + String.format("%.2f", (((double) dest / (double) src)) * 100) + "%");
//                     CheckThread.sleep(100);
//                 }
//             } catch (Exception e) {
//                 e.printStackTrace();
//             }
//         }
//     }
// // /**
// //  * 定义一个可序列化类，该类用来存储各个起始点线程的当前复制位置
// //  */
// // class StartPos implements Serializable{
// //     HashMap<Long,Long> pos = new HashMap<Long,Long>();
// // }


//     /**
//      * 定义一个线程子类，实现复制的线程操作
//      */
//     class SubCopyThread extends Thread{
//         private String srcPath;// 源文件路径
//         private String destPath;// 目标文件路径
//         private long startIndex;// 线程读取/写入开始位置
//         private long endIndex;// 线程读取/写入结束位置
//         // 构造函数

//         public SubCopyThread(String srcPath, String destPath, long startIndex, long endIndex) {
//             this.srcPath = srcPath;// 源文件路径
//             this.destPath = destPath;
//             this.startIndex = startIndex;
//             this.endIndex = endIndex;
//         }

//         public String getSrcPath() {
//             return srcPath;
//         }

//         public String getDestPath() {
//             return destPath;
//         }

//         @Override
//         public void run() {
//             if (startIndex >= endIndex) {
//                 return;
//             }
//             // 子线程操作
//             // 由RandomAccessFile读取/写入，StartIndex是指针，通过seek()固定位置
//             try (RandomAccessFile srcFile = new RandomAccessFile(srcPath, "r"); // 读模式，读取文件
//                     RandomAccessFile destFile = new RandomAccessFile(destPath, "rw"); // 目标存储文件写入
//             ) {
//                 // 指针移动到指定位置
//                 srcFile.seek(startIndex);
//                 destFile.seek(startIndex);
                

//                 long index = startIndex;// 标志读取的起始位置，

//                 byte[] bytesRead = new byte[100];// 读取内容到字节数组
//                 int readNum;
//                 while ((readNum = srcFile.read(bytesRead)) != -1) {// 读取文件到结尾（要是会的话
//                     index += readNum;
                    
//                     destFile.write(bytesRead, 0, readNum);
                   
//                     if (index >= endIndex) {
//                         break;// 读到结尾就结束了
//                     }

//                 }
//             } catch (Exception ex) {
//                 ex.printStackTrace();
//             }
//         }

//     }

//     public static void main(String[] args) {
//         new ThreadCopy().init();
//     }
// }
