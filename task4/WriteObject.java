package task4;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 这个类将一个person对象写入磁盘文件（序列化）
 */

public class WriteObject {
    public static void main(String[] args) {
        try(//创建一个ObjectOutputStream输出流
        var oos = new ObjectOutputStream(new FileOutputStream("object.txt"))){
            var per = new Person("孙悟空",500);
            //将per对象写入输出流
            oos.writeObject(per);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
