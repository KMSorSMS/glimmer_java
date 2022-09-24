---

---

### 演示视频在本md里，图床在gihub，可能需要翻墙看到

# Java方向-06B：网络编程

![image-20220918095334563](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220918095334563.png)

![image-20220918095514936](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220918095514936.png)

![image-20220918095748017](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220918095748017.png)

![image-20220918100111679](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220918100111679.png)

![image-20220918100302453](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220918100302453.png)

![image-20220918100326423](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220918100326423.png)

![image-20220918100449388](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220918100449388.png)![image-20220918100654498](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220918100654498.png)![image-20220918100528066](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220918100528066.png)

![image-20220918100740035](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220918100740035.png)

-----------------

Task1：

- 了解TCP/IP协议是什么---<u>参考《图解HTTP》</u>

- 先看看这个图，了解网络层次

  ![image-20220918164836318](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220918164836318.png)

  TCP/IP 指传输控制协议/网际协议（Transmission Control Protocol / Internet Protocol）

  ##### 谈谈：在网上查到，首先是IP：

  1. IP属于网络层
  2. Internet Protocol 名字很夸张，但是几乎所有使用网络的系统都会用到IP协议。
  3. IP不是IP地址，它是一种协议
  4. IP协议的作用是把各类数据包传输给对方。要保证确实传送到对方那里，有两个最重要的条件：IP地址，MAC（Media Access Control Address）地址
  5. ![image-20220918164554367](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220918164554367.png)

  小总结：![image-20220918164702226](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220918164702226.png)

##### 然后是TCP：

- TCP属于传输层，提供稳定字节流服务（为了方便传输将大块数据分割成以报文段（segment）为单位的数据包进行管理，而且保证数据准确可靠地传给对方）

- 采用三次握手策略：

  ![image-20220918165357564](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220918165357564.png)

- #### Java中有一个叫做InetAddress的类，可以获取指定主机的信息。

  - 尝试运行以下代码,理解主机名、域名和ip的联系与不同

    ```java
    import java.net.InetAddress;
    
    public class Inet {
        public static void main(String[] args) throws Exception{
            InetAddress address = InetAddress.getLocalHost();
            System.out.println(address);
            address=InetAddress.getByName("glimmer.space");
            System.out.println(address);
        }
    }
    ```

    ![image-20220918170355495](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220918170355495.png)

认识：java提供了InetAddress类来代表IP地址

关于这个类的方法：

1. ![image-20220918171136331](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220918171136331.png)

