package com.kuraki.concurrency.chapter08;

import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    public static void main(String[] args) throws InterruptedException {
        // 定义线程池，初始化线程数为2，核心线程数为4，最大线程数为6，任务队列最多允许1000个任务
        final ThreadPool threadPool = new BasicThreadPool(2, 6, 4, 1000);
        // 定义20个任务并且提交给线程池
        for (int i = 0; i < 20; i++) {
            threadPool.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                    System.out.println(Thread.currentThread().getName() + " is running and done.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        for (; ; ) {
            /*// 不断输出线程池的信息
            System.out.println("getActiveCount：" + threadPool.getActiveCount());
            System.out.println("getQueueSize：" + threadPool.getQueueSize());
            System.out.println("getCoreSize：" + threadPool.getCoreSize());
            System.out.println("getMaxSize：" + threadPool.getMaxSize());
            System.out.println("-----------------------------------------------------");
            TimeUnit.MILLISECONDS.sleep(5);*/

            TimeUnit.MILLISECONDS.sleep(12);
            // 线程在12秒之后将被shutdown
            threadPool.shutdown();
            // 使main线程join，方便通过工具观察堆栈信息
            // Thread.currentThread().join();
            System.out.println(threadPool.isShutdown());
        }
    }
}
