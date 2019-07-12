package com.kuraki.concurrency.chapter21;

public class BalkingTest {

    public static void main(String[] args) {
        new DocumentEditThread("E:\\", "balking.txt").start();
    }
}
