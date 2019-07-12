package com.kuraki.concurrency.chapter05.customLock;

import java.util.List;
import java.util.concurrent.TimeoutException;

public interface Lock {

    void lock();

    void lock(long mills) throws InterruptedException, TimeoutException;

    void unlock();

    List<Thread> getBlockedThreads();
}