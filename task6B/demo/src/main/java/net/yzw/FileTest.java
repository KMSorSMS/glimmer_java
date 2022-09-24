package net.yzw;


public class FileTest {
    public static void main(String[] args) {
        try{
            // String fileName = "E:\\schoollearn\\glimmer02\\task6B\\demo\\src\\main\\java\\net\\yzw\\FileTest.java";
            // String[] fileArr = fileName.split("[\\\\/]");
            // System.out.println("分割文件名长度:"+fileArr.length);
            // System.out.println("最后一个"+fileArr[fileArr.length-1]);
            //测试运行时参数传入
            String i = args[0];
            System.out.println(i+"   "+args[1]);
        }
        catch(Exception e){
            e.printStackTrace();
        } 
    }
}
