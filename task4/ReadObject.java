package task4;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadObject {
public static void main(String[] args) {
    try (//创建一个objectInputStream输入流
    var ois = new ObjectInputStream(new FileInputStream("object.txt"))){
        //从输入流中读取一个Java对象，并将其强制类型转换成person类
        var p = (Person) ois.readObject();
        System.out.println("名字为："+p.getName()+"\n年龄为："+p.getAge());
    } catch (IOException|ClassNotFoundException ex) {
        ex.printStackTrace();
    }
}    
}
