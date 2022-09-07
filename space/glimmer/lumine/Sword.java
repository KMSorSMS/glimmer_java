package space.glimmer.lumine;

public abstract class Sword implements Item {
    protected double power;
    public Sword(double power){
        this.power = power;
    }
    //抽象方法---附魔
    public abstract void enchant();
    protected void increasePower(double percent){
        power *= (1+percent);
    }
}
