package com.kuraki.concurrency.chapter28;

public interface EventExceptionHandler {
    void handle(Throwable cause, EventContext context);
}
