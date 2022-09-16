package com.task6;

import org.reflections.Reflections;

import org.reflections.scanners.*;
import org.reflections.util.ConfigurationBuilder;



public class ReflectionTest {
    public static void main(String[] args) {
        // 扫包
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages("com.boothsun.reflections") // 指定路径URL
                .addScanners(new SubTypesScanner()) // 添加子类扫描工具
                .addScanners(new FieldAnnotationsScanner()) // 添加 属性注解扫描工具
                .addScanners(new MethodAnnotationsScanner() ) // 添加 方法注解扫描工具
                .addScanners(new MethodParameterScanner() ) // 添加方法参数扫描工具
                );

    }
}
