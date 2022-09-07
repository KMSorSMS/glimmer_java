taskt1

-----

类的实质是**一种引用数据类型**，类似于 [byte](https://baike.baidu.com/item/byte/810420?fromModule=lemma_inlink)、[short](https://baike.baidu.com/item/short/4729482?fromModule=lemma_inlink)、[int](https://baike.baidu.com/item/int/54055?fromModule=lemma_inlink)（char）、[long](https://baike.baidu.com/item/long/412402?fromModule=lemma_inlink)、[float](https://baike.baidu.com/item/float/19167524?fromModule=lemma_inlink)、[double](https://baike.baidu.com/item/double/2726635?fromModule=lemma_inlink) 等基本数据类型，不同的是它是一种复杂的数据类型。因为它的本质是[数据类型](https://baike.baidu.com/item/数据类型/10997964?fromModule=lemma_inlink)，而不是[数据](https://baike.baidu.com/item/数据/5947370?fromModule=lemma_inlink)，所以不存在于内存中，不能被直接操作，只有被实例化为对象时，才会变得可操作。

类是**对现实生活中一类具有共同特征的事物的抽象**。如果一个程序里提供的数据类型与应用中的概念有直接的对应，这个程序就会更容易理解，也更容易修改。

#### 类的属性也就是类变量，是类包括它所有的实例对象共有的数据

#### 类方法是类包括它所有的实例对象共有的方法（很类型于函数

public class Girlfriend02 {

  private String name = "cyh";

  private int age = 18;

  private int height = 165;

  private int facialDegree = 9;*//(out of 10);*

  public void dating(){

​    System.out.println(name+"在dating");

  }

  public void eat(){

​    System.out.println(name+"在吃饭");

  }

  public void bailan(){

​    System.out.println(name+"摆烂中！");

  }

  public void study(){

​    System.out.println(name+"学习");

  }

  public String getName() {

​    return name;

  }

  public void setAge(int *age*) {

​    this.age = *age*;

  }

  public void setName(String *name*) {

​    this.name = *name*;

  }

  public void setHeight(int *height*) {

​    this.height = *height*;

  }

  public static void main(String[] *args*){

​    Girlfriend02 girlfriend1 = new Girlfriend02();

​    girlfriend1.setName("cyh");

​    girlfriend1.bailan();

​    girlfriend1.eat();

  } 

}

instanceof 是 Java 的一个二元操作符，类似于 ==，>，< 等操作符。

instanceof 是 Java 的保留关键字。它的作用是测试它左边的对象是否是它右边的类的实例，返回 boolean 的数据类型。

##### ps：这里其实有坑，虽然测试了左边是否为右边的实例，但是实际上有可能是右边类子类的实例，并非直接是它的实例，要是直接判断，就用.getclass()方法

![image-20220906212135293](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220906212135293.png)

##### 什么是对象/实例？和类有什么区别？：

对象是类的具体化，它拥有类的全部类变量，类方法，还有实例变量和实例方法，只要由对象，那么就一定存在类，类初始化后才可能创建一个或多个实例，类并不拥有实例变量和实例方法，所以类方法中无法使用实例方法/变量

#### 了解什么是类的构造方法，给你的类写一个构造方法并使用它。

类构造方法是用来构造类的实例变量的，他的返回值就是实例变量的地址（我是这样理解的哈）

public class Girlfriend02 {

  private String name = "cyh";

  private int age = 18;

  private int height = 165;

  private int facialDegree = 9;*//(out of 10);*

  public Girlfriend02(int *age*){

​    this.age = *age*;

  }

  public Girlfriend02(){



  }

  public void dating(){

​    System.out.println(name+"在dating");

  }

  public void eat(){

​    System.out.println(name+"在吃饭");

  }

  public void bailan(){

​    System.out.println(name+"摆烂中！");

  }

  public void study(){

​    System.out.println(name+"学习");

  }

  public String getName() {

​    return name;

  }

  public void setAge(int *age*) {

​    this.age = *age*;

  }

  public void setName(String *name*) {

​    this.name = *name*;

  }

  public void setHeight(int *height*) {

​    this.height = *height*;

  }

  public static void main(String[] *args*){

​    Girlfriend02 girlfriend1 = new Girlfriend02();

​    girlfriend1.setName("cyh");

​    girlfriend1.bailan();

​    girlfriend1.eat();

​    Girlfriend02 girlfriend2 = new Girlfriend02(19);



  } 

}

--------

public class Girlfriend02 {

  private String name = "cyh";

  private int age = 18;

  private int height = 165;

  private int facialDegree = 9;*//(out of 10);*

  public Girlfriend02(int *age*){

​    this.age = *age*;

  }

  public Girlfriend02(){



  }

  public void dating(){

​    System.out.println(name+"在dating");

  }

  public void eat(){

​    System.out.println(name+"在吃饭");

  }

  public void bailan(){

​    System.out.println(name+"摆烂中！");

  }

  public void study(){

​    System.out.println(name+"学习");

  }

  public String getName() {

​    return name;

  }

  public void setName(String *name*) {

​    this.name = *name*;

  }

  public void setHeight(int *height*) {

​    this.height = *height*;

  }

  public static void main(String[] *args*){

​    Girlfriend02 girlfriend1 = new Girlfriend02();

​    girlfriend1.setName("cyh");

​    girlfriend1.bailan();

​    girlfriend1.eat();

​    Girlfriend02 girlfriend2 = new Girlfriend02(19);



  } 

}

![image-20220906213754812](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220906213754812.png)

![image-20220906213953927](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220906213953927.png)

##### 为什么要限制外部访问？结合“封装”的知识，谈谈你的理解。

1、和客观世界逻辑一样，对象的状态信息都是被隐藏在对象内部，外界无法直接操作和修改。比如人的年纪除了出生时0岁设定，后面的更改只能时随着时间流逝而变化，不能自己修改。

2、同样，我们很多数据都是需要进行封装，所以通过访问限制符实现了数据的隐藏，使得访问者无法进行恶意的或者不和逻辑的修改

#### 了解什么是Java的包，讲讲包有什么作用。

包是为了解决重名，大规模开发防止类名重复而产生。它提供了类的多层命名空间。通过将一组功能相关的类放到一个包下，不仅实现了名字的独立，还能增强模块化，更有逻辑，比如java.util、java.swing、java.awt等等

##### 新建一个名为`space.glimmer.lumine`的包，将你的类丢进去，尝试从外部访问它。

不是很想写，我就写个答案把。从外部访问必须要写完整的名字space.glimmer.lumine.xxx,

-------------------



##### 学习属性和方法的 `static` 关键字，完成上述要求的类并将代码附在题解中。

##### 如何理解`static`的“静态”的含义？被`static`修饰的成员和其他成员有什么区别？

import java.util.ArrayList;

/**
 * 实现对于waifu对象的管理
 */
 public class WaiFuContraller02 {
    private static ArrayList<Girlfriend02> waiFus = new ArrayList<>();// 通过list集合实例ArrayList进行管理

    public static void add(Girlfriend02 wife) {
        waiFus.add(wife);
    }

    public static int returnNum() {
        return waiFus.size();
    }

    /**
     * 根据姓名删除
     * 
     * @param name
     */

    public static void remove(String name) {
        for (int i = 0; i < waiFus.size(); i++) {
            if (waiFus.get(i).getName().equals(name)) {
                waiFus.remove(i);
            }
        }
    }

    /**
     * 根据姓名获得
     * 
     * @param name
     */

    public static Girlfriend02 get(String name) {
        for (int i = 0; i < waiFus.size(); i++) {
            if (waiFus.get(i).getName().equals(name)) {
                return waiFus.get(i);
            }
        }
        return null;
    }

    /**
     * 测试一下
     * 
     * @param args
     */
        public static void main(String[] args) {
        Girlfriend02 girlfriend1 = new Girlfriend02();
        girlfriend1.setName("lzl");
        girlfriend1.bailan();
        girlfriend1.eat();
        Girlfriend02 girlfriend2 = new Girlfriend02(19);
        Girlfriend02 girlfriend3 = new Girlfriend02(21);
        Girlfriend02 girlfriend4 = new Girlfriend02(30);
        girlfriend4.setName("bdyjy");
        girlfriend3.setName("lzll");
        WaiFuContraller02.add(girlfriend1);
        WaiFuContraller02.add(girlfriend2);
        WaiFuContraller02.add(girlfriend3);
        System.out.println(WaiFuContraller02.returnNum() + "");
        WaiFuContraller02.add(girlfriend4);
        System.out.println(WaiFuContraller02.returnNum() + "");
        WaiFuContraller02.remove("lzll");
        System.out.println(WaiFuContraller02.returnNum() + "");

    }
 }
 
 * static修饰的方法/成员变量是类方法/成员变量，是属于类的一个成员，它表明类这个元素是属于类的，只会在类初始化时被初始一次，而后的实例创建过程中不会再次创建，是实例所共有的概念，抽象到一个类的成员，就好像一般人都会走路，所以可以把走路的动作变成static方法，表明这是一个类的特征而不是实例所独有。
