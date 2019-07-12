package com.kuraki.concurrency.chapter28;

import java.util.concurrent.ThreadPoolExecutor;

public class AsyncEventBus extends EventBus {

    AsyncEventBus(String busName, EventExceptionHandler exceptionHandler, ThreadPoolExecutor executor) {
        super(busName, null, executor);
    }

    public AsyncEventBus(String testBus, ThreadPoolExecutor executor) {
        this("default-async", null, executor);
    }

    public AsyncEventBus(EventExceptionHandler exceptionHandler, ThreadPoolExecutor executor) {
        this("default-async", exceptionHandler, executor);
    }
}
