package com.kuraki.concurrency.chapter27;

import com.kuraki.concurrency.chapter19.Future;
import com.kuraki.concurrency.chapter19.FutureService;
import com.kuraki.concurrency.chapter27.activeObject.ActiveMethod;

import java.util.concurrent.TimeUnit;

public class OrderServiceImpl implements OrderService {

    @ActiveMethod
    @Override
    public Future<String> findOrderDetails(long orderId) {
        // 使用19章中实现的Future返回结果
        return FutureService.<Long, String>newService().submit(input -> {
            try {
                // 通过休眠来模拟方法的执行比较耗时
                TimeUnit.SECONDS.sleep(5);
                System.out.println("process the orderID ->" + orderId);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "The order Details Information";
        }, orderId, null);
    }

    @ActiveMethod
    @Override
    public void order(String account, long orderId) {
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("process the order for account " + account + ",orderId " + orderId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
