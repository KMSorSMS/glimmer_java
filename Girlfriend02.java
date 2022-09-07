public class Girlfriend02 {
    private String name = "cyh";
    private int age = 18;
    private int height = 165;
    private int facialDegree = 9;//(out of 10);
    public Girlfriend02(int age){
        this.age = age;
    }
    public Girlfriend02(){

    }
    public void dating(){
        System.out.println(name+"在dating");
    }
    public void eat(){
        System.out.println(name+"在吃饭");
    }
    public void bailan(){
        System.out.println(name+"摆烂中！");
    }
    public void study(){
        System.out.println(name+"学习");
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    // public static void main(String[] args){
    //     Girlfriend02 girlfriend1 = new Girlfriend02();
    //     girlfriend1.setName("cyh");
    //     girlfriend1.bailan();
    //     girlfriend1.eat();
    //     Girlfriend02 girlfriend2 = new Girlfriend02(19);

    // } 
}
