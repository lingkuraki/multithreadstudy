package com.kuraki.concurrency.chapter27;

import com.kuraki.concurrency.chapter19.Future;

import java.util.Map;

public class FindOrderDetailsMessage extends MethodMessage {

    public FindOrderDetailsMessage(Map<String, Object> params, OrderService orderService) {
        super(params, orderService);
    }

    @Override
    public void execute() {
        // 1.执行orderService的findOrderDetails方法
        Future<String> realFuture = orderService.findOrderDetails((Long) params.get("orderId"));
        ActiveFuture<String> activeFuture = (ActiveFuture<String>) params.get("activeFuture");
        try {
            // 2.调用orderServiceImpl返回的Future.get(),此方法会导致阻塞直到findOrderDetails方法完全执行结束
            String result = realFuture.get();
            // 3.当findOrderDetails执行结束时，将结果通过finish的方法传递给activeFuture
            activeFuture.finish(result);
        } catch (InterruptedException e) {
            activeFuture.finish(null);
        }
    }
}
