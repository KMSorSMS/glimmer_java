package space.glimmer.lumine;

public class Cake extends Eatable {
    private static int hungerRecovery = 5;
    public Cake(int count){
        super(hungerRecovery,count);
    }
    public Cake(){
       super(hungerRecovery);
    }
    
}