2. 这里被突然的域名，主机名，ip地址，全限定域名搞得有点晕，我得仔细看看区别：

   - ![image-20220918171811598](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220918171811598.png)<u>域名只是在公网（INtERNET)中存在(以实验为目的的局域网域网实验性除外)，每个域名都对应一个IP地址，但一个IP地址可有对应多个域名。域名类型 linuxsir.org 这样的；</u>

     

   - 主机名

     主机名是分配给网络上的设备（主机）的标签（名称），用于区分特定网络上或互联网上的某个设备。<u>家庭网络中计算机的主机名可能与*新笔记本电脑* ， *Guest-Desktop*或*FamilyPC类似*</u> 。**主机名也被DNS服务器使用**，因此您可以通过一个通用的，易于记忆的名称访问网站，以避免为了打开网站而记住一串数字（一个IP地址 ）。

     ##### 主机名的例子

     ##### 以下每个示例都是<u>完全限定域名</u> （FQDN），其主机名写在旁边：

     - pcsupport.about.com：pcsupport
     - www.google.com：www
     - **images.google.com** ：images
     - **products.office.com** ：products
     - **www.microsoft.com：www**

     

     ### <u>**主机名(hostname)和域名(Domain）的区别；**</u>

     <u>**主机名就机器本身的名字，域名是用来解析到IP的。但值得一说的是在局域网中，主机名也是可以解析到IP上的；**</u>

     ### 重要的理解：

     这样就会产生疑惑：主机名应该就是一个字符串<u>**没有.**</u>才对，那为什么我们的getHostName方法返回的是glimmer.space呢？

     看解答：<u>**Internet域名是Internet网络上的一个服务器或一个网络系统的名字,在全世界,没有重复的域名。域名的范围要比主机名大。一个域名下可以有多个主机名,域名下还可以有子域名。例如,域名cnwg.cn下,有主机server1和server2,其主机全名就是server1.cnwg.cn和server2.cnwg.cn**</u>，也就是说这里的glimmer.space是主机全名，包含了域名，而space是域名

     再上图：

     ![image-20220918175432060](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220918175432060.png)

     ![image-20220918175605163](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220918175605163.png)

   - #### 完全限定域名

     ### FQDN示例

     完全限定的域名总是以这种格式写入： *[host name].[domain].[tld]* 。 例如， *example.com*域上的邮件服务器可能使用FQDN *mail.example.com* 。

     以下是一些完全合格的域名的其他例子：

     www.microsoft.com en.wikipedia.org p301srv03.timandtombreadco.us

     不是“完全限定”的域名总会对它们产生某种模糊的含义。 例如， *p301srv03*不能是FQDN，因为有任何数量的域也可能具有该名称的服务器。 *p301srv03.wikipedia.com*和*p301srv03.microsoft.com*只是两个例子 - 只知道主机名对你*没什么* *用处* 。

3. 好了，等不及了，先™试试：

   ```java
   package net.yzw;
   import java.net.InetAddress;
   
   public class InetAddressTest {
       public static void main(String[] args) throws Exception {
       //根据主机来获取对应的InetAddress对象
           InetAddress ip = InetAddress.getByName("www.baidu.com");
           //看看主机名字
           System.out.println(ip.getHostName());
           //完整的
           System.out.println(ip.getCanonicalHostName());
   
           //根据原始IP地址来获取对应的InetAddress实例
           InetAddress local = InetAddress.getByAddress(new byte[] {127,0,0,1});
           System.out.println(local.getCanonicalHostName());//这里得到的都是ip
           System.out.println(local.getHostAddress());
           //通过localhost方法得到的是名字
           InetAddress address = InetAddress.getLocalHost();
           System.out.println(address.getCanonicalHostName());
           System.out.println(address.getHostAddress());
           
       }
   }
   
   ```

   ![image-20220918183629934](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220918183629934.png)

- #### 通过URL，我们可以获取各种网络上的资源🔗。

  - Java提供了`java.net.URL`类，通过它，我们可以研究🔎URL构造。

  - 尝试运行以下代码，分析URL包含了哪些信息。（答案写在注释里）

    ```java
    package net.yzw;
    
    import java.net.MalformedURLException;
    import java.net.URL;
    
    public class LookURL {
    
        public static void main(String[] args)  {
                try {
                    URL url = new URL("https://www.bilibili.com/video/BV1nU4y1i7Lt?share_source=copy_web");
                    System.out.println(url.getProtocol());//协议---https
                    /**
                     * The getAuthority() function is a part of URL class. 
                     * The function getAuthority() returns the authority 
                     * of a specified URL. The Authority part of the URL
                     *  is the host name and the port of the URL.
                     */
                    /**
                     * The difference between getAuthority() and getHost()
                     *  function is that getAuthority() returns the host 
                     * along with the port but getHost() returns only the
                     *  host name.
                     * e.g.:
                     * URL = https:// www.geeksforgeeks.org:80/url-samefile-method-in-java-with-examples/
                     * Authority =  www.geeksforgeeks.org:80
                     * Host =  www.geeksforgeeks.org
                     */
                    System.out.println(url.getAuthority());//即主机名加端口号，英文是我查的资料与例子
                    System.out.println(url.getFile());//获取URL的资源名
                    System.out.println(url.getHost());//主机名
                    System.out.println(url.getPath());//获取URL的路径部分，即资源名里面?之前
                    System.out.println(url.getPort());//端口号the port number, or -1 if the port is not set
                    System.out.println(url.getDefaultPort());//返回与改URL联系的默认端口号
                    System.out.println(url.getQuery());//得到URL的查询字符串部分，即资源名里面?以后
                    System.out.println(url.getRef());//the anchor (also known as the "reference") of this URL, or null if one does not exist
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
        }
    }
    
    ```

    对于URL：![image-20220918184611744](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220918184611744.png)

