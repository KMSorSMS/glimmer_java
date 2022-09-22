package net.yzw;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;

public class Server {
    private static final int SERVER_PORT = 30000;
    //使用CrazyitMap对象来保存每个客户端名字和对应的输出流之间的关系
    public static CrazyitMap<String, PrintStream> clients = new CrazyitMap<>();
    public void init()
    {
        try(//建立监听的ServerSocket
            var ss = new ServerSocket(SERVER_PORT);
        ){
            //采用死循环来不断地监听来自客户端的请求
            while(true){
                var socket = ss.accept();//在这里会发生阻塞，等待连接
                new ServerThread2(socket).start();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    /**
     * 开启服务器服务
     * @param args
     */
    public static void main(String[] args) {
        var server = new Server();
        server.init();
    }
}
