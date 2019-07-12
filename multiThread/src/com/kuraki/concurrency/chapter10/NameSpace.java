package com.kuraki.concurrency.chapter10;

public class NameSpace {

    // 相同类加载器加载
/*    public static void main(String[] args) throws ClassNotFoundException {
        // 获取系统类加载器
        ClassLoader classLoader = NameSpace.class.getClassLoader();
        Class<?> aClass = classLoader.loadClass("com.kuraki.concurrency.chapter10.MyClassLoaderTest");
        Class<?> bClass = classLoader.loadClass("com.kuraki.concurrency.chapter10.MyClassLoaderTest");
        System.out.println(aClass.hashCode());
        System.out.println(bClass.hashCode());
        System.out.println(aClass == bClass);
    }*/

    // 相同类加载器加载同一个class
    /*public static void main(String[] args) throws ClassNotFoundException {

        MyClassLoader classLoader1 = new MyClassLoader("D:\\kuraki\\multiThread\\out\\production\\multiThread", null);
        MyClassLoader classLoader2 = new MyClassLoader("D:\\kuraki\\multiThread\\out\\production\\multiThread", null);
        Class<?> aClass = classLoader1.loadClass("com.kuraki.concurrency.chapter10.HelloWorld");
        Class<?> bClass = classLoader2.loadClass("com.kuraki.concurrency.chapter10.HelloWorld");
        System.out.println(aClass.getClassLoader());
        System.out.println(bClass.getClassLoader());
        System.out.println(aClass.hashCode());
        System.out.println(bClass.hashCode());
        System.out.println(aClass == bClass);
    }*/

    // 不同类加载器加载同一个class
    public static void main(String[] args) throws ClassNotFoundException {
        MyClassLoader classLoader1 = new MyClassLoader("D:\\kuraki\\multiThread\\out\\production\\multiThread", null);
        BrokerDelegateClassLoader classLoader2 = new BrokerDelegateClassLoader("D:\\kuraki\\multiThread\\out\\production\\multiThread", null);
        Class<?> aClass = classLoader1.loadClass("com.kuraki.concurrency.chapter10.HelloWorld");
        Class<?> bClass = classLoader2.loadClass("com.kuraki.concurrency.chapter10.HelloWorld");
        System.out.println(aClass.getClassLoader());
        System.out.println(bClass.getClassLoader());
        System.out.println(aClass.hashCode());
        System.out.println(bClass.hashCode());
        System.out.println(aClass == bClass);
    }
}