​							而URI：![image-20220918184639452](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220918184639452.png)

## TASK2：简单的Socket编程

- 通过Socket，我们可以方便地进行网络通信。
- 尝试通过客户端-服务器结构编写一个程序。实现**客户端和服务器互相发送文本信息**并读取。
- 通过这一步，我们可以了解：
  - 如何创建Socket对象并建立连接。
  - Socket基本的I/O方法
  
  在这里我想做一个简单的聊天工具，但是当我把自己的主机当作服务器的时候，遇到了问题，我只能实现局域网的交流，没办法让公网来访问我
  
  ![image-20220919165545382](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220919165545382.png)
  
  就是这里，我的ip是在局域网里面的，并不是在公网里面，在公网的client没办法连接到我的服务器
  
  --------------
  
  ## 寻找答案
  
  ## DMZ
  
  登录路由器过后，在页面内找到 DMZ选项（各家路由器不太一样），点击开启，并设置想要暴露到外网的电脑的 IP （路由器分配给电脑的内网 IP） 地址，确实开启，即可在外网通过 IP （拨号的外网 IP）地址访问到暴露的电脑（电脑需要提供访问服务）。
  
  DMZ主机是开放了指定的内网电脑的所有端口，DMZ和端口转发不能同时使用，如果需要映射多台主机到外网可以使用端口转发。

![image-20220919171719294](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220919171719294.png)

但是我们开的是局域网

，我打算买个服务器然后运行那个程序

![image-20220920084913381](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220920084913381.png)



![image-20220920085040173](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220920085040173.png)

连接了

怎么下载东西呢？：![image-20220920090740747](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220920090740747.png)

![image-20220920090650954](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220920090650954.png)查看jdk版本

![image-20220920090559377](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220920090559377.png)

下载JDK11

要去配置maven![image-20220920092046929](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220920092046929.png)

![image-20220920092027652](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220920092027652.png)

结果我的是ip是私网的：![image-20220920093543876](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220920093543876.png)
我这里开服务器服务的时候出问题了，就是我一直不知道怎么用我服务器的外网ip来提供socket服务，我用的new ServerSocket()构造器，要是只传入端口号就会用服务器私网的ip，然后我就认为服务器是连个ip，公网和私网
，然后我就想到new ServerSocket（）有个重载构造器是可以指定另一个ip的，
![img](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/YEARJTOV$9GZ%7DBZCNU%5BB2E3.png)
，结果我就报错了

然后又遇到了乱码问题：![image-20220920120757298](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220920120757298.png)

![image-20220920121032570](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220920121032570.png)

当英文的时候是正常，但是中文就是异常，推断是编码格式问题

但是我服务器编辑的格式是utf-8	![image-20220920121148919](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220920121148919.png)

客户端编辑的格式也是utf-8:![image-20220920121234397](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220920121234397.png)

但是无论是服务器发给客户端的中文消息，还是客户端给服务器的中文消息，都是乱码

我推断，那是socket对应输出流的编码格式不是utf-8造成的，那应该是JVM的编码格式非utf-8吗，但是我用的是![image-20220920121420248](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220920121420248.png)这个扩展来编译运行的java，如果是这个问题的话，那怎么改正呢？如果不是这个问题的话又是哪里出错了呢？

-------

关于写的局域网聊天平台的部分：

