package com.kuraki.concurrency.chapter03;

import java.util.concurrent.TimeUnit;

public class ThreadInterrupted {

    public static void main(String[] args) {
        // 判断当前线程是否被中断
        System.out.println("Main thread is interrupted?" + Thread.interrupted());
        // 中断当前线程
        Thread.currentThread().interrupt();
        // 判断当前线程是否已经被中断
        System.out.println("Main thread is interrupted?" + Thread.currentThread().isInterrupted());

        try {
            // 当前线程执行可中断方法
            TimeUnit.MINUTES.sleep(1);
        } catch (InterruptedException e) {
            // 获取中断信号
            System.out.println("I will be interrupted still.");
        }
    }
}