package space.glimmer.lumine;

public class Eatable implements Item{
    protected int count;
    protected int hungerRecovery;
    protected Eatable(int hungerRecovery,int count) {
        this.count = count;
        this.hungerRecovery = hungerRecovery;
    }

    protected Eatable(int hungerRecovery) {
        this.hungerRecovery = hungerRecovery;
    }
    protected Eatable(){

    }

    public void setCount(int count) {
        this.count = count;
    }
    public void eat() {
        System.out.printf("通过吃东西获得了" + hungerRecovery + "点饥饿值");
        Game.player.hunger+=hungerRecovery;
    }
    //测试
    // public static void main(String[] args) {
    //     new GoldenApple().eat();
    //     new Apple().eat();
    // }
}
