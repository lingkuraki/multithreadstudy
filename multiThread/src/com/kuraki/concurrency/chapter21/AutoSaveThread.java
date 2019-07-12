package com.kuraki.concurrency.chapter21;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AutoSaveThread extends Thread {

    private final Document document;

    public AutoSaveThread(Document document) {
        super("DocumentAutoSaveThread");
        this.document = document;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 每隔一秒自动保存一次文件
                document.save();
                TimeUnit.SECONDS.sleep(1);
            } catch (IOException | InterruptedException e) {
                break;
            }
        }
    }
}
