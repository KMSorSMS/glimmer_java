package space.glimmer.lumine;

public class Apple extends Eatable implements Item {
    private static int hungerRecovery = 2;
    public Apple(int count){
        super(hungerRecovery,count);
    }
    public Apple(){
       super(hungerRecovery);
    }
}
