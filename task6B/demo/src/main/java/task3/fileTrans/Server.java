package task3.fileTrans;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.util.HashMap;

/**
 * 开启socket端口，打开线程处理客户端命令，记录客户端名称与输出流
 * 开启服务器
 */

public class Server {
    //进行命令处理的端口号
    private static final int SERVER_COMMAND = 30001;
    //记录客户端名称以及对应的输出流（向客户端输出数据的流）
    public static HashMap<String, PrintStream> clientsOut = new HashMap<>();
    public void init(){
        try(var ss = new ServerSocket()) {
            //采用死循环来不断地监听来自客户端发起的socket连接
            while(true){
                var socket = ss.accept();//如果没收到就会再此阻塞
                //将与客户端建立好的socket对象传入对应建立的服务器线程
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
        new Server().init();
    }
    
}
