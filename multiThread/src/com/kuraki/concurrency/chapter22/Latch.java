package com.kuraki.concurrency.chapter22;

import java.util.concurrent.TimeUnit;

public abstract class Latch {

    // 用于控制多少个线程完成任务时才能打开阀门
    protected int limit;

    // 通过构造函数传入limit
    public Latch(int limit) {
        this.limit = limit;
    }

    // 该方法会使得当前线程一直等待，直到所有的线程都完成工作，当阻塞的线程是允许被中断的
    public abstract void await() throws InterruptedException;

    // 超时等待的抽象方法
    public abstract void await(TimeUnit unit, long time) throws InterruptedException, WaitTimeOutException;

    // 当任务线程完成工作之后会调用该方法是的计数器减1
    public abstract void countDown();

    // 获取当前还有多少个线程没有完成任务
    public abstract int getUnArrived();
}
