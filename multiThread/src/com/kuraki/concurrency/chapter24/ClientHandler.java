package com.kuraki.concurrency.chapter24;

import com.kuraki.concurrency.chapter25.SocketCleaningTracker;

import java.io.*;
import java.net.Socket;

// ChatHandler同样是一个Runnable接口的实现
public class ClientHandler implements Runnable {

    // 客户端的socket连接
    private final Socket socket;

    // 客户端的identity
    private final String clientIdentity;

    // 通过构造函数传入客户端连接
    public ClientHandler(final Socket socket) {
        this.socket = socket;
        this.clientIdentity = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
    }

    @Override
    public void run() {
        try {
            this.chat();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 任务执行结束时，执行释放资源的工作
            this.release();
        }
    }

    private void release() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Throwable e) {
            if (socket != null) {
                // 将Socket实例加入Tracker中
                SocketCleaningTracker.tracker(socket);
            }
        }
    }

    private void chat() throws IOException {
        BufferedReader bufferedReader = wrap2Reader(this.socket.getInputStream());
        PrintStream printStream = wrap2Print(this.socket.getOutputStream());
        String received;
        while ((received = bufferedReader.readLine()) != null) {
            // 将客户端发送的消息输出到控制台
            System.out.printf("client:%s-message:%s\n", clientIdentity, received);
            if (received.equals("quit")) {
                // 如果客户端发送了quit指令，则断开与客户端的链接
                write2Client(printStream, "client will close.");
                socket.close();
                break;
            }
            // 向客户端发送消息
            write2Client(printStream, "Server: " + received);
        }

    }

    // 将输入字节流封装到BufferedReader缓冲字符流中
    private BufferedReader wrap2Reader(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    // 将输出字节流封装成PrintStream
    private PrintStream wrap2Print(OutputStream outputStream) {
        return new PrintStream(outputStream);
    }

    // 该方法主要用于向客户端发送消息
    private void write2Client(PrintStream print, String message) {
        print.println(message);
        print.flush();
    }


}