package task5;

public class BuyThread extends Thread {
    private Glimmer glimmer;
    private int buyNum; // 代表购买数量
    private boolean flag = false;//标记买没买到

    public void run() {
        /**
         * 第一种同步方式----同步代码块
         * 锁住glimmer对象，因为它的数据是公共的
         */
        // synchronized (glimmer) {
            // 如果剩余数量足够
        //     if (glimmer.getRemainNum() >= buyNum) {
        //         System.out.println(getName() + "已买入" + buyNum + "个微光娘抱枕！");
        //         glimmer.setRemainNum(glimmer.getRemainNum() - buyNum);
        //         System.out.println("还剩" + glimmer.getRemainNum() + "个抱枕");
        //     } else
        //         System.out.println("剩余量不足，购买失败");
        // }
        /**
         * 第二种方式，对于glimmer的买方法进行包装
         *
         */
        while(!flag){//没买到那不得一直抢？？？
        glimmer.buy(buyNum,this);
        }
        System.out.println(getName()+"退出了争夺");
    }
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    // }

    public BuyThread(String name, Glimmer glimmer, int buyNum) {
        super(name);
        this.glimmer = glimmer;
        this.buyNum = buyNum;
    }
}