package space.glimmer.lumine;

import java.util.ArrayList;
import java.util.Random;

class Player {
    double hunger;
    double health;
    String name;
    ArrayList<Item> inventory;

    Player(String name) {
        this.name = name;
        health = 20;
        hunger = 20;
        inventory = new ArrayList<>();
    }

    public void play() {
        boolean hasDiamondSword = false;
        while (Game.day <= 30) {
            if (Game.gameOver)
                return;
            // 在这里开始玩游戏吧!
            if (!hasDiamondSword) { // 没有钻石剑，好害怕，先挖矿！
                for (Item item : inventory) {
                    if (item instanceof Diamond && ((Diamond) item).count >= 10) {
                        Game.make(2);
                        hasDiamondSword = true;
                        break;
                    }
                }
                if (hasDiamondSword)
                    continue;
                Game.mine();
                continue;
            }
            Monster nextMonster = Game.getNextMonster();
            // 如果下一只怪是苦力怕，而且我还不饿,赶紧跑！
            if (nextMonster instanceof Creeper && hunger >= 10) {
                nextMonster.runAway();
                continue;
            }
            // 有钻石剑且状态良好，直接莽！
            if (hunger >= 10 && health >= 10) {
                nextMonster.attack();
                continue;
            }
            for (Item item : inventory) { // 状态不好，补充状态！
                if ((item instanceof Eatable && ((Eatable) item).count > 0)) {
                    ((Eatable) item).eat();
                }
            }
            //如果都是末影龙了，就拼了
            if(health>=4&&hunger>=4&&nextMonster instanceof EnderDragon){
                nextMonster.attack();
                continue;
            }
            Game.adventure(); // 打不了怪就去收集食物吧

        }
    }
}

public class Game {
    static int day = 0;
    static boolean gameOver = false;
    static Player player;
    static int monsterNow = 0;
    static ArrayList<Monster> monsters = new ArrayList<>();

    static void mine() { // 玩家选择挖矿
        int num1 = new Random().nextInt(6) + 1;
        int num2 = new Random().nextInt(3) + 1;
        int num3 = new Random().nextInt(3);
        Iron iron = new Iron();
        iron.count = num1;
        Diamond diamond = new Diamond();
        diamond.count = num2;
        Netherite netherite = new Netherite();
        netherite.count = num3;
        boolean tag1 = false;
        for (Item item : player.inventory) {
            if (item instanceof Iron) {
                tag1 = true;
                ((Iron) item).count += num1;
            }
        }
        if (!tag1)
            player.inventory.add(iron);// 如果用户的库存里面都没有铁

        /**
         * 相同的逻辑，先看用户里面有没有对应要添加的物品---钻石
         */
        boolean tag2 = false;
        for (Item item : player.inventory) {
            if (item instanceof Diamond) {
                tag2 = true;
                ((Diamond) item).count += num1;
            }
        }
        if (!tag2)
            player.inventory.add(diamond);

        /**
         * 把下界石加入进去
         */
        boolean tag3 = false;
        for (Item item : player.inventory) {
            if (item instanceof Netherite) {
                tag3 = true;
                ((Netherite) item).count += num3;
            }
        }
        if (!tag3)
            player.inventory.add(netherite);
        /**
         * 报告挖矿挖了多少
         */
        System.out.println(player.name + "通过挖矿获得了" + num1 + "个铁，" + num2 + "个钻石，" + num3 + "个下届合金！");
        // 表明这个方法（行为）是要耗时一天的
        nextDay();
    }

