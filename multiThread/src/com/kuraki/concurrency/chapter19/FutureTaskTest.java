package com.kuraki.concurrency.chapter19;

import java.util.concurrent.TimeUnit;

public class FutureTaskTest {

    /*// 无返回值的任务提交
    public static void main(String[] args) throws InterruptedException {
        // 定义不需要返回值的FutureService
        FutureService<Void, Void> service = FutureService.newService();
        // submit方法为立即返回的方法
        Future<?> future = service.submit(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("I am finish done.");
        });
        // get方法会使当前线程进入阻塞
        future.get();
    }*/


    // 有返回值的任务提交
    /*public static void main(String[] args) throws InterruptedException {
        // 定义有返回值的FutureService
        FutureService<String, Integer> service = FutureService.newService();
        // submit方法会立即返回
        Future<Integer> future = service.submit(input -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return input.length();
        }, "kuraki");
        // get方法使当前线程进入阻塞，最终会返回计算的结果
        System.out.println(future.get());
    }*/

    // 增加Callback后的Future任务提交
    public static void main(String[] args) {
        FutureService<String, Integer> service = FutureService.newService();
        service.submit(input -> {
            try {
                TimeUnit.MILLISECONDS.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return input.length();
        }, "kuraki", System.out::println);
    }

}
