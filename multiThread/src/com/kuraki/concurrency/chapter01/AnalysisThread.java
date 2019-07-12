package com.kuraki.concurrency.chapter01;

import java.util.concurrent.TimeUnit;

public class AnalysisThread {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            try {
                TimeUnit.MINUTES.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("Oh, I am be interrupted.");
            }
        });
        thread.start();

        // short block and make sure thread is started.
        TimeUnit.MILLISECONDS.sleep(2);
        thread.interrupt();
    }
}
