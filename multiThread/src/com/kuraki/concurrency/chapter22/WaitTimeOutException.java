package com.kuraki.concurrency.chapter22;

// 当子线程执行超时的时候会抛出该异常
public class WaitTimeOutException extends Exception {

    public WaitTimeOutException(String message) {
        super(message);
    }
}
