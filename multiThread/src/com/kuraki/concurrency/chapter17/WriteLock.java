package com.kuraki.concurrency.chapter17;

public class WriteLock implements Lock {

    private final ReadWriteLockImpl readWriteLock;

    public WriteLock(ReadWriteLockImpl readWriteLock) {
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void lock() throws InterruptedException {
        synchronized (readWriteLock.getMutex()) {
            try {
                // 首先使等待获取写入锁的数字加1
                readWriteLock.incrementWaitingWriters();
                // 如果此时有其他线程正在进行读操作、或者写操作，那么当前线程被挂起
                while (readWriteLock.getReadingReaders() > 0 || readWriteLock.getWritingWriters() > 0) {
                    readWriteLock.decrementWaitingWriters();
                }
            } finally {
                // 成功获取到了写入锁，使得等待获取写入锁的计数器减1
                this.readWriteLock.decrementWaitingWriters();
            }
            // 将正在写入的线程数量加1
            readWriteLock.incrementWritingWriters();
        }
    }

    @Override
    public void unlock() {
        synchronized (readWriteLock.getMutex()) {
            // 减少正在写入锁的线程计数器
            readWriteLock.decrementWritingWriters();
            // 将偏好状态修改为false,可以使得读锁将被最快速的获得
            readWriteLock.changePrefer(false);
            // 通知唤醒其它在Mutex monitor waitSet中的线程
            readWriteLock.getMutex().notifyAll();
        }
    }
}