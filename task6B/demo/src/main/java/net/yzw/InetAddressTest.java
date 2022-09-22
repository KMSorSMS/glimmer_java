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
