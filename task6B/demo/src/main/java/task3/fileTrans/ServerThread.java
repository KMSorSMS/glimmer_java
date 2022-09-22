package task3.fileTrans;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread {
    //所处理的socket连接
    private Socket ss;
    //服务器与客户端socket连接后的输入字节流
    private BufferedInputStream serverRead;
    public ServerThread(Socket ss){
        this.ss = ss;
        try{
        this.serverRead = new BufferedInputStream(ss.getInputStream());
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
    //获取用户名
}
