package com.task6;



@Component
public class Glimmer
{
    private String location;      //工作室地点
    private int memberNum;        //工作室人数
    public Glimmer(String string, int i) {
        location = string;
        memberNum = i;
    }
    public Glimmer(){}
    public void setLocation(String location) {
        this.location = location;
    }
    public void setMemberNum(int memberNum) {
        this.memberNum = memberNum;
    }
    @Override
    public String toString(){
        return "Glimmer{"+"location="+"\'"+location+"\'"+",MemberNum="+"\'"+memberNum+"\'}";
    }
    @Bean("glimmer")
    public Glimmer getGlimmer() {
        return new Glimmer("三教401",41);
    }
}