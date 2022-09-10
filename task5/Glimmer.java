package task5;


public class Glimmer {
    private String Id; // 代表各工作室财产编号，抱枕为cushion
    private int remainNum; // 代表剩余数量

    public Glimmer(String Id, int remainNum) {
        this.Id = Id;
        this.remainNum = remainNum;
    }

    /**
     * 二---通过同步方法实现锁
     * 其实这里不应该是对getRemainNum进行同步，实际上应该把买这个动作合并为一个方法
     * 然后对这个方法加锁，但是这里我们只能在原有的基础上少许改动，所以就直接对判断这一步加入锁,
     * BUT:
     * 这也是不行滴
     * 
     * @return
     */
    public int getRemainNum() {
        return remainNum;
    }

    public String getId() {
        return Id;
    }

    public void setRemainNum(int remainNum) {
        this.remainNum = remainNum;
    }
    /**
     * 包装一个完整的买方法来实现同步锁
     * @param buyNum
     */

    public synchronized void addNum(int add){
        
        remainNum += add;
        System.out.println("微光工作室进货了"+add+"个微光娘抱枕！"+"\n还剩余"+remainNum+"个抱枕");
        //告诉在这个监视器上等待的线程我进货了
        notify();
    }

    public synchronized void buy(int buyNum,BuyThread bt) {
       try{
        if (getRemainNum() >= buyNum) {
            System.out.println(Thread.currentThread().getName() + "已买入" + buyNum + "个微光娘抱枕！");
            setRemainNum(getRemainNum() - buyNum);
            System.out.println("还剩" + getRemainNum() + "个抱枕");
           bt.setFlag(true); ;//那就是买到了
        } else {
            System.out.println("剩余量不足，"+Thread.currentThread().getName()+"购买失败");
            bt.setFlag(false);
            //那就等等吧，嘻嘻
            wait();
        }
    }
    catch(Exception ie){
        ie.printStackTrace();
    }
    }
}