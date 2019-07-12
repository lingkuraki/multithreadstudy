package com.kuraki.concurrency.chapter06;

import java.util.concurrent.TimeUnit;

public class ThreadGroupEnumerateThreads {

    public static void main(String[] args) throws InterruptedException {
        // 创建一个ThreadGroup
        ThreadGroup myGroup = new ThreadGroup("MyGroup");
        // 创建线程传入threadgroup
        Thread thread = new Thread(myGroup, () -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "MyThread");
        thread.start();

        TimeUnit.MILLISECONDS.sleep(2);
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();

        Thread[] list = new Thread[mainGroup.activeCount()];
        int recurseSize = mainGroup.enumerate(list);
        System.out.println("recurseSize = " + recurseSize);

        recurseSize = mainGroup.enumerate(list,false);
        System.out.println("recurseSize = " + recurseSize);
    }
}
