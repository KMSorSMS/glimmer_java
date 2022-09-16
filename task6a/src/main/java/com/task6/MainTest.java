package com.task6;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainTest {

    public static void main(String[] args) {

        var beanFactory = new BeanFactoryImple();
       
        Glimmer glimmer = (Glimmer) beanFactory.getBean("glimmer");// beanFactory为BeanFactory实现类的实例
        System.out.println(glimmer);
    }

}

class BeanFactoryImple implements BeanFactory{
      /**
     * 保存所有bean对象，格式为 task6.xxx : @52x2xa
     */
    private Map<String, Object> beans = new HashMap<>();

    /**
     * 存储bean和name的关系
     */
    private Map<String, String> beanKeys = new HashMap<>();

    public BeanFactoryImple()
    {//扫描
        org.reflections.Reflections  reflections = new org.reflections.Reflections("com.task6.");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Component.class);
        for(var clazz:classes){
            registerBean(clazz);
        }
    }
    
    public Object registerBean(Class<?> clazz) {
        String nameClass = clazz.getName();
        String id = nameClass;
        // beanKeys.put(id, nameClass);
        try{
            Object bean = clazz.getConstructor().newInstance();
            for(Method m: clazz.getMethods()){
                if(m.isAnnotationPresent(Bean.class)){
                    bean = m.invoke(bean);
                    id = m.getAnnotation(Bean.class).value();
                }
            }
        beanKeys.put(id, nameClass);
        beans.put(nameClass, bean);
        return bean;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
        
    }
    public Object getBean(String str) {
        String nameClass = beanKeys.get(str);
        Object target = beans.get(nameClass);
        return target;
    }
}