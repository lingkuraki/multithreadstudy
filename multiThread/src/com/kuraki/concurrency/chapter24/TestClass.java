package com.kuraki.concurrency.chapter24;

import java.io.IOException;

public class TestClass {

    public static void main(String[] args) throws IOException {
        new ChatServer().startServer();
    }
}
