package task5;


public class BuyTest 
{
    public static void main(String[] args) {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        Glimmer glimmer = new Glimmer("cushion",10);
        var by1 = new BuyThread("佬1",glimmer,7);
        by1.setPriority(Thread.MAX_PRIORITY);
        by1.start();
        var by2 = new BuyThread("佬2",glimmer,5);
        by2.setPriority(Thread.NORM_PRIORITY);
        by2.start();

        try {
            Thread.sleep(100);
        } catch (Exception e) {
          
        }
        
        //补一次货
        glimmer.addNum(1);
        try{
            Thread.sleep(1);
            }
            catch(Exception e){
    
            }//就不用去强制暂停了
        //再补一次吧
        glimmer.addNum(6);

    }
}