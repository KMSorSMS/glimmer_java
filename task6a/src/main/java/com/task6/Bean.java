package com.task6;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)//在运行java程序时，JVM也可获取到注解信息
@Target({ElementType.METHOD,ElementType.FIELD})//只能修饰类、接口或枚举定义
public @interface Bean {
    String value();
}
