package com.kuraki.concurrency.chapter05.customLock;

import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

public class BooleanLockTest {
    // 定义BooleanLock
    private final Lock lock = new BooleanLock();

    // 使用try...finally语句确保lock每次都能正确释放
    public void syncMethod() {
        // 加锁
        lock.lock();
        try {
            int randomInt = ThreadLocalRandom.current().nextInt(10);
            System.out.println(Thread.currentThread() + " get the lock");
            TimeUnit.MILLISECONDS.sleep(randomInt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " release the lock monitor");
            // 释放锁
            lock.unlock();
        }
    }

    // 超时自动中断的方法
    public void syncMethodTimeOutAble() {
        try {
            lock.lock(1000);
            System.out.println(Thread.currentThread() + " get the lock.");
            int randomInt = ThreadLocalRandom.current().nextInt(10);
            TimeUnit.SECONDS.sleep(randomInt);
        } catch (InterruptedException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    /**
     * 多个线程通过lock()方法争抢锁
     */
    public static void main(String[] args) throws InterruptedException {
        BooleanLockTest blt = new BooleanLockTest();
        // 定义线程并启动
        IntStream.range(0, 10).mapToObj(i -> new Thread(blt::syncMethod)).forEach(Thread::start);
    }

    /**
     * 中断被阻塞的线程
     */
    @Test
    public void test1() throws InterruptedException {
        BooleanLockTest blt = new BooleanLockTest();
        new Thread(blt::syncMethod, "T1").start();
        TimeUnit.MILLISECONDS.sleep(4);
        Thread t2 = new Thread(blt::syncMethod, "T2");
        t2.start();
        TimeUnit.MILLISECONDS.sleep(10);
        t2.interrupt();
    }

    /**
     * 阻塞线程超时
     */
    @Test
    public void test2() {
        BooleanLockTest blt = new BooleanLockTest();
        new Thread(blt::syncMethod, "T1").start();
        try {
            TimeUnit.MILLISECONDS.sleep(2);
            Thread t2 = new Thread(blt::syncMethodTimeOutAble,"T2");
            t2.start();
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
