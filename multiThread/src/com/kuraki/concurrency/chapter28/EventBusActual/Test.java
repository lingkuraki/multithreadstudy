package com.kuraki.concurrency.chapter28.EventBusActual;

import com.kuraki.concurrency.chapter28.AsyncEventBus;
import com.kuraki.concurrency.chapter28.EventBus;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Test {

    public static void main(String[] args) throws IOException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
        final EventBus eventBus = new AsyncEventBus("TestBus", executor);
        // 注册
        eventBus.register(new FileChangeListener());
        DirectoryTargetMonitor monitor = new DirectoryTargetMonitor(eventBus, "E:\\background");
        monitor.startMonitor();
    }
}