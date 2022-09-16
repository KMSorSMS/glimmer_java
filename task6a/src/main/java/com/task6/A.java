package com.task6;
public class A {
    private String str;
    private int num;
    public void setStr(String str) {
        this.str = str;
    }
    public void setNum(int num) {
        this.num = num;
    }
    @Override
    public String toString(){
        return "A{"+"str="+"\'"+str+"\'"+" ,num="+num+"}";
    }
}