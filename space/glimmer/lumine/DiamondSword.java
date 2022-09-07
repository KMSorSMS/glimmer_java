package space.glimmer.lumine;

public class DiamondSword  extends Sword {
    public DiamondSword(){
        super(7);
    }
    @Override
    public void enchant() {
        this.increasePower(0.2);//提高20%攻击
    }
}
