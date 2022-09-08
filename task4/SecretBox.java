package task4;

import java.util.ArrayList;

public class SecretBox<E>{
    private ArrayList<E> listItem; 
    public SecretBox(ArrayList<E> listItem){
        this.listItem = listItem;
    }
    public ArrayList<E> getListItem() {
        return listItem;
    }
    public E getItem(int index){
        return listItem.get(index);
    }
    public void addListItem(E item) {
        listItem.add(item);
    }
    public static void main(String[] args) {
        SecretBox<String> love = new SecretBox<String>(new ArrayList<String>());
        love.addListItem("cyh");
        love.addListItem("yzw");
        love.addListItem("Liam");
        System.out.println(love.getItem(1)); 
    }   
}
