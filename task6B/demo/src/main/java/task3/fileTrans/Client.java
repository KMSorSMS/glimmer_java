package task3.fileTrans;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
    private static final int SERVER_COMMAND = 30001;// 服务器命令交互的端口
    private static final int SERVER_DATA = 30002;// 服务器数据交互的端口
    private static final String ip = "47.108.81.131";// 服务器ip地址
    private Socket socket;// 与服务器命令之间建立的连接并返回的socket
    private Socket dataSocket;// 与服务器数据端口之间建立的连接返回的socket
    private BufferedReader brServer;// 输入流获取服务器传来的命令反馈
    private BufferedWriter bwServer;// 输出命令
    private BufferedReader keyIn;// 获得键盘输入
    private BufferedInputStream inputServer;// 获得服务器的输入字节流
    private BufferedOutputStream outputServer;// 获得服务器的输出字节流
    private String fileNow;

    public static void main(String[] args) {
        new Client().init();
    }

    public void init() {
        try {
            // 初始化键盘的输入流
            keyIn = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
            // 连接到服务器端
            System.out.println("客户端已打开,正在连接服务器中...\n" + "IP:" + ip + "Port:" + SERVER_COMMAND + "\n\n");
            socket = new Socket(ip, SERVER_COMMAND);
            System.out.println("服务器连接成功");
            // 初始化与服务器的输入流
            brServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bwServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            // 获得当前路径名称
            fileNow = brServer.readLine();
            // 取得命令输出流
            String commandIn = null;
            String param = null;
            String sentence = null;
            do {
                // 打印命令提示行
                System.out.print(fileNow + ">>>");
                // 输入命令
                sentence = keyIn.readLine();
                // 发送命令
                bwServer.write(sentence+"\n");
                bwServer.flush();
                // 对于命令的解析
                String arr[] = sentence.split("\\s+");
                // 说明为无参数命令：pwd\ls\quit
                if (arr.length <= 1) {
                    commandIn = arr[0];
                }
                // 为含参数命令：cd\get\put\
                else {
                    commandIn = arr[0];
                    param = arr[1];
                }
                // 开始命令的处理部分
                handleCommand(commandIn, param);
            } while (!commandIn.equals("quit"));// 当不为quit时一直读取命令
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 处理命令的方法
     * 
     * @param commandIn 命令
     * @param param     命令参数
     */
    private void handleCommand(String commandIn, String param) {
        if (commandIn.equals("cd")) {
            cdCommand(param);
            return;
        }
        if (commandIn.equals("pwd")) {
            pwdCommand();
            return;
        }
        if (commandIn.equals("ls")) {
            lsCommand();
            return;
        }
        if (commandIn.equals("quit")) {
            quitCommand();
            return;
        }
        if (commandIn.equals("get")) {
            getCommand(param);
            return;
        }
        if(commandIn.equals("put")){
            putCommand(param);
            return;
        }
        System.out.println("err:没有"+commandIn+"指令");
    }

    public void putCommand(String param) {
        if(param==null){
            System.out.println("缺少参数");
            return;
        }
        try {
             //根据参数创建对应的文件实例
             File srcFile = new File(param);
             //判断文件是否有效
             if(!srcFile.exists()){
                 bwServer.write("FNE\n");
                 bwServer.flush();
                 System.out.println("文件路径错误");
                 return;
             }
             else{
                 bwServer.write("done\n");
                 bwServer.flush();
             }
            // 根据数据端口建立连接
            dataSocket = new Socket(ip, SERVER_DATA);
            System.out.println("数据端口已连接 Port" + SERVER_DATA);
            // 获得服务器的对应字节输出流
            outputServer = new BufferedOutputStream(dataSocket.getOutputStream());
            // 打开客户端源文件对应字节流
            FileInputStream fileIn = new FileInputStream(param);
            // 字节流容器
            byte[] in = new byte[1024];
            // 开始上传文件
            System.out.println("开始上传文件");
            //要都多少写多少
            int count = 0;
            while ((count = fileIn.read(in, 0, in.length)) >= 0) {// 读取如果还有
                //输出
                outputServer.write(in,0,count);
                outputServer.flush();
            }
            //关闭输出流
            if(outputServer!=null){
                outputServer.close();
            }
            //关闭socket
            if(dataSocket!=null){
                dataSocket.close();
            }
            if(fileIn!=null){
                fileIn.close();
            }
            //完成上传
            System.out.println("文件传输完毕 Socket已关闭");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }

    /**
     * 从服务器获得数据
     * 
     * @param param
     */
    private void getCommand(String param) {
        if(param==null){
            System.out.println("缺少参数");
            return;
        }
        try {
            // 先通过命令端口读取反馈信息，确定文件存在
            if (brServer.readLine().equals("FNE")) {// 说明文件不存在
                System.out.println("所请求的资源在服务器上不存在");
                return;
            }
            // 根据数据端口建立连接
            dataSocket = new Socket(ip, SERVER_DATA);
            System.out.println("数据端口已连接 Port" + SERVER_DATA);
            // 开启字节流接收
            inputServer = new BufferedInputStream(dataSocket.getInputStream());
            // 打开接收的文件储存位置
            FileOutputStream fileOut = new FileOutputStream("E:/schoollearn/glimmer02/task6B/demo/src/main/java/source/" + param);
            // 字节流容器
            byte[] in = new byte[1024];
            // 读入输入
            // 嵌套异常捕获,因为当服务器关闭输出流时，客户端输入流会出现io异常
            try {
                // 开始下载文件
                System.out.println("开始下载文件");
                //记录读的字节，读多少，写多少
                int count = 0;
                while ((count=inputServer.read(in, 0, in.length)) >= 0) {// 当有输入时
                    // 向指定文件写入
                    fileOut.write(in,0,count);
                    fileOut.flush();
                }
                //其实不会走到这一步
                if (dataSocket != null) {
                    dataSocket.close();
                }
                if (inputServer != null) {
                    inputServer.close();
                }
                if (fileOut != null) {
                    fileOut.close();
                }
                System.out.println("文件传输完毕 Socket已关闭");
            } catch (IOException ex) {
                if (dataSocket != null) {
                    dataSocket.close();
                }
                if (inputServer != null) {
                    inputServer.close();
                }
                if (fileOut != null) {
                    fileOut.close();
                }
                System.out.println("文件传输完毕 Socket已关闭");
                return;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

    }

    /**
     * 处理cd命令
     * 
     * @param param cd命令参数
     */
    private void cdCommand(String param) {
        try {
            // 接收传来的信息
            String receive = brServer.readLine();
            // 如果服务器传来报错信息
            if (receive.equals("NoDir")) {
                System.out.println("文件夹不存在");
            } else {
                // 无误后更改目录
                fileNow = receive;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
    }

    /**
     * 打印出绝对路径
     * 
     * @param param
     */
    private void pwdCommand() {
        try {
            // 接收传来的信息
            String receive = brServer.readLine();
            // 打印
            System.out.println(receive);
            return;
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
    }

    /*
     * 列出当前目录下的文件
     */
    private void lsCommand() {
        try {
            String receive = null;
            // 只要初始接收到结束标志
            while (!(receive = brServer.readLine()).equals("done")) {
                System.out.println(receive);
            }
            return;

        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
    }

    /**
     * 程序退出
     * 断开连接
     */
    private void quitCommand() {
        try {
            // 打印服务器传出的结束语
            System.out.println(brServer.readLine());
            // 关闭各种流
            if (brServer != null) {
                brServer.close();
            }
            if (bwServer != null) {
                bwServer.close();
            }
            if (socket != null) {
                socket.close();
            }
            if (keyIn != null) {
                keyIn.close();
            }
            return;
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
    }

}
