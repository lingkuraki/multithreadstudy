package com.kuraki.concurrency.chapter26;

/**
 * 抽象类，产品说明书
 */
public abstract class InstructionBook {

    // 在流水线上需要被加工的产品，create作为一个摸板方法，提供了加工产品的说明书
    public final void create(){
        this.firstProcess();
        this.secondProcess();
    }

    protected abstract void firstProcess();
    protected abstract void secondProcess();
}