1. ### 服务器部分

   ![](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/QQ%E5%9B%BE%E7%89%8720220919222150.jpg)

------

- 接下来，我们将要利用Socket写一个功能简单的远程文件传输工具。
- 功能描述：
  - 客户端和服务器需要建立两个Socket连接（命令端口和数据端口）。命令端口用于客户端向服务器发送命令，数据端口用于上传或下载文件，每次传输开始时打开，传输完毕后关闭。
  - 客户端每行以`>>>`开始，提示用户输入。
  - 服务器会显示接收到的指令（可以尝试输出到一个日志文件中）。
  - 你需要实现以下指令
  
  ## 对于如何运行：

![image-20220923213315959](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923213315959.png)在这个文件夹里![image-20220923213631706](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923213631706.png)![image-20220923213746500](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923213746500.png)这里是在局域网调试，（因为服务器是我租的，还没学会怎么一直让它挂着端口运行程序，就让学长测试的时候先不用服务器来测试好了)

![image-20220923213939094](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923213939094.png)输入用户名登录聊天

![image-20220923214412392](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923214412392.png)![image-20220923214422717](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923214422717.png)

![image-20220923214529210](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923214529210.png)

![image-20220923214637863](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923214637863.png)注意我的分支和位置

---------------------

Task3

遇到了文件名处理问题，用的File.getabsolutepath ,有问题：

![image-20220923180853647](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923180853647.png)

还需要解决相对路径无法建立file实例的问题（因为jvm只会注册启动时的文件夹，相对路径只会在那里面）

![image-20220923181237958](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923181237958.png)

![image-20220923181800768](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923181800768.png)

BufferedOutputStream使用出问题了，我觉得这个写入用个flush就好，读入就用对应的read

![image-20220923181638737](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923181638737.png)

![image-20220923182047526](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923182047526.png)

把打开路径都改成像这样的模式

![image-20220923183249490](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923183249490.png)

文件名好像有出错

![image-20220923184534689](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923184534689.png)

我在这里用正则表达式想把文件名单独拿出来，不过好像是这里出了问题，没达到我的预期，才报的错：

![image-20220923184911460](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923184911460.png)

![image-20220923184959134](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923184959134.png)

艹，我想起来了，因为\在java字符串里面是转义字符，又在正则里面是转义字符，在字符串里面用\\\表示\过后，这个在正则里面是转义，所以还要\\\,总共是\\\\\\\来表示正则里面的\:

![image-20220923185618352](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923185618352.png)

![image-20220923185638104](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923185638104.png)

![image-20220923185703233](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923185703233.png)可以了![image-20220923185730543](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923185730543.png)

用bufferedoutputstream来写复制过来的文件的时候为什么一直会出现这种东西

![image-20220923191443421](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923191443421.png)因为要读多少写多少，这里多写了，最后一次1024字节没满![image-20220923191616194](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923191616194.png)

成功了：![image-20220923192111775](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923192111775.png)

视频里还有两个功能没放出来：![image-20220923195334101](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923195334101.png)文件名错误是get不到的

![image-20220923201456968](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923201456968.png)文件路径错误也是找不到的

<iframe src="//player.bilibili.com/player.html?aid=560839537&bvid=BV1Te4y1b7Zm&cid=841351136&page=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" height = "700" width = "700"> </iframe>

我还在租的服务器上试了一下，挺好，支持多客户端同时访问（不过还没进行并发控制，有点懒了）

对于服务器上linux系统，调整了文件夹的名称就是\变成/，

<iframe src="//player.bilibili.com/player.html?aid=815774240&bvid=BV1iG4y1B7VR&cid=841408344&page=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" height = "700"> </iframe>

代码贴上：

```java
package net.yzw;

import java.net.ServerSocket;

/**
 * 开启socket端口，打开线程处理客户端命令，记录客户端名称与输出流
 * 开启服务器
 */

public class Server {
    //进行命令处理的端口号
    private static final int SERVER_COMMAND = 30001;
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
        new Server().init();
    }
    
}

```

