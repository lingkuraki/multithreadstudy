package com.kuraki.concurrency.chapter28.EventBusActual;

import com.kuraki.concurrency.chapter28.Subscribe;

public class FileChangeListener {

    @Subscribe
    public void onChange(FileChangeEvent event) {
        System.out.printf("%s-%s\n", event.getPath(), event.getKind());
    }
}
