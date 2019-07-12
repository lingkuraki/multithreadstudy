package com.kuraki.concurrency.chapter27;

import com.kuraki.concurrency.chapter27.activeObject.ActiveMessage;

import java.util.LinkedList;

public class ActiveMessageQueue {

    // 用于存放提交的MethodMessage消息
    private final LinkedList<ActiveMessage> messages = new LinkedList<>();

    public ActiveMessageQueue() {
        // 启动Worker线程
        new ActiveDaemonThread(this).start();
    }

    public void offer(ActiveMessage activeMessage) {
        synchronized (this) {
            messages.addLast(activeMessage);
            // 因为只有一个线程负责take数据，因此没有必要使用notifyAll方法
            this.notify();
        }
    }

    protected ActiveMessage take() {
        synchronized (this) {
            // 当MethodMessage队列中没有Message的时候，执行线程进入阻塞
            while (messages.isEmpty()) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            // 获取其中一个MethodMessage并且从队列中移除
            return messages.removeFirst();
        }
    }
}
