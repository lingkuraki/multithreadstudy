package com.kuraki.concurrency.chapter25;

import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.net.Socket;

public class SocketCleaningTracker {

    // 定义ReferenceQueue
    private static final ReferenceQueue<Object> queue = new ReferenceQueue<>();

    static {
        // 启动Cleaner线程
        new Cleaner().start();
    }

    public static void tracker(Socket socket) {
        new Tracker(socket, queue);
    }

    private static class Cleaner extends Thread {
        private Cleaner() {
            super("SocketCleaningTracker");
            setDaemon(true);
        }

        @Override
        public void run() {
            for (; ; ) {
                try {
                    // 当Tracker被垃圾回收时会加入Queue中
                    Tracker tracker = (Tracker) queue.remove();
                    tracker.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Tracker是一个PhantomReference的子类
    private static class Tracker extends PhantomReference<Object> {

        private final Socket socket;

        /**
         * Creates a new phantom reference that refers to the given object and
         * is registered with the given queue.
         *
         * <p> It is possible to create a phantom reference with a <tt>null</tt>
         * queue, but such a reference is completely useless: Its <tt>get</tt>
         * method will always return null and, since it does not have a queue, it
         * will never be enqueued.
         *
         * @param socket the object the new phantom reference will refer to
         * @param q      the queue with which the reference is to be registered,
         */
        public Tracker(Socket socket, ReferenceQueue<? super Object> q) {
            super(socket, q);
            this.socket = socket;
        }

        public void close() {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
