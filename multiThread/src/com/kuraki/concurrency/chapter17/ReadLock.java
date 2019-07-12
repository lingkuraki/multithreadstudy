package com.kuraki.concurrency.chapter17;

class ReadLock implements Lock {

    private final ReadWriteLockImpl readWriteLock;

    public ReadLock(ReadWriteLockImpl readWriteLock) {
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void lock() throws InterruptedException {
        // 使用Mutex作为锁
        synchronized (readWriteLock.getMutex()) {
            // 若此时有线程在进行写操作，或者有写线程在等待并且偏向写锁的标识为true时，就会无法获取读锁，只能被挂起
            while (readWriteLock.getWritingWriters() > 0
                    || (readWriteLock.isPreferWriter()) && readWriteLock.getWaitingWriters() > 0) {
                readWriteLock.getMutex().wait();
            }
            // 成功获取读锁，并且是readingReaders的数量增加
            readWriteLock.incrementReadingReaders();
        }
    }

    @Override
    public void unlock() {
        // 使用Mutex作为锁，并且进行同步
        synchronized (readWriteLock.getMutex()) {
            // 释放锁的过程就是使用当前reading的数量减1
            // 将preferWriter设置为true，可以使得writer线程获得更多的机会
            // 通知唤醒与Mutex关联的monitor waitSet 中的线程
            readWriteLock.decrementReadingReaders();
            readWriteLock.changePrefer(true);
            readWriteLock.getMutex().notifyAll();
        }
    }
}
