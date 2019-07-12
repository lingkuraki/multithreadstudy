package com.kuraki.concurrency.chapter18;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

// 不可变对象不允许被继承
public class FinalIntegerAccumulator {

    private final int init;

    // 构造时传入初始值
    public FinalIntegerAccumulator(int init) {
        this.init = init;
    }

    // 构造新的累加器，需要用到另外一个accumulator和初始值
    public FinalIntegerAccumulator(FinalIntegerAccumulator accumulator, int init) {
        this.init = accumulator.getValue() + init;
    }

    // 每次相加都会产生一个新的FinalIntegerAccumulator
    public FinalIntegerAccumulator add(int i) {
        return new FinalIntegerAccumulator(this, i);
    }

    public int getValue() {
        return this.init;
    }

    public static void main(String[] args) {
        // 定义累加器，并且将设置初始值为0
        FinalIntegerAccumulator accumulator = new FinalIntegerAccumulator(0);
        // 定义三个线程，并且分别启动
        IntStream.range(0, 3).forEach(i -> new Thread(() -> {
            int inc = 0;
            while (true) {
                // 首先获得old value
                int oldValue = accumulator.getValue();
                // 然后调用add方法计算
                int result = accumulator.add(inc).getValue();
                System.out.println(oldValue + "+" + inc + "=" + result);
                if ((inc + oldValue) != result) {
                    System.out.println("ERROR: " + oldValue + "+" + inc + "=" + result);
                }
                inc++;
                // 模拟延迟
                slowly();
            }
        }).start());
    }

    private static void slowly() {
        try {
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
