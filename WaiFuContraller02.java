import java.util.ArrayList;

/**
 * 实现对于waifu对象的管理
 */
public class WaiFuContraller02 {
    private static ArrayList<Girlfriend02> waiFus = new ArrayList<>();// 通过list集合实例ArrayList进行管理

    public static void add(Girlfriend02 wife) {
        waiFus.add(wife);
    }

    public static int returnNum() {
        return waiFus.size();
    }

    /**
     * 根据姓名删除
     * 
     * @param name
     */

    public static void remove(String name) {
        for (int i = 0; i < waiFus.size(); i++) {
            if (waiFus.get(i).getName().equals(name)) {
                waiFus.remove(i);
            }
        }
    }

    /**
     * 根据姓名获得
     * 
     * @param name
     */

    public static Girlfriend02 get(String name) {
        for (int i = 0; i < waiFus.size(); i++) {
            if (waiFus.get(i).getName().equals(name)) {
                return waiFus.get(i);
            }
        }
        return null;
    }

    /**
     * 测试一下
     * 
     * @param args
     */
    public static void main(String[] args) {
        Girlfriend02 girlfriend1 = new Girlfriend02();
        girlfriend1.setName("lzl");
        girlfriend1.bailan();
        girlfriend1.eat();
        Girlfriend02 girlfriend2 = new Girlfriend02(19);
        Girlfriend02 girlfriend3 = new Girlfriend02(21);
        Girlfriend02 girlfriend4 = new Girlfriend02(30);
        girlfriend4.setName("bdyjy");
        girlfriend3.setName("lzll");
        WaiFuContraller02.add(girlfriend1);
        WaiFuContraller02.add(girlfriend2);
        WaiFuContraller02.add(girlfriend3);
        System.out.println(WaiFuContraller02.returnNum() + "");
        WaiFuContraller02.add(girlfriend4);
        System.out.println(WaiFuContraller02.returnNum() + "");
        WaiFuContraller02.remove("lzll");
        System.out.println(WaiFuContraller02.returnNum() + "");

    }
}
