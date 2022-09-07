package space.glimmer.lumine;

public class NetheriteSword  extends Sword {
    public NetheriteSword(){
        super(8);
    }
    @Override
    public void enchant() {
        this.increasePower(0.2);//提高20%攻击
    }
}
