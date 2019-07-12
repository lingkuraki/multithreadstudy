package com.kuraki.concurrency.chapter27;

import com.kuraki.concurrency.chapter19.FutureTask;

/**
 * 重写finish方法，并将protected的权限改为public,
 * 是的执行线程完成任务之后传递最终结果
 *
 * @param <T>
 */
public class ActiveFuture<T> extends FutureTask<T> {

    @Override
    public void finish(T result) {
        super.finish(result);
    }
}
