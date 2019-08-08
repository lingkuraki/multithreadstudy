package com.kuraki.redis;

import redis.clients.jedis.Jedis;

public class JedisTest {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        Jedis jedis = new Jedis();
        for (int i = 0; i < 100000; i++) {
            jedis.pfadd("kuraki", "use" + i);
        }
        long total = jedis.pfcount("kuraki");
        System.out.printf("%d %d\n", 100000, total);
        jedis.close();
        long end = System.currentTimeMillis();
        System.out.println("总共执行了：" + ((end - start) / 1000) + "秒");
    }
}
