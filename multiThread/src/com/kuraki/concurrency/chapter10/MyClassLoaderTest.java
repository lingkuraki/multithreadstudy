package com.kuraki.concurrency.chapter10;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException,
            IllegalAccessException, InstantiationException, NoSuchMethodException {
        // 声明一个MyClassLoader
        BrokerDelegateClassLoader classLoader = new BrokerDelegateClassLoader();
        // 使用MyClassLoader加载HelloWorld
        Class<?> aClass = classLoader.loadClass("com.kuraki.concurrency.chapter10.HelloWorld");

        System.out.println(aClass.getClassLoader());
        // 1.注释
        Object helloWorld = aClass.newInstance();
        System.out.println(helloWorld);
        Method welcomeMethod = aClass.getMethod("welcome");
        String result = (String) welcomeMethod.invoke(helloWorld);
        System.out.println("Result：" + result);
    }
}
