package task4;

public class Person implements java.io.Serializable {
    private String name;
    private int age;
    //注意这里没有提供无参数构造器，等会儿我反序列化的时候是不会主动调用构造器的，我是把这个对象直接还原过来
    public Person(String name, int age){
        System.out.println("有参数构造器");
        this.name = name;
        this.age = age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public String getName() {
        return name;
    }
}
