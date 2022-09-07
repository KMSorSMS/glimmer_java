package space.glimmer.lumine;

public class IronSword extends Sword{
    public IronSword(){
        super(5);
    }
    @Override
    public void enchant() {
        this.increasePower(0.2);//提高20%攻击
    }
}
