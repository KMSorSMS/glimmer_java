package com.task6;

public class B {
    private A refA;
    public void setRefA(A refA) {
        this.refA = refA;
    }
    @Override
    public String toString(){
        return "B{"+"refA="+refA+"}";
    }
}
