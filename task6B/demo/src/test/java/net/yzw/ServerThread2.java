package net.yzw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerThread2 extends Thread {
    private Socket socket;// 该线程负责的与客户端对应的Socket
    BufferedReader br = null;// 读取客户端发送的数据，对应的输入数据流
    PrintStream ps = null;// 向客户端发送数据对应的输出数据流，printStream
    // 定义一个构造器，用于接收一个Socket来创建ServerThread线程

    public ServerThread2(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // 获取该Socket对应的输入流,即来自于对应客户端的消息
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 获取该Socket对应的输出流，即向该客户端输出消息
            ps = new PrintStream(socket.getOutputStream());
            String line = null;
            while ((line = br.readLine()) != null) {
                // 如果读到的行以CrazyitProtocol.User_ROUND 开始，并以其结束
                // 则可以确定读到的是用户登录的用户名
                if (line.startsWith(CrazyitProtocol.USER_ROUND) && line.endsWith(CrazyitProtocol.USER_ROUND)) {
                    // 得到真实消息,通过自己实现的工具方法
                    String userName = getRealMsg(line);
                    // 如果用户名重复,向客户端传输信息，表示重复了
                    if (Server.clients.map.containsKey(userName)) {// 调用CrazyitMap实例的map<key username,value
                                                                   // outputStream>实例变量来找是否有key
                        System.out.println("重复");
                        ps.println(CrazyitProtocol.NAME_REP);// 向对应客户端发送重复用户名信息
                    } else {// 成功登入
                        System.out.println("成功");
                        ps.println(CrazyitProtocol.LOGIN_SUCCESS);// 向客户端发送成功登入标志信息
                        Server.clients.put(userName, ps);// 存入CrazyitMap实例,记录用户名与对应的输出流
                    }

                }
                // 如果读到的行以CrazyitProtocol.PRIVATE_ROUND开始，并以其结束
                // 则可以确定是私聊信息，私聊信息只向特定的输出流发送
                else if (line.startsWith(CrazyitProtocol.PRIVATE_ROUND)
                        && line.endsWith(CrazyitProtocol.PRIVATE_ROUND)) {
                    // 得到真实信息
                    String userAndMsg = getRealMsg(line);
                    // 以SPLIT_SIGN分割字符串，前半部分是私聊用户，后半部分是聊天信息
                    String user = userAndMsg.split(CrazyitProtocol.SPLIT_SIGN)[0];
                    String msg = userAndMsg.split(CrazyitProtocol.SPLIT_SIGN)[1];
                    // 获取私聊用户对应的输出流，并发送私聊信息
                    Server.clients.map.get(user).println(Server.clients.getKeyByValue(ps) + "悄悄地对你说" + msg);
                }
                // 公聊要向每个Socket发送
                else {
                    // 得到真实消息
                    String msg = getRealMsg(line);
                    // 遍历clients中的每个输出流
                    for (var clientPs : Server.clients.valueSet()) {
                        clientPs.println(Server.clients.getKeyByValue(ps) + "给全部人说：" + msg);
                    }
                }
            }

        }
        // 捕获到异常后，表明该Socket对应的客户端已经出现了问题
        catch (IOException e) {
            Server.clients.removeByValue(ps);// 因为这个时候只有对应客户端输出流，没有客户端的名字
            System.out.println(Server.clients.map.size());
            // 关闭网络、IO资源
            try {
                if (br != null) {
                    br.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 将读到的内容去掉前后的协议字符，恢复成真实数据
     * 
     * @param line
     * @return
     */
    private String getRealMsg(String line) {
        return line.substring(CrazyitProtocol.PROTOCOL_LEN, line.length() - CrazyitProtocol.PROTOCOL_LEN);// 开始的位置是2，也就是跳过起始的标识，结束的位置是length-2.也就是跳过了结尾
        // 注意：length-2的脚标的元素是没被包括的，相当于左闭右开
    }
}
