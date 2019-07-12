package com.kuraki.concurrency.chapter28.EventBusActual;

import com.kuraki.concurrency.chapter28.EventBus;

import java.io.IOException;
import java.nio.file.*;

public class DirectoryTargetMonitor {

    private WatchService watchService;

    private final EventBus eventBus;

    private final Path path;

    private volatile boolean start = false;

    public DirectoryTargetMonitor(final EventBus eventBus, final Path path) {
        this.eventBus = eventBus;
        this.path = path;
    }

    // 构造Monitor的时候需要传入EventBus以及需要监控的目录
    public DirectoryTargetMonitor(final EventBus eventBus, final String targetPath, final String... morePath) {
        this.eventBus = eventBus;
        this.path = Paths.get(targetPath, morePath);
    }

    public void startMonitor() throws IOException {

        this.watchService = FileSystems.getDefault().newWatchService();
        // 为路径注册感兴趣的事件
        this.path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_CREATE);
        this.start = true;
        while (start) {
            WatchKey watchKey = null;
            try {
                // 当有事件发生时会返回对应的WatchKey
                watchKey = watchService.take();
                watchKey.pollEvents().forEach(event -> {
                    WatchEvent.Kind<?> kind = event.kind();
                    Path path = (Path) event.context();
                    Path child = DirectoryTargetMonitor.this.path.resolve(path);
                    // 提交FileChangeEvent到EventBus
                    eventBus.post(new FileChangeEvent(child, kind));
                });
            } catch (InterruptedException e) {
                this.start = false;
                e.printStackTrace();
            } finally {
                if (watchKey != null) watchKey.reset();
            }
        }
    }

    public void stopMonitor() throws IOException {
        System.out.printf("The directory [%s] monitor will be stop ...\n", path);
        Thread.currentThread().interrupt();
        this.start = false;
        this.watchService.close();
        System.out.printf("The directory [%s] monitor will be stop done .\n", path);
    }
}
