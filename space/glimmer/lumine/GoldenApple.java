package space.glimmer.lumine;

public class GoldenApple extends Eatable{
    private static int hungerRecovery = 2;
    public GoldenApple(int count){
        super(hungerRecovery,count);
    }
    public GoldenApple(){
       super(hungerRecovery);
    }
    //重写eat（）方法
    public void eat(){
        System.out.println("通过吃东西回复了"+super.hungerRecovery+"点饥饿值"+super.hungerRecovery+"点生命值");
        Game.player.health += hungerRecovery;
        Game.player.hunger += hungerRecovery;
    }
}
