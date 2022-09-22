package net.yzw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerThread implements Runnable {
    // 定义当前线程所处理的Socket
    Socket s = null;
    // 该线程所处理的Socket对应的输入流
    BufferedReader br = null;

    public ServerThread(Socket s) throws IOException {
        this.s = s;
        // 初始化该Socket对应的输入流
        // 站在服务器的角度，就是接收客户端的数据
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    public void run(){
        try{
            String content = null;
            //采用循环不断的从Socket中读取客户端发送过来的数据
            while((content = readFromClient())!=null){
                //遍历socketList中的每一个Socket
                //将读到的内容向每个Socket发送一次
                for(var s: MyServer.socketList){
                    var ps = new PrintStream(s.getOutputStream());//打开与各个客户端相关的socket的输出流
                    ps.println(content);
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    // 定义读取客户端数据的工具方法
    private String readFromClient() {
        try {
            return br.readLine();
        }
        // 如果捕获异常，则表明该socket对应的客户端已经关闭
        catch (IOException e) {
            // 删除该Socket
            MyServer.socketList.remove(s);
        }
        return null;
    }
}
