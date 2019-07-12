package com.kuraki.concurrency.chapter05;

import java.util.LinkedList;

public class EventQueue {

    // 静态内部类，存储事件（任务）
    public static class Event {
    }

    // eventQueue队列的阈值
    private final int max;
    // event队列，存储event对象
    private final LinkedList<Event> eventQueue = new LinkedList<>();
    // 默认队列的最大值为10
    private final static int DEFAULT_MAX_EVENT = 10;

    public EventQueue() {
        this(DEFAULT_MAX_EVENT);
    }

    public EventQueue(int size) {
        this.max = size;
    }

    public void offer(Event event) {
        synchronized (eventQueue) {
            // 在多线程调用offer方法时，if关键字会存在问题
//          if (eventQueue.size() >= this.max) {
            while (eventQueue.size() >= max) {
                try {
                    console(" the queue is full.");
                    eventQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            console("the new event is submitted");
            eventQueue.addLast(event);
            // notify方法只唤醒单个线程，notifyAll方法唤醒所有该对象阻塞的线程
//          eventQueue.notify();
            eventQueue.notifyAll();
        }
    }

    public Event take() {
        synchronized (eventQueue) {
//          if (eventQueue.isEmpty()) {
            while (eventQueue.isEmpty()) {
                try {
                    console("the queue is empty.");
                    eventQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Event event = eventQueue.removeFirst();
//          this.eventQueue.notify();
            this.eventQueue.notifyAll();
            console("the event " + event + " is handled.");
            return event;
        }
    }

    private void console(String message) {
        System.out.printf("%s：%s\n", Thread.currentThread().getName(), message);
    }


}
