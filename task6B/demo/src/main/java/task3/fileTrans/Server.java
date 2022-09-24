package task3.fileTrans;

import java.net.ServerSocket;

/**
 * 开启socket端口，打开线程处理客户端命令，记录客户端名称与输出流
 * 开启服务器
 */

public class Server {
    //进行命令处理的端口号
    private static int SERVER_COMMAND = 30001;
    public void init(){
        try(var ss = new ServerSocket(SERVER_COMMAND)) {
            //采用死循环来不断地监听来自客户端发起的socket连接
            while(true){
                System.out.println("服务器已打开 端口:"+SERVER_COMMAND+"等待连接中...");
                var socket = ss.accept();//如果没收到就会再此阻塞
                //将与客户端建立好的socket对象传入对应建立的服务器线程
                System.out.println("客户端已连接");
                new ServerThread(socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 开启服务器线程
     * @param args
     */
    public static void main(String[] args) {
        if(args.length==2){
            SERVER_COMMAND = Integer.parseInt(args[0]);
            ServerThread.SERVER_DATA = Integer.parseInt(args[1]);
        }
        new Server().init();
    }
    
}
