package com.task6;

import java.io.File;
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

class BeanFactoryIm implements BeanFactory{

     /**
     * 保存所有bean对象，格式为 name : @52x2xa
     */
    private Map<String, Object> beans = new HashMap<>();

    public Object getBean(String str) {
        
        return beans.get(str);
    }
    public BeanFactoryIm(){
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
        //必须先实例化A
        for(var ele:allBeans){
            try{
                // System.out.println("Element："+ele.asXML()+"\n"+ele.attributeValue("name"));
                if(ele.attributeValue("name").equals("A")){
                    Class clazz = A.class;
                    A bean = (A)clazz.getConstructor().newInstance();
                    List<Element> allProperties = ele.elements("property");
                for(Element property:allProperties){
                    if(property.attributeValue("name").equals("str")){
                        ((A)bean).setStr(property.attributeValue("value"));
                    }
                    else if(property.attributeValue("name").equals("num")){
                        ((A)bean).setNum(Integer.parseInt(property.attributeValue("value")));
                    }
                    beans.put(ele.attributeValue("name"), bean);
                }
                //然后实例化B
                for(var eleB:allBeans){
                    if(eleB.attributeValue("name").equals("B")){
                        B beanB = (B)(B.class).getConstructor().newInstance();
                        beanB.setRefA(bean);
                        beans.put(eleB.attributeValue("name"), beanB);
                    }
                }
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}