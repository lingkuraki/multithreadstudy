package com.kuraki.concurrency.chapter27;

import com.kuraki.concurrency.chapter27.activeObject.ActiveMessage;

public class ActiveDaemonThread extends Thread {

    private final ActiveMessageQueue queue;

    public ActiveDaemonThread(ActiveMessageQueue queue) {
        super("ActiveDaemonThread");
        this.queue = queue;
        // ActiveDaemonThread为守护线程
        setDaemon(true);
    }

    @Override
    public void run() {
        /**
         * 从MethodMessage队列中获取一个MethodMessage，然后执行execute方法
         */
        for (; ; ) {
            ActiveMessage activeMessage = this.queue.take();
            activeMessage.execute();
        }
    }
}
