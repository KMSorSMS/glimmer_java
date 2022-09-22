package net.yzw;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 保存聊天室所有用户和对应Socket之间的映射关系-----
 * --这样服务器端就根据用户名获得对应的输出流
 * 当然，输入流就不用区分用户名和输入流了
 */

/**
 * 严格来说CrazyitMap已经不是一个标准的map结构了，但程序
 * 需要这样的数据结构来保存用户名和对应输出流之间的映射关系
 * 这样既可以通过用户名找到对应的输出流，也可以根据输出流找
 * 到对应的用户名
 */

// 通过组合HashMap对象来实现CrazyitMap，CrazyitMap要求value也不可重复
public class CrazyitMap<K, V> {
    // 创建一个线程安全的HashMap
    public Map<K, V> map = Collections.synchronizedMap(new HashMap<K, V>());

    // 根据value来删除指定项,这是本来hashmap没有的，因为map里面value不一定只会对应一个key
    public synchronized void removeByValue(Object value) {
        for (var key : map.keySet()) {
            if (map.get(key) == value || map.get(key).equals(value)) {
                map.remove(key);
                break;
            }
        }
    }

    /**
     * 获取所有value组成的Set集合
     * 
     * @return
     */
    public synchronized Set<V> valueSet() {
        Set<V> result = new HashSet<>();
        // 将map中所有value添加到result集合中
        map.forEach((key, value) -> result.add(value));
        return result;
    }

    /**
     * 根据calue查找key（这是普通map不具有的，而这里是因为value与key一一对应
     * 
     * @param val
     * @return
     */

    public synchronized K getKeyByValue(V val) {
        // 遍历所有key组成的集合
        for (var key : map.keySet()) {
            // 如果指定key对应的value与被搜索的value相同，则返回对应的key
            if (map.get(key) == val || map.get(key).equals(val)) {
                return key;
            }

        }
        return null;
    }

    /**
     * 实现put()方法，该方法不允许value重复
     * @param key
     * @param value
     * @return
     */
    public synchronized V put(K key, V value){
        //遍历所有value组成的集合
        for(var val: valueSet()){
            //如果某个value与试图放入集合的value相同
            //则抛出一个RuntimeException异常
            if(val.equals(value)&&val.hashCode()==value.hashCode()){
                throw new RuntimeException("CrazyitMap实例中不允许有重复value");
            }
        }
        return map.put(key, value);
    }
}
