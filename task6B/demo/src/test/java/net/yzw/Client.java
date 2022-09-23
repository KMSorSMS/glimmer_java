package net.yzw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Client {
    private static final int SERVER_PORT = 30000;
    private Socket socket;// 与服务器之间建立并返回的socket
    private PrintStream ps;// 输出流输出信息给服务器
    private BufferedReader brServer;// 输入流获取服务器传来的信息
    private BufferedReader keyIn;// 获得键盘输入

    public void init() {
        try {
            // 初始化代表键盘的输入流
            keyIn = new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
            // 连接到服务器端
            socket = new Socket("127.0.0.1", SERVER_PORT);// 向服务器发起请求，
            // 获取该Socket对应的输入流和输出流
            ps = new PrintStream(socket.getOutputStream());// 输出流,输出到服务器
            brServer = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));// 输入流，获取服务器信息
            String tip = "";
            // 采用循环不断地弹出对话框要求输入用户名
            while (true) {
                String userName = JOptionPane.showInputDialog(tip + "UserName:");
                // 在用户输入的用户名前后奸商协议字符串后发送
                ps.println(CrazyitProtocol.USER_ROUND + userName + CrazyitProtocol.USER_ROUND);
                // 读取服务器端的响应
                String result = brServer.readLine();
                // 如果用户名重复，则开始下次循环
                if (result.equals(CrazyitProtocol.NAME_REP)) {
                    tip = "UserName repeat, please try again";
                    continue;
                }
                // 说明用户名已经加载进服务器了，完成了初始化聊天的步骤
                if (result.equals(CrazyitProtocol.LOGIN_SUCCESS)) {
                    break;
                }
            }
        } catch (UnknownHostException ex) {
            System.out.println("can't find remote,please try again");
            closeRs();
            System.exit(1);
        } catch (IOException ex) {
            System.out.println("network err,login again");
            closeRs();
            System.exit(1);
        }
        //以该Socket对应的输入流启动ClientThread线程
        new ClientThread2(brServer).start();
    }
    private void readAndSend(){
        try {
            //不断地读取键盘输入
            String line = null;
            while((line = keyIn.readLine())!=null){
                //如果发送的信息中有冒号，且以//开头，则认为想发送私聊信息
                //注意：这里有问题，如果内容里面也有：就会出问题
                if(line.indexOf(":")>0&&line.startsWith("//")){//Returns the index within this string of the first occurrence of the specified substring.,保证第一次:
                    line = line.substring(2);
                    //提供输出流打印包装信息（有协议前后缀，目标用户名称，分割号（分割用户名与内容）,内容）
                    String content = CrazyitProtocol.PRIVATE_ROUND + line.substring(0,line.indexOf(":"))
                    +CrazyitProtocol.SPLIT_SIGN+line.substring(line.indexOf(":")+1)+CrazyitProtocol.PRIVATE_ROUND;
                    ps.println(content);
                    
                }
                else
                {
                    ps.println(CrazyitProtocol.MSG_ROUND + line + CrazyitProtocol.MSG_ROUND);
                }
            }
        } catch (IOException ex) {
            System.out.println("Connetion err,try again");
            closeRs();
            System.exit(1);
        }
    }

    private void closeRs() {
        try {
            if (keyIn != null) {
                ps.close();
            }
            if (brServer != null) {
                ps.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (socket != null) {
                keyIn.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    //客户端启动
    public static void main(String[] args) {
        var client = new Client();
        client.init();
        client.readAndSend();
    }
}
