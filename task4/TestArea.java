package task4;

import java.util.ArrayList;
import java.util.List;

public class TestArea {
  // generic method printArray 
public  static  <  E  >  void  printList (  List<E> inputList  ) 
{ 
// Display array elements 
for  (  E  element  :  inputList  ){ 
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
    TestArea.printList(list);
    System.out.println("-------------------");
    TestArea.printList(list1);
}
}
