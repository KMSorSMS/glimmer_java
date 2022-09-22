package com.task6;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class RunAB {
    public static void main(String[] args) {
        //建立beanfactory实例
        BeanFactoryIm beanFactory = new BeanFactoryIm();
        //得到并打印对象
        System.out.println(beanFactory.getBean("A"));
        System.out.println(beanFactory.getBean("B"));
    }
}

//我想在这里建立一个BeanInfo类来解析储存每个bean对象的信息，再建一个property对于方法参数信息的储存
class BeanInfo{
    //String 是class名字，property是信息
    private HashMap<String,Property> map = new HashMap<>();
    //String Key是名字，如A，value为class名
    private HashMap<String,String> mapId = new HashMap<>();
    //名字列表
    public List<String> list = new ArrayList<>();

    public String getClassName(String name){
        return mapId.get(name);
    }
    public Property getproperty(String name){
        return map.get(mapId.get(name));
    }

    public void read(){
        SAXReader reader = new SAXReader();
        Document document = null;
        try{
            document = reader.read(new File("E:\\schoollearn\\glimmer02\\task6a\\src\\main\\java\\source\\applicationContext.xml"));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        //得到根结点
        Element rootElement = document.getRootElement();
         //得到bean对象的信息
         List<Element> allBeans = rootElement.elements("bean");
         for(var ele:allBeans){//一个个的bean对象信息
            //类名
           String className =  ele.attributeValue("class");
           String name = ele.attributeValue("name");
           var property = new Property();
           property.setName(name);//设置名字
           List<Element> allProperties = ele.elements("property");
           //把信息保存
           for(Element pro:allProperties){
            if(pro.attributeValue("name").equals("str")){
                property.setStr(pro.attributeValue("value"));
            }
            else if(pro.attributeValue("name").equals("num")){
                property.setNum(Integer.parseInt(pro.attributeValue("value")));
            }
            else if(pro.attributeValue("name").matches("^ref.*")){
                property.setRef(pro.attributeValue("ref"));
            }
           }
           map.put(className, property);
           mapId.put(property.getName(),className);
           list.add(property.getName());
         }
        
    }
}

class Property{
    private String name = null;
    private String str = null;
    private Integer num =null;
    private String ref = null;
    public void setName(String name) {
        this.name = name;
    }
    public void setRef(String ref) {
        this.ref = ref;
    }
    public void setNum(Integer num) {
        this.num = num;
    }
    public void setStr(String str) {
        this.str = str;
    }
    public String getName() {
        return name;
    }
    public Integer getNum() {
        return num;
    }
    public String getRef() {
        return ref;
    }
    public String getStr() {
        return str;
    }
}


class BeanFactoryIm implements BeanFactory{

     /**
     * 保存所有bean对象，格式为 name : @52x2xa
     */
    private Map<String, Object> beans = new HashMap<>();

    public Object getBean(String str) {
        
        return beans.get(str);
    }
    //根据名字创建对象的方法
    public void createBean(String beanName,BeanInfo beanInfo){
        try{
            if(beans.keySet().contains(beanName)){
                return;
            }
            System.out.println(beanInfo.getClassName(beanName));
        Class<?> clazz = Class.forName(beanInfo.getClassName(beanName));
        //建立实例 
        Object obj = clazz.getConstructor().newInstance();
        Property pro = beanInfo.getproperty(beanName);//得到对象的信息
        if(pro.getStr()!=null){//有str
            clazz.getMethod("setStr", String.class).invoke(obj, pro.getStr());
        }
        if(pro.getNum()!=null){//有数值
            clazz.getMethod("setNum", int.class).invoke(obj, pro.getNum());
        }
        if(pro.getRef()!=null){//有依赖
            String refName = pro.getRef();
            if(beans.get(refName)!=null){//如果之前依赖已经实例化了
                clazz.getMethod("setRef"+refName, Class.forName(beanInfo.getClassName(refName))).invoke(obj,beans.get(pro.getRef()));
            }
            else{//否则就是没有初始化依赖
                //就再次调用去初始化
                createBean(pro.getRef(), beanInfo);
            }
        }
        beans.put(pro.getName(),obj);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    } 
    public BeanFactoryIm(){
        //上来先读取
        var beanInfo = new BeanInfo();
        beanInfo.read();
        //遍历所有bean对象的名字
        for(var beanName:beanInfo.list){
            //用创建方法
            createBean(beanName, beanInfo);
        }
    }
}