package com.kuraki.concurrency.chapter15;

import java.util.concurrent.TimeUnit;

public class ObservableTest {

    public static void main(String[] args) {

        /*Observable observableThread = new ObservableThread<>(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" finished done.");
            return null;
        });
        observableThread.start();*/

        final TaskLifecycle<String> lifecycle = new TaskLifecycle.EmptyLifecycle<String>() {
            @Override
            public void onFinish(Thread thread, String result) {
                System.out.println("The result is " + result);
            }
        };

        Observable observableThread = new ObservableThread<>(lifecycle, () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" finished done.");
            return "Hello Observer";
        });
        observableThread.start();

    }
}
