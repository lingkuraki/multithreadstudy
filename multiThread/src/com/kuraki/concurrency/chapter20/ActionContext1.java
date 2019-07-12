package com.kuraki.concurrency.chapter20;

public class ActionContext1 {

    // 定义ThreadLocal，并且使用Supplier的方式重写initValue
    private static final ThreadLocal<Context> context = ThreadLocal.withInitial(Context::new);

    public static Context get() {
        return context.get();
    }

    // 每一个线程都会有一个独立的Context实例
    static class Context {
        // 在Context中的其它成员
        // private Configuration configuration;
        // private OtherResource otherResource;
    }
}
