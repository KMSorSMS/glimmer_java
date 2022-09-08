task1

---

![image-20220907172315123](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220907172315123.png)

![image-20220907172340514](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220907172340514.png)

##### 编译运行使用`_gcd1`的代码产生的错误和编译运行使用`_gcd2`的代码产生的错误一样吗？讲讲编译时的错误和运行时的异常的区别。

不一样，前一个编译错误是在程序在编译生成.class文件过程中产生的错误，由java编译器检测出来，后面gcd2的错误是运行时错误，由jvm在运行时发现的ClassCastException，类型投射错误，此时.class文件已经生成，是在运行程序过程中检测出来的，这样的错误更加危险且隐蔽。

##### 为什么`_gcd2`产生的异常叫"Exception"，而爆栈时产生的异常叫“Error”？它们有什么区别？

Error类和Exception类的父类都是throwable类，他们的区别是：

###### Error类一般是指与<u>虚拟机相关的问题</u>，如<u>系统崩溃，虚拟机错误，内存空间不足，方法调用栈溢等</u>。对于这类错误的导致的应用程序中断，仅靠<u>程序本身无法恢复和预防</u>，遇到这样的错误，建议让程序终止。

######  Exception类表示程序可以处理的异常，<u>可以捕获且可能恢复</u>。遇到这类异常，应该尽可能处理异常，使程序恢复运行，而不应该随意终止异常。

######  Exception类又分为<u>运行时异常（Runtime Exception）</u>和<u>受检查的异常(Checked Exception )</u>。**运行时异常**，编译能通过，但是一运行就终止了，程序不会处理运行时异常，出现这类异常，程序会终止。         而**受检查的异常**，要么用try。。。catch捕获，要么用throws字句声明抛出，交给它的父类处理，否则编译不会通过。



-------

随身笔记：

1、异常处理语法结构中只有try块是必须的，也就是说是，如果没有try块，则不能有后面的catch块和finally块；catch块和finally 块都是可选的，但catch块和finally至少出现其中之一。

2、throws：当前方法不知道如何处理这种类型的异常，该异常应该由上一级调用者处理；如果main方法也不知道如何处理这种类型的异常，也可以使用throws声明抛出异常，这个异常就会交给JVM处理，而JVM处理异常的方法是---打印异常的跟踪栈信息，并中止程序运行，这也就是为什么我们的程序在遇到异常后自动结束的原因。

thows声明抛出的语法仅跟在方法签名之后，一旦使用throws语句声明抛出该异常，程序就无需使用try...catch块来捕获该异常了

某段代码中如果调用了一个带throws声明的方法，该方法抛出了一个checked异常（也就是非runtime异常----RuntimeException类及其子类的实例），就表明该方法希望它的调用者来处理，则调用该方法必须在try块中或者在另一个throws声明抛出异常的方法中

3、当程序出现错误时，系统会自动抛出异常；<u>除此之外，Java也允许程序自行抛出异常</u>，自行抛出异常通常由throw语句来完成（注意没有s哦）

因为一些操作可能会和我们的业务需求不符合，我们就需要去抛出异常

Attention：为什么要自定义的去抛出异常

ans:在通常情况下，程序很少会自行抛出系统异常，以为异常的类名通常也包含了该异常的有用信息。所以在选择抛出异常时，应该选择合适的异常类，从而可以明确地描述该异常的情况。所以程序往往就要自定义抛出异常

##### 关于自定义异常的语法：（现学的，笔记部分可以略过，仅自用

1. 都继承Exception类，（如果想定义Runtime异常应该继承RuntimeException基类。）

2. 通常是两个构造器，无参和一个带字符串（异常对象描述信息）（也就是异常对象 getMessage() 的返回值）的构造器

3. ![image-20220908082153485](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220908082153485.png)

   

-----------------

1. **学习如何自定义异常类型，然后完成tk_sky要求的自定义异常。这个异常类应该能在出现异常时保存下非法输入的对象（Object类型），然后提供方法使处理异常的程序能get到它。**
2. **学习异常的throw相关知识，使得改进后的`_gcd2`方法在出现非法输入（如`“0”`）时能抛出你自定义的异常。然后在调用他的main方法处catch它，使用异常的`printStackTrace()`方法将异常打印出来。在题解上附上你的代码和打印出异常的截图。**

![image-20220908082734787](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220908082734787.png)

![image-20220908082748550](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220908082748550.png)

哦，确实是打印异常跟踪栈也会在第一行打印异常的详细信息（也就是msg的内容）



--------------------------------------------------------------------

## Part 2. 怎么想都进不去吧

--------------------------

### Task 3.

学习泛型的知识，完成泛型方法`printArray`，使其能够打印各种类型的数组。

package task4;



public  class  GenericMethod 

