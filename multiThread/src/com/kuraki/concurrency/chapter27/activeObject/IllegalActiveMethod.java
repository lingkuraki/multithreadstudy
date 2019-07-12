package com.kuraki.concurrency.chapter27.activeObject;

/**
 * 若方法不符合则被转换为Active方法时抛出该异常
 */
public class IllegalActiveMethod extends Exception {
    public IllegalActiveMethod(String message) {
        super(message);
    }
}