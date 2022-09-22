package net.yzw;

import java.net.InetAddress;

public class Inet {
    public static void main(String[] args) throws Exception{
        InetAddress address = InetAddress.getLocalHost();
        System.out.println(address);
        System.out.println(address.getHostName());
        address=InetAddress.getByName("glimmer.space");
        System.out.println(address);
        System.out.println(address.getHostName());

    }
}