{ 

*// generic method printArray* 

public  static  <  E  >  void  printArray (  E []  *inputArray*  ) 

{ 

*// Display array elements* 

for  (  E  element  :  *inputArray*  ){ 

System . out . printf (  " " +  element  ); 

} 

System . out . println (); 

} 

public  static  void  main(  String  *args* []  ) 

{ 

*// Create arrays of Integer, Double and Character* 

Integer []  intArray  =  {  1 ,  2 ,  3 ,  4 ,  5  }; 

Double []  doubleArray  =  {  1.1 ,  2.2 ,  3.3 ,  4.4  }; 

Character []  charArray  =  {  'H' ,  'E' ,  'L' ,  'L' ,  'O'  }; 

System .out . println (  "Array integerArray contains:"  ); 

printArray (  intArray  );  *// pass an Integer array* 

System . out . println (  "\nArray doubleArray contains:"  ); 

printArray (  doubleArray  );  *// pass a Double array* 

System . out . println (  "\nArray characterArray contains:"  ); 

printArray (  charArray  );  *// pass a Character array* 

} 

}

#### //ps:这个代码是我在网上抄的，确实是学的时候看到了就贴上来了

![image-20220908084200741](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220908084200741.png)

-------

##### 泛型学习笔记（自用，可跳过

问题

1. 集合对元素类型没有任何限制
2. 对象被“丢进”集合时，集合丢失了对象的状态信息，只知道它盛放的是Object，因此取出集合中元素后还需要进行强制类型的转换，有危险

解决

1. 允许程序在创建集合时指定集合元素的类型，比如List\<String\>,

   e.g. List\<String> strList = new ArrayList\<String>();

   

深入泛型：

1. 定义：

   就是允许在定义类、接口、方法时使用类型形参，这个类型形参（或叫泛型）将在声明变量、创建对象、调用方法时动态地指定

2. ![image-20220908092123183](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220908092123183.png)

   ![image-20220908092303109](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220908092303109.png)

3. ![image-20220908093157730](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220908093157730.png)

   

类型通配符的引入：

1. 当我们使用一个泛型类，比如把它放到方法里面的形参，我们在方法调用之前可能无法确定这个泛型类到底会被定为什么类型。
2. java的泛型与数组不同，它不支持型变，（Foo时Bar的子类，但是G\<Foo>不是G\<Bar>的子类.



细节语法：

1. ![image-20220908101420261](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220908101420261.png)

   因为根本无法知道是哪一个实例，而如果加入Object类那么很有可能不是这个泛型类的实际类型的实例

   但是可以加入：

   ![image-20220908101837229](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220908101837229.png)

2. <? extends xxx>设置上限（即是xxx的子类），<? super xxx>设置下限（即是xxx的父类）

   加和取操作看起来很容易混，但是要抓住核心：

   要在集合中加入元素，那必须是此元素是集合泛型定义的类的实例，所以只知道上限没办法加入，传入的集合很可能只是一个子类，你不知道是哪个，但是知道下限的话就能确定一定是xxx的父类，那么加入xxx的实例肯定可以。同样，取出元素时，要保证取出的元素的方法都是能用的，知道上限，那么对于上限的方法子类都有，那么一定能用，而下限无法知道上限具体是谁，不能保证一个类的方法都能用，不过由于object类是所有类的父类，所以可以强行取出来作为object类。





泛型方法：

1. 为什么需要![image-20220908093522675](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220908093522675.png)

2. ![image-20220908093813458](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220908093813458.png)

   

   

Attention：

![image-20220908104241320](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220908104241320.png)

![image-20220908104310523](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220908104310523.png)

泛型构造器：

![image-20220908104704482](C:\Users\yuanz\AppData\Roaming\Typora\typora-user-images\image-20220908104704482.png)

---------

### 如果tk_sky的数据使用List保存，又可以怎么写呢？学习并使用类型通配符完成代码。

-------------------------------

package task4;



import java.util.ArrayList;

import java.util.List;



public class GenericForList {

  *// generic method printArray* 

public  static  void  printList (  List<?> *inputList*  ) 

{ 

*// Display array elements* 

for  (  var  element  :  *inputList*  ){ 

System . out . printf (  " " +  element  ); 

} 

System . out . println (); 

} 

public static void main(String[] *args*) {

  ArrayList<String> list = new ArrayList<>();

  list.add("hello");

  list.add("my name is");

  list.add("Liam");

  ArrayList<\Integer\> list1 = new ArrayList<>();

  list1.add(1);

  list1.add(2);

  list1.add(3);

  System.out.println("-------------------");

  GenericForList.printList(list);

  System.out.println("-------------------");

  GenericForList.printList(list1);

}

}

---------------------------

task4

#### 学习泛型类，完成一个泛型类`SecretBox`，使得tk_sky以后可以把任意类型的数据存在一个`SecretBox`实例中，收纳整齐。

package task4;



import java.util.ArrayList;



public class SecretBox<E>{

  private ArrayList<E> listItem; 

  public SecretBox(ArrayList<E> *listItem*){

​    this.listItem = *listItem*;

  }

  public ArrayList<E> getListItem() {

​    return listItem;

  }

  public E getItem(int *index*){

​    return listItem.get(*index*);

  }

  public void addListItem(E *item*) {

​    listItem.add(*item*);

  }

  public static void main(String[] *args*) {

​    SecretBox<String> love = new SecretBox<String>(new ArrayList<String>());

​    love.addListItem("cyh");

​    love.addListItem("yzw");

​    love.addListItem("Liam");

​    System.out.println(love.getItem(1)); 

  }  

}