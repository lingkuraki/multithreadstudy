package com.kuraki.concurrency.chapter24;

import com.kuraki.concurrency.chapter08.BasicThreadPool;
import com.kuraki.concurrency.chapter08.ThreadPool;

public class Operator {

    // 使用线程池替代为每一个请求创建线程
    private final ThreadPool threadPool = new BasicThreadPool(2, 6, 4, 1000);

    public void call(String business) {
        TaskHandler taskHandler = new TaskHandler(new Request(business));
        threadPool.execute(taskHandler);
    }
}
