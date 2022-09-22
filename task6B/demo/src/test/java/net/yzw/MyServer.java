package net.yzw;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyServer {
    // 定义保存所有的Socket的ArrayList，并将其包装为线程安全的
    public static List<Socket> socketList = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws IOException {

        // 服务器端口号为30000，主机名为本地主机
        var ss = new ServerSocket(30000);
        try{
        while (true) {
            // 此行代码会阻塞，将一直等待别人连接
            Socket s = ss.accept();
            socketList.add(s);
            // 每当客户端连接后启动一个ServerThread线程为客户端服务
            new Thread(new ServerThread(s)).start();
            System.out.println("I'm working");
        }
        }
        finally{
        ss.close();
        }
    }
}