```java
package net.yzw;

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
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

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
    // private String usrName;
    // 用户所在的当前文件夹
    private File fileNow;

    public ServerThread(Socket ss) {
        this.ss = ss;
        // 设定打开的目录,指定为当前目录
        fileNow = new File(".");
    }

    @Override
    public void run() {
        try {
            // 获得服务器对应输入流
            this.serveReader = new BufferedReader(new InputStreamReader(ss.getInputStream(), "UTF-8"));
            // 获得服务器对应输出流
            this.serveWriter = new BufferedWriter(new OutputStreamWriter(ss.getOutputStream(), "UTF-8"));

            // 初次连接，发送文件绝对路径信息
            //调试用
            System.out.println(fileNow.getCanonicalPath());
            serveWriter.write(fileNow.getCanonicalPath()+"\n");
            //将缓冲区数据写入
            serveWriter.flush();
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
        } catch(SocketException ex){
            return;
        } 
        catch (IOException ex) {
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
        if (commandIn.equals("put")) {
            putCommand(param);
            return;
        }

    }

    /**
     * 在当前文件夹建立一个文件
     * 接收客户端上传数据
     * 
     * @return
     */

    private boolean putCommand(String path) {
        if(path==null){
            return false;
        }
        try {
            //先看客户端反馈，是否上传文件是有效的
            if(serveReader.readLine().equals("FNE")){//说明文件不存在
                System.out.println("客户端上传的文件无效");
                return false;
            }
            System.out.println("打开数据端口: " + SERVER_DATA + "等待连接中...");
            // 建立连接
            var socketServer = new ServerSocket(SERVER_DATA);
            var socket = socketServer.accept();
            //表示连接成功
            System.out.println("客户端连接成功 等待客户端上传文件 ");
            //根据正则表达式解析出文件名称
            String[] fileArr = path.split("[\\\\/]");
            // 找到最后一个文件名
            String fileName = fileArr[fileArr.length - 1];
            //测试一下，因为出现了文件名错误
            System.out.println("发出的文件名为"+fileName);
            //客户端上传的文件到当前的目录下
            FileOutputStream serveOut = new FileOutputStream(fileNow + "/" + fileName);
            this.serverInput = new BufferedInputStream(socket.getInputStream());
            //字节流容器
            byte[] in = new byte[1024];
            //要保证读多少写多少
            int count = 0;
            try {
                // 当有读入的时候
                while ((count = serverInput.read(in, 0, in.length)) >= 0) {
                    // 写入
                    serveOut.write(in,0,count);
                    serveOut.flush();
                }
                 // 关闭端口
                 if (socket != null) {
                    socket.close();
                }
                if (socketServer != null) {
                    socketServer.close();
                }
                // 关闭文件流
                if (serveOut != null) {
                    serveOut.close();
                }
                // 接收文件结束，检查到读入流关闭
                System.out.println("接收文件结束");
            } catch (IOException ex) {
                // 关闭端口
                if (socket != null) {
                    socket.close();
                }
                if (socketServer != null) {
                    socketServer.close();
                }
                // 关闭文件流
                if (serveOut != null) {
                    serveOut.close();
                }
                // 接收文件结束，检查到读入流关闭
                System.out.println("接收文件结束");
            }
            return true;
        } catch (IOException ex) {
            System.out.println("接收客户输入异常");
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 响应客户端的get请求，开启新的端口
     * 客户端对应开启数据的新线程，当服务器关闭通信时
     * 客户端依靠异常处理关闭接收数据
     * 
     * @param path
     * @return
     */
    private boolean getCommand(String path) {
        if(path==null){
            return false;
        }
        try {
            System.out.println("打开数据端口: " + SERVER_DATA + "等待连接中...");
            //打开当前目录下的path文件
            File srcFile = new File(fileNow+"/" + path);
            // 判断文件是否存在
            if (!srcFile.exists()) {
                serveWriter.write("FNE\n");
                serveWriter.flush();
                return false;
            } else {
                serveWriter.write("done\n");
                serveWriter.flush();
            }
            // 获得源文件的文件
            FileInputStream fileInTemp = new FileInputStream(srcFile);
            // 建立和客户端的连接
            var socketServer = new ServerSocket(SERVER_DATA);
            var socket = socketServer.accept();// 得到与客户端数据端口沟通的响应
            System.out.println("客户端连接成功 传输文件 " + path);
            this.serverOutput = new BufferedOutputStream(socket.getOutputStream());
            //字节流容器
            byte[] in = new byte[1024];
            //保证读多少写多少
            int count = 0;
            // 当有的时候，进行输出
            while ((count = fileInTemp.read(in, 0, in.length)) >= 0) {
                // 输出
                serverOutput.write(in,0,count);
                serverOutput.flush();
            }
            //关闭端口
            if(socket!=null){
                socket.close();
            }
            // 关闭端口
            if(socketServer!=null){
            socketServer.close();
            }
            // 关闭源文件输入流
            if(fileInTemp!=null){
            fileInTemp.close();
            }
            if(serverOutput!=null){
                serverOutput.close();
            }
            System.out.println("传输成功 Socket已关闭");
            return true;

        } catch (IOException ex) {
            ex.printStackTrace();
            // 调试用
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
            serveWriter.write("bye:)\n");
            serveWriter.flush();
            // 关闭和客户端开启的资源
            if (serveReader != null) {
                serveReader.close();
            }
            if (serveWriter != null) {
                serveWriter.close();
            }
            if (ss != null) {
                ss.close();
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
                serveWriter.write(file+"\n");
                serveWriter.flush();
            }
            // 标志文件名称输出结束
            serveWriter.write("done\n");
            serveWriter.flush();
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
            serveWriter.write(fileNow.getCanonicalPath()+"\n");
            serveWriter.flush();
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
            // 根据路径名称补充为绝对路径建立新的file;
            File fileTemp = new File(fileNow+"/"+Path);
            // 当路径存在且为目录时，更新当前的文件
            if (fileTemp != null && fileTemp.isDirectory()) {
                fileNow = fileTemp;
                // 向客户端发送绝对路径名称
                serveWriter.write(fileNow.getCanonicalPath()+"\n");
                serveWriter.flush();
                return true;
            }
            serveWriter.write("NoDir\n");
            serveWriter.flush();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                serveWriter.write("NoDir\n");
                serveWriter.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }
}

```

老实说我的注释自认为应该很详尽

![image-20220923214727198](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220923214727198.png)

代码主体在githb，网址：

#### https://github.com/KMSorSMS/glimmer_java.git



对于port，ip的动态传参实现：

![image-20220924090212057](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220924090212057.png)

![image-20220924090404184](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220924090404184.png)

这样写好像不对：![image-20220924091156218](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220924091156218.png)

![image-20220924091229904](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220924091229904.png)

它是传入的这个![image-20220924091315930](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220924091315930.png)

感觉这样有戏:![image-20220924091517261](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220924091517261.png)

好耶！![image-20220924091553114](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220924091553114.png)

配置好运行时参数：![image-20220924091852116](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220924091852116.png)

测试一下：

![image-20220924093151170](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220924093151170.png)![image-20220924093248067](https://fastly.jsdelivr.net/gh/KMSorSMS/picGallery/img/image-20220924093248067.png)

这里我对于运行时的参数传递要求是：如果要传参不用默认值，那么你的参数必须完整，端口号加ip（客户端），端口号（服务器端），因为你如果要用自己的值，默认值并没有什么实际意义，最多只是处于偶然和你的值相等，那么你应该自己把ip和端口写完整，防止漏掉出错
