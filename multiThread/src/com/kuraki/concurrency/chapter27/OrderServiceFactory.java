package com.kuraki.concurrency.chapter27;

public class OrderServiceFactory {

    // 将ActiveMessageQueue定义成static的目的是，保持其在整个JVM进程中是唯一的，并且ActiveDaemonThread会在此刻启动
    private final static ActiveMessageQueue activeMessageQueue = new ActiveMessageQueue();

    // 私有构造函数
    private OrderServiceFactory() {
    }

    // 返回OrderServiceProxy
    public static OrderService toActiveObject(OrderService orderService) {
        return new OrderServiceProxy(orderService, activeMessageQueue);
    }

    public static void main(String[] args) throws InterruptedException {
        // 在创建orderService时需要传递OrderService接口的具体实现
        OrderService orderService = OrderServiceFactory.toActiveObject(new OrderServiceImpl());
        orderService.order("hello", 453453);
        // 立即返回
        System.out.println("Return immediately");
        Thread.currentThread().join();

    }
}
