package com.kuraki.concurrency.chapter22;

import java.util.concurrent.TimeUnit;

public class LatchTest {

    // 未超时的测试代码
    /*public static void main(String[] args) throws InterruptedException {
        // 定义Latch,limit为4
        Latch latch = new CountDownLatch(4);
        new ProgrammerTravel(latch,"Alex","Bus").start();
        new ProgrammerTravel(latch,"Ben","walking").start();
        new ProgrammerTravel(latch,"Jack","Subway").start();
        new ProgrammerTravel(latch,"Dillon","Bicycle").start();
        // 当前线程
        latch.await();
        System.out.println("== all of programmer arrived ==");
    }*/

    public static void main(String[] args) throws InterruptedException {
        // 定义Latch,limit为4
        Latch latch = new CountDownLatch(4);
        new ProgrammerTravel(latch, "Alex", "Bus").start();
        new ProgrammerTravel(latch, "Ben", "walking").start();
        new ProgrammerTravel(latch, "Jack", "Subway").start();
        new ProgrammerTravel(latch, "Dillon", "Bicycle").start();
        // 当前线程
        try {
            latch.await(TimeUnit.SECONDS, 5);
            System.out.println("== all of programmer arrived ==");
        } catch (WaitTimeOutException e) {
            e.printStackTrace();
        }
    }
}
