package com.kuraki.concurrency.chapter05.customLock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;


public class BooleanLock implements Lock {

    // 当前拥有锁的线程
    private Thread currentThread;
    // 锁开关，false表示未获取锁或者已经释放锁，true表示锁已经被currentThread获取
    private boolean locked = false;
    // 存储获取当前线程持有的锁时进入阻塞状态的线程
    private final List<Thread> blockedList = new ArrayList<>();

    @Override
    public void lock() {
        // 1.使用同步代码块的方式进行方法同步
        synchronized (this) {
            // 2.如果当前锁已经被某个线程获得，则该线程将加入到阻塞队列，并且使当前线程wait释放对this monitor的所有权
            while (locked) {
                blockedList.add(Thread.currentThread());
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 3.如果当前锁没有被其他线程获取，则尝试从阻塞队列中删除自己。
            // 这一步，主要考虑当前线程可能是从wait set中唤醒的
            blockedList.remove(Thread.currentThread());
            // 4.设置locked开关为true
            this.locked = true;
            // 5.记录获取锁的线程
            this.currentThread = Thread.currentThread();
        }
    }

    @Override
    public void lock(long mills) throws InterruptedException, TimeoutException {
        synchronized (this) {
            // 1.如果参数不合法，则执行代码块中的语句
            if (mills < 0) {
                // 这里默认调用无参的lock方法，但实际中应该抛出参数不合法的异常
                this.lock();
            } else {
                long remainingMills = mills;
                long endMills = System.currentTimeMillis() + remainingMills;
                while (locked) {
                    // 2. 如果剩余时间≤0，则说明在指定的mills时间内，当前线程都没有获取到锁，则抛出超时异常
                    if (remainingMills <= 0) {
                        throw new TimeoutException("can not get the lock during " + mills);
                    }
                    // 3.如果阻塞队列中不包含当前线程，则将当前线程加入到阻塞队列中去
                    if (!blockedList.contains(Thread.currentThread())) {
                        blockedList.add(Thread.currentThread());
                    }
                    // 4.该值最开始是由其他线程传入的，但是在多次调用后会重新计算
                    this.wait(remainingMills);
                    // 5.重新计算剩余时间
                    remainingMills = endMills - System.currentTimeMillis();
                }
                // 6.获取该锁，从阻塞队列中移除当前线程。并设置locked为true,记录下当前线程
                blockedList.remove(Thread.currentThread());
                this.locked = true;
                this.currentThread = Thread.currentThread();
            }
        }
    }

    /**
     * 将locked状态修改为false,并且唤醒wait set中的其他线程，再次抢夺资源（锁）
     * 注意：哪个线程加的锁只能由那个线程来解锁
     */
    @Override
    public void unlock() {
        synchronized (this) {
            // 1.判断当前线程是否是持有锁的那个线程
            if (currentThread == Thread.currentThread()) {
                // 2.将锁开关置为false
                this.locked = false;
                // 3.唤醒wait set中所有wait等待的线程
                this.notifyAll();
            }
        }
    }

    @Override
    public List<Thread> getBlockedThreads() {
        return Collections.unmodifiableList(blockedList);
    }
}
