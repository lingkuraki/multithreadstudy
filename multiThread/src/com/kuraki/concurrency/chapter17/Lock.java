package com.kuraki.concurrency.chapter17;

public interface Lock {

    // 获取显示锁，没有获取锁的线程将被阻塞
    void lock() throws InterruptedException;
    // 释放获取的锁
    void unlock();
}
