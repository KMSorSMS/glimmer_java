package task4;

import java.util.ArrayList;
import java.util.List;

public class GenericForList {
    // generic method printArray 
public  static  void  printList (  List<?> inputList  ) 
{ 
// Display array elements 
for  (  var  element  :  inputList  ){ 
System . out . printf (  " " +  element  ); 
} 
System . out . println (); 
} 
public static void main(String[] args) {
    ArrayList<String> list = new ArrayList<>();
    list.add("hello");
    list.add("my name is");
    list.add("Liam");
    ArrayList<Integer> list1 = new ArrayList<>();
    list1.add(1);
    list1.add(2);
    list1.add(3);
    System.out.println("-------------------");
    GenericForList.printList(list);
    System.out.println("-------------------");
    GenericForList.printList(list1);
}
}
