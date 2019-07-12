package com.kuraki.concurrency.chapter27;

import java.util.Map;

/**
 * 收集每个接口的方法参数，并且提供execute方法共ActiveDaemonThread直接调用
 */
public abstract class MethodMessage {

    // 用于收集方法参数，如果又返回Future类型则一并收集
    protected final Map<String, Object> params;

    protected final OrderService orderService;

    public MethodMessage(Map<String, Object> params, OrderService orderService) {
        this.params = params;
        this.orderService = orderService;
    }

    // 抽象方法吗，扮演work-Thread的说明书
    public abstract void execute();
}
