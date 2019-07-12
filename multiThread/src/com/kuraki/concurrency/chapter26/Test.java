package com.kuraki.concurrency.chapter26;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Test {

    public static void main(String[] args) {
        // 流水线上有5个工人
        final ProductionChannel channel = new ProductionChannel(5);
        AtomicInteger producitonNo = new AtomicInteger();
        // 流水线上有8个工作人员在传送带上不断地防止等待加工的半成品
        IntStream.range(1, 8).forEach(i -> new Thread(() -> {
                    while (true) {
                        channel.offerProduction(new Production(producitonNo.getAndIncrement()));
                        try {
                            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start()
        );
    }
}
