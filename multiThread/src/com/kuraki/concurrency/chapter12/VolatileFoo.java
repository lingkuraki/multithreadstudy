package com.kuraki.concurrency.chapter12;

import java.util.concurrent.TimeUnit;

public class VolatileFoo {
    // init_value的最大值
    private final static int MAX = 5;
    // init_value的初始值、
    private static volatile int initValue = 0;

    public static void main(String[] args) {
        // 启动一个Reader线程，当发现local_value和init_value不同时，则输出init_value被修改的信息
        new Thread(() -> {
            int localValue = initValue;
            while (localValue < MAX) {
                if (initValue != localValue) {
                    System.out.printf("The initValue is updated to [%d]\n", initValue);
                    // 对localValue进行重新赋值
                    localValue = initValue;
                }
            }
        }, "Reader").start();

        // 启动一个Updater线程，主要用于对initValue的修改，对localValue>=5的时候则退出生命周期
        new Thread(() -> {
            int localValue = initValue;
            while (localValue < MAX) {
                // 修改initValue
                System.out.printf("The initValue will be changed to [%d]\n", ++localValue);
                initValue = localValue;
                try {
                    // 暂时休眠，目的是为了使Reader线程能够来得及输出变化内容
                    TimeUnit.MILLISECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Updater").start();
    }
}
