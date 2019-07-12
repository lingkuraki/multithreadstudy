package com.kuraki.util;

import java.util.concurrent.TimeUnit;

/**
 * @author 凌波
 */
public class SleepUtils {

    public static void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
