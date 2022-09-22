# Java方向-06B：网络编程

![image-20220918095334563](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220918095334563.png)

![image-20220918095514936](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220918095514936.png)

![image-20220918095748017](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220918095748017.png)

![image-20220918100111679](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220918100111679.png)

![image-20220918100302453](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220918100302453.png)

![image-20220918100326423](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220918100326423.png)

![image-20220918100449388](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220918100449388.png)![image-20220918100654498](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220918100654498.png)![image-20220918100528066](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220918100528066.png)

![image-20220918100740035](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220918100740035.png)

-----------------

Task1：

- 了解TCP/IP协议是什么---<u>参考《图解HTTP》</u>

- 先看看这个图，了解网络层次

  ![image-20220918164836318](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220918164836318.png)

  TCP/IP 指传输控制协议/网际协议（Transmission Control Protocol / Internet Protocol）

  ##### 谈谈：在网上查到，首先是IP：

  1. IP属于网络层
  2. Internet Protocol 名字很夸张，但是几乎所有使用网络的系统都会用到IP协议。
  3. IP不是IP地址，它是一种协议
  4. IP协议的作用是把各类数据包传输给对方。要保证确实传送到对方那里，有两个最重要的条件：IP地址，MAC（Media Access Control Address）地址
  5. ![image-20220918164554367](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220918164554367.png)

  小总结：![image-20220918164702226](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220918164702226.png)

##### 然后是TCP：

- TCP属于传输层，提供稳定字节流服务（为了方便传输将大块数据分割成以报文段（segment）为单位的数据包进行管理，而且保证数据准确可靠地传给对方）

- 采用三次握手策略：

  ![image-20220918165357564](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220918165357564.png)

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

    ![image-20220918170355495](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220918170355495.png)

认识：java提供了InetAddress类来代表IP地址

关于这个类的方法：

1. ![image-20220918171136331](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220918171136331.png)

2. 这里被突然的域名，主机名，ip地址，全限定域名搞得有点晕，我得仔细看看区别：

   - ![image-20220918171811598](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220918171811598.png)<u>域名只是在公网（INtERNET)中存在(以实验为目的的局域网域网实验性除外)，每个域名都对应一个IP地址，但一个IP地址可有对应多个域名。域名类型 linuxsir.org 这样的；</u>

     

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

     ![image-20220918175432060](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220918175432060.png)

     ![image-20220918175605163](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220918175605163.png)

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

   ![image-20220918183629934](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220918183629934.png)

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

    对于URL：![image-20220918184611744](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220918184611744.png)

​							而URI：![image-20220918184639452](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220918184639452.png)

## TASK2：简单的Socket编程

- 通过Socket，我们可以方便地进行网络通信。
- 尝试通过客户端-服务器结构编写一个程序。实现**客户端和服务器互相发送文本信息**并读取。
- 通过这一步，我们可以了解：
  - 如何创建Socket对象并建立连接。
  - Socket基本的I/O方法
  
  在这里我想做一个简单的聊天工具，但是当我把自己的主机当作服务器的时候，遇到了问题，我只能实现局域网的交流，没办法让公网来访问我
  
  ![image-20220919165545382](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220919165545382.png)
  
  就是这里，我的ip是在局域网里面的，并不是在公网里面，在公网的client没办法连接到我的服务器
  
  --------------
  
  ## 寻找答案
  
  ## DMZ
  
  登录路由器过后，在页面内找到 DMZ选项（各家路由器不太一样），点击开启，并设置想要暴露到外网的电脑的 IP （路由器分配给电脑的内网 IP） 地址，确实开启，即可在外网通过 IP （拨号的外网 IP）地址访问到暴露的电脑（电脑需要提供访问服务）。
  
  DMZ主机是开放了指定的内网电脑的所有端口，DMZ和端口转发不能同时使用，如果需要映射多台主机到外网可以使用端口转发。

![image-20220919171719294](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220919171719294.png)

但是我们开的是局域网

，我打算买个服务器然后运行那个程序

![image-20220920084913381](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220920084913381.png)



![image-20220920085040173](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220920085040173.png)

连接了

怎么下载东西呢？：![image-20220920090740747](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220920090740747.png)

![image-20220920090650954](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220920090650954.png)查看jdk版本

![image-20220920090559377](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220920090559377.png)

下载JDK11

要去配置maven![image-20220920092046929](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220920092046929.png)

![image-20220920092027652](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220920092027652.png)

结果我的是ip是私网的：![image-20220920093543876](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220920093543876.png)
我这里开服务器服务的时候出问题了，就是我一直不知道怎么用我服务器的外网ip来提供socket服务，我用的new ServerSocket()构造器，要是只传入端口号就会用服务器私网的ip，然后我就认为服务器是连个ip，公网和私网
，然后我就想到new ServerSocket（）有个重载构造器是可以指定另一个ip的，
![img](file:///C:\Users\yuanz\Documents\Tencent Files\1429572661\Image\C2C\YEARJTOV$9GZ}BZCNU[B2E3.png)
，结果我就报错了

然后又遇到了乱码问题：![image-20220920120757298](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220920120757298.png)

![image-20220920121032570](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220920121032570.png)

当英文的时候是正常，但是中文就是异常，推断是编码格式问题

但是我服务器编辑的格式是utf-8	![image-20220920121148919](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220920121148919.png)

客户端编辑的格式也是utf-8:![image-20220920121234397](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220920121234397.png)

但是无论是服务器发给客户端的中文消息，还是客户端给服务器的中文消息，都是乱码

我推断，那是socket对应输出流的编码格式不是utf-8造成的，那应该是JVM的编码格式非utf-8吗，但是我用的是![image-20220920121420248](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220920121420248.png)这个扩展来编译运行的java，如果是这个问题的话，那怎么改正呢？如果不是这个问题的话又是哪里出错了呢？

-------

关于写的局域网聊天平台的部分：

1. ### 服务器部分

   ![](D:\Extend_E\Photos\QQ图片20220919222150.jpg)

------

- 接下来，我们将要利用Socket写一个功能简单的远程文件传输工具。
- 功能描述：
  - 客户端和服务器需要建立两个Socket连接（命令端口和数据端口）。命令端口用于客户端向服务器发送命令，数据端口用于上传或下载文件，每次传输开始时打开，传输完毕后关闭。
  - 客户端每行以`>>>`开始，提示用户输入。
  - 服务器会显示接收到的指令（可以尝试输出到一个日志文件中）。
  - 你需要实现以下指令