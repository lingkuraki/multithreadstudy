package com.kuraki.concurrency.chapter28;

/**
 * 非常普通的对象
 */
public class SimpleObject {
    /**
     * subscribe方法，比如使用@Subscribe标记，并且是void类型且有一个参数
     */
    @Subscribe(topic = "alex-topic")
    public void test2(Integer x) {
    }

    @Subscribe(topic = "test-topic")
    public void test3(Integer x) {
    }
}
