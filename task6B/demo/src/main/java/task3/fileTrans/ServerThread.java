package task3.fileTrans;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {
    // 对于数据传输的端口号
    private static final int SERVER_DATA = 30002;
    // 所处理的socket连接
    private Socket ss;
    // 服务器与客户端socket连接后的输入字节流,以及包装的字符流
    private BufferedInputStream serverInput;
    private BufferedReader serveReader;
    // 服务器与客户端socket连接后的输出字节流,以及包装的字符流
    private BufferedOutputStream serverOutput;
    private BufferedWriter serveWriter;
    private String usrName;
    // 用户所在的当前文件夹
    private File fileNow;

    public ServerThread(Socket ss) {
        this.ss = ss;
        // 设定打开的目录,指定为当前目录
        fileNow = new File("./");
    }

    @Override
    public void run() {
        try {
            // 获得服务器对应输入流
            this.serveReader = new BufferedReader(new InputStreamReader(ss.getInputStream(), "UTF-8"));
            // 获得服务器对应输出流
            this.serveWriter = new BufferedWriter(new OutputStreamWriter(ss.getOutputStream(), "UTF-8"));

            // 使用读取名字的方法
            registerUsrName();
            // 接收指令
            String commandIn = null;
            String param = null;
            String sentence = null;
            while ((sentence = serveReader.readLine()) != null) {
                // 打印接收到的指令语句
                System.out.println(sentence);
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
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 对于所有命令的处理方法
     * 
     * @param commandIn 命令种类
     * @param param     命令参数
     */
    private void handleCommand(String commandIn, String param) {
        if (commandIn.equals("cd")) {
            // 如果服务器端成功调整了file
            try {
                if (cdCommand(param)) {
                    // 向客户端发送绝对路径名称
                    serveWriter.write(fileNow.getAbsolutePath());
                } else {
                    serveWriter.write("指定文件夹不存在或不是目录");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
        if(commandIn.equals("get")){
            getCommand(param);
            return;
        }
        if(commandIn.equals("put")){
            putCommand(param);
            return;
        }

    }

    /**
     * 在当前文件夹建立一个文件
     * 接收客户端上传数据
     * @return
     */

    public boolean putCommand(String path){
        try{
            System.out.println("打开数据端口: "+SERVER_DATA+"等待连接中...");
            //建立连接
            var socketServer = new ServerSocket(SERVER_DATA);
            var socket = socketServer.accept();
            String[] fileArr = path.split("[\\/]");
            //找到最后一个文件名
            String fileName = fileArr[fileArr.length-1];
            FileOutputStream serveOut = new FileOutputStream("./"+fileName);
            System.out.println("客户端连接成功 等待客户端上传文件 ");
            this.serverInput = new BufferedInputStream(socket.getInputStream());
            byte [] in = new byte[1024];
            //当有读入的时候
            while((serverInput.read(in, 0, in.length))>=0){
                //写入
                serveOut.write(in);
            }
            //关闭端口
            socket.close();
            socketServer.close();
            //关闭文件流
            serveOut.close();
            return true;
        }
        catch(IOException ex){
            //接收文件失败
            System.out.println("接收文件结束");
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 响应客户端的get请求，开启新的端口
     * 客户端对应开启数据的新线程，当服务器关闭通信时
     * 客户端依靠异常处理关闭接收数据
     * @param path
     * @return
     */
    public boolean getCommand(String path) {
        try {
            System.out.println("打开数据端口: "+SERVER_DATA+"等待连接中...");
            File srcFile = new File("./"+path);
            // 判断文件是否存在
            if (!srcFile.exists()) {
                serveWriter.write("FNE");
                return false;
            } else {
                serveWriter.write("done");
            }
            //获得源文件的文件
            FileInputStream fileInTemp = new FileInputStream(srcFile);
            //建立和客户端的连接
            var socketServer = new ServerSocket(SERVER_DATA);
            var socket = socketServer.accept();//得到与客户端数据端口沟通的响应
            System.out.println("客户端连接成功 传输文件 "+path);
            this.serverOutput = new BufferedOutputStream(socket.getOutputStream());
            byte [] in = new byte[1024];
            //当有的时候，进行输出
            while((fileInTemp.read(in, 0, in.length))>=0){
                //输出
                serverOutput.write(in);
            }
            //关闭端口
            socketServer.close();
            //关闭源文件输入流
            fileInTemp.close();
            System.out.println("传输成功 Socket已关闭");
            return true;
            
        } catch (IOException ex) {
            ex.printStackTrace();
            //调试用
            System.out.println("get出现问题");
            return false;
        }

    }

    /**
     * 退出连接
     * 
     * @return
     */
    private boolean quitCommand() {
        try {
            // 向客户端致意再见
            serveWriter.write("bye\n:)");
            // 关闭和客户端开启的资源
            if (ss != null) {
                ss.close();
            }
            if (serveReader != null) {
                serveReader.close();
            }
            if (serveWriter != null) {
                serveWriter.close();
            }
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    /**
     * 对于目录下文件名称的打印
     * 
     * @return
     */

    private boolean lsCommand() {
        try {
            // 向目标输出文件名称，这里用list方法只会打印名称，非绝对路径
            for (String file : fileNow.list()) {
                serveWriter.write(file);
            }
            // 标志文件名称输出结束
            serveWriter.write("done");
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 列出此刻的绝对路径
     * 
     * @return
     */

    private boolean pwdCommand() {
        try {
            serveWriter.write(fileNow.getAbsolutePath());
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 对于cd命令的解决
     * 
     * @param path 路径名称
     */
    private boolean cdCommand(String Path) {
        try {
            // 根据路径名称建立新的file;
            File fileTemp = new File(Path);
            // 当路径存在且为目录时，更新当前的文件
            if (fileTemp != null && fileTemp.isDirectory()) {
                fileNow = fileTemp;
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 获取用户名方法
    private void registerUsrName() {
        String name = null;
        try {
            // 读取名字
            while (name == null) {
                name = serveReader.readLine();
                if (name == null) {
                    serveWriter.write("empty");
                }
            }
            this.usrName = name;
            // 表示完成
            serveWriter.write("done");
            // 返回给客户端当前的路径
            serveWriter.write(fileNow.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
