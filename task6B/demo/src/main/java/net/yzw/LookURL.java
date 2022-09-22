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
                System.out.println(url.getAuthority());//即主机名加端口号
                System.out.println(url.getFile());//获取URL的资源名
                System.out.println(url.getHost());//主机名
                System.out.println(url.getPath());//获取URL的路径部分，即资源名里面?之前
                System.out.println(url.getPort());//端口号the port number, or -1 if the port is not set
                System.out.println(url.getDefaultPort());//返回与改URL联系的默认端口号
                System.out.println(url.getQuery());//得到URL的查询字符串部分，即资源名里面?以后
                System.out.println(url.getRef());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
    }
}
