package com.kuraki.concurrency.chapter24;

import com.kuraki.concurrency.chapter08.BasicThreadPool;
import com.kuraki.concurrency.chapter08.ThreadPool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    // 服务端端口
    private final int port;
    // 定义线程池，该线程池是之前定义的线程池
    private ThreadPool threadPool;
    // 服务端Socket
    private ServerSocket serverSocket;

    // 通过构造函数传入端口
    public ChatServer(int port) {
        this.port = port;
    }

    // 默认使用13312端口
    public ChatServer() {
        this(13312);
    }

    public void startServer() throws IOException {
        // 创建线程池，初始化一个线程，核心线程数量为2，最大线程数为4，阻塞队列中最大可加入1000个任务
        this.threadPool = new BasicThreadPool(1, 4, 2, 1000);
        this.serverSocket = new ServerSocket(port);
        this.serverSocket.setReuseAddress(true);
        System.out.println("Chat server is started and listen at port: " + port);
        this.listen();
    }

    private void listen() throws IOException {
        for (; ; ) {
            // accept方法时阻塞方法，当有新的链接进入时才会返回，并且返回的是客户端的链接
            Socket client = serverSocket.accept();
            // 将客户端连接作为Request封装成对应的Handler然后提交给线程池
            this.threadPool.execute(new ClientHandler(client));
        }
    }

}