    static void adventure() { // 玩家选择冒险
        int num1 = new Random().nextInt(4) + 1;// 判断得到哪一类得东西，1苹果，2蛋糕，3金苹果
        int num2 = new Random().nextInt(3) + 1;// 看是多少，
        switch (num1) {
            case 1:
                boolean tag = false;
                for (Item item : player.inventory) {
                    if (item instanceof Apple) {
                        tag = true;
                        ((Apple) item).count += num1;
                    }
                }
                if (!tag) {
                    player.inventory.add(new Apple(num2));
                }
                System.out.println(player.name + "通过探索获得了" + num2 + "个苹果！");
            case 2:
                boolean tag2 = false;
                for (Item item : player.inventory) {
                    if (item instanceof Cake) {
                        tag2 = true;
                        ((Cake) item).count += num1;
                    }
                }
                if (!tag2) {
                    player.inventory.add(new Cake(num2));
                }
                System.out.println(player.name + "通过探索获得了" + num2 + "个蛋糕！");
            case 3:
                boolean tag3 = false;
                for (Item item : player.inventory) {
                    if (item instanceof GoldenApple) {
                        tag3 = true;
                        ((GoldenApple) item).count += num1;
                    }
                }
                if (!tag3) {
                    player.inventory.add(new GoldenApple(num2));
                }
                System.out.println(player.name + "通过探索获得了" + num2 + "个金苹果！");
        }
        nextDay();
    }

    static void make(int id) { // 玩家选择合成武器
        // 1:铁剑 2：钻石剑 3：下界合金剑
        boolean materialAvailable = false;
        switch (id) {
            case 1:
                for (Item item : player.inventory) {
                    if (item instanceof Iron && ((Iron) item).count >= 10) {
                        ((Iron) item).count -= 10;
                        player.inventory.add(new IronSword());
                        System.out.println(player.name + "合成了铁剑！");
                        materialAvailable = true;
                        break;
                    }
                }
            case 2:
                for (Item item : player.inventory) {
                    if (item instanceof Diamond && ((Diamond) item).count >= 10) {
                        ((Diamond) item).count -= 10;
                        player.inventory.add(new DiamondSword());
                        System.out.println(player.name + "合成了钻石剑！");
                        materialAvailable = true;
                        break;
                    }
                }
            case 3:
                for (Item item : player.inventory) {
                    if (item instanceof Netherite && ((Netherite) item).count >= 10) {
                        ((Netherite) item).count -= 10;
                        player.inventory.add(new Netherite());
                        System.out.println(player.name + "合成了下届合金剑！");
                        materialAvailable = true;
                        break;
                    }
                }
        }
        if (!materialAvailable) {
            System.out.println("材料不够！合成失败！");
        }
        nextDay();
    }

    static Monster getNextMonster() {
        return monsters.get(monsterNow);
    }

    static void nextDay() {
        // 第二天的初始判断，看看死了没
        if (Game.gameOver)
            return;
        // 先看是不是饿了，饿了就减生命
        if (player.hunger <= 0) {
            player.health -= 5;
            System.out.println("由于太饿了，" + player.name + "损失了5点生命值！");
        }
        // 当然了，减少完生命就再看看死了没
        if (player.health <= 0) {
            System.out.println(player.name + "去世了！游戏失败！gg！");
            gameOver = true;
            return;
        }
        // 如果吃得很饱那就要么回到20初始值，这里有bug要是我吃得多，加的生命都超过了20，反而减少了生命，或者如果生命低得话就+2
        if (player.hunger >= 16) {
            player.health = Math.min(20, player.health + 2);
            System.out.println("吃饱喝足，" + player.name +
                    "回复了2点生命值！");
        }
        day++;
        player.hunger = Math.max(0, player.hunger - 2);
        System.out.println("第" + day + "天结束！今天健康值为" + player.health + "，饥饿值为" + player.hunger);
        if (day >= 30) {
            System.out.println("30天生存时限到！你挑战了" + monsterNow + "只怪物！未挑战全部怪物，游戏失败！");
            gameOver = true;
        }
    }

    public static void main(String[] args) {
        monsters.add(new Zombie());
        monsters.add(new Creeper());
        monsters.add(new Zombie());
        monsters.add(new Zombie());
        monsters.add(new Creeper());
        monsters.add(new EnderDragon());
        player = new Player("Lumine");
        player.play();
    }